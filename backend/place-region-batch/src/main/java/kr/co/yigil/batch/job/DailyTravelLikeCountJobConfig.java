package kr.co.yigil.batch.job;

import jakarta.persistence.EntityManagerFactory;
import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.favor.infrastructure.DailyFavorCountRepository;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.infrastructure.TravelRepository;
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
public class DailyTravelLikeCountJobConfig {
    private final EntityManagerFactory em;
    private final TravelRepository travelRepository;
    private final DailyFavorCountRepository dailyFavorCountRepository;

    @Bean
    public Job likeCountJob(
            JobRepository jobRepository,
            Step calculateLikeCountStep
    ) {
        return new JobBuilder("likeCountJob", jobRepository)
                .start(calculateLikeCountStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step calculateLikeCountStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            ItemReader<Object[]> likeCountItemReader,
            ItemProcessor<Object[], DailyFavorCount> likeCountItemProcessor,
            ItemWriter<DailyFavorCount> dailyFavorCountItemWriter
    ) {
        return new StepBuilder("calculateLikeCountStep", jobRepository)
                .<Object[], DailyFavorCount>chunk(30, platformTransactionManager)
                .reader(likeCountItemReader)
                .processor(likeCountItemProcessor)
                .writer(dailyFavorCountItemWriter)
                .build();
    }

    @Bean
    public JpaPagingItemReader<Object[]> likeCountItemReader() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate today = LocalDate.now();

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("yesterday", yesterday);
        parameters.put("today", today);

        return new JpaPagingItemReaderBuilder<Object[]>()
                .queryString("SELECT t.id, COUNT(f), f.createdAt FROM Travel t LEFT JOIN Favor f ON f.travel = t AND f.createdAt >= :yesterday AND f.createdAt < :today GROUP BY t.id, f.createdAt HAVING COUNT(f) > 0")
//                .queryString("SELECT t.id, COUNT(f), f.createdAt FROM Travel t LEFT JOIN Favor f ON f.travel = t AND f.createdAt >= :yesterday AND f.createdAt < :today GROUP BY t.id, f.createdAt")
                .entityManagerFactory(em)
                .name("likeCountItemReader")
                .parameterValues(parameters)
                .pageSize(30)
                .build();
    }

    @Bean
    public ItemProcessor<Object[], DailyFavorCount> likeCountItemProcessor() {
        return item -> {
            Long travelId = (Long) item[0];
            Travel travel = travelRepository.findById(travelId).orElseThrow(
                    () -> new IllegalArgumentException("Travel not found. id=" + travelId)
            );
            long count = (Long) item[1];
            LocalDate date = (LocalDate) item[2];
            return new DailyFavorCount(count, travel, date);
        };
    }

    @Bean
    public ItemWriter<DailyFavorCount> dailyFavorCountItemWriter() {
        return new RepositoryItemWriterBuilder<DailyFavorCount>()
                .repository(dailyFavorCountRepository)
                .methodName("save")
                .build();
    }

}
