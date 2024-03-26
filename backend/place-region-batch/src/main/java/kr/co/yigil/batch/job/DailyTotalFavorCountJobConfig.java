package kr.co.yigil.batch.job;

import jakarta.persistence.EntityManagerFactory;
import kr.co.yigil.favor.domain.DailyTotalFavorCount;
import kr.co.yigil.favor.infrastructure.DailyTotalFavorCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class DailyTotalFavorCountJobConfig {
    private final EntityManagerFactory em;
    private final DailyTotalFavorCountRepository dailyTotalFavorCountRepository;

    @Bean
    public Job totalCountJob(
            JobRepository jobRepository,
            Step calculateTotalCountStep
    ) {
        return new JobBuilder("DailyTotalCountJob", jobRepository)
                .start(calculateTotalCountStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step calculateTotalCountStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            ItemReader<Object[]> totalCountItemReader,
            ItemProcessor<Object[], DailyTotalFavorCount> totalFavorCountItemProcessor,
            ItemWriter<DailyTotalFavorCount> dailyTotalFavorCountItemWriter
    ) {
        return new StepBuilder("calculateTotalCountStep", jobRepository)
                .<Object[], DailyTotalFavorCount>chunk(30, platformTransactionManager)
                .reader(totalCountItemReader)
                .processor(totalFavorCountItemProcessor)
                .writer(dailyTotalFavorCountItemWriter)
                .build();
    }

    @Bean
    public JpaPagingItemReader<Object[]> totalCountItemReader() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate today = LocalDate.now();

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("yesterday", yesterday);
        parameters.put("today", today);

        return new JpaPagingItemReaderBuilder<Object[]>()
                .queryString("SELECT count(f.id), f.createdAt FROM DailyFavorCount f WHERE f.createdAt >= :yesterday AND f.createdAt < :today GROUP BY f.createdAt")
                .entityManagerFactory(em)
                .name("totalCountItemReader")
                .parameterValues(parameters)
                .pageSize(100)
                .build();
    }

    @Bean
    public ItemProcessor<Object[], DailyTotalFavorCount> totalFavorCountItemProcessor() {
        return item ->{
            Long count = (Long) item[0];
            LocalDate createdAt = (LocalDate) item[1];
          return new DailyTotalFavorCount(count, createdAt);
        };
    }

    @Bean
    public ItemWriter<DailyTotalFavorCount> dailyTotalFavorCountItemWriter() {
        return new RepositoryItemWriterBuilder<DailyTotalFavorCount>()
                .repository(dailyTotalFavorCountRepository)
                .methodName("save")
                .build();
    }
}
