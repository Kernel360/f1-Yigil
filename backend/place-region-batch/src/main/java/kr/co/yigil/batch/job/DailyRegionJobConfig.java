package kr.co.yigil.batch.job;

import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.region.domain.Region;
import kr.co.yigil.region.infrastructure.DailyRegionRepository;
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
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class DailyRegionJobConfig {
    private final EntityManagerFactory entityManagerFactory;
    private final DailyRegionRepository dailyRegionRepository;

    @Bean
    public Job dailyRegionJob(
            JobRepository jobRepository,
            Step calculateDailyRegionsStep
    ) {
        return new JobBuilder("dailyRegionJob", jobRepository)
                .start(calculateDailyRegionsStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step calculateDailyRegionsStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            ItemReader<Object[]> dailyRegionItemReader,
            ItemProcessor<Object[], DailyRegion> dailyRegionItemProcessor,
            ItemWriter<DailyRegion> dailyRegionItemWriter
    ) {
        return new StepBuilder("calculateDailyRegionsStep", jobRepository)
                .<Object[], DailyRegion>chunk(30, platformTransactionManager)
                .reader(dailyRegionItemReader)
                .processor(dailyRegionItemProcessor)
                .writer(dailyRegionItemWriter)
                .build();
    }
    @Bean
    public JpaPagingItemReader<Object[]> dailyRegionItemReader() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime startOfYesterday = yesterday.atStartOfDay();
        LocalDateTime endOfYesterday = yesterday.plusDays(1).atStartOfDay();

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("startDate", startOfYesterday);
        parameters.put("endDate", endOfYesterday);

        return new JpaPagingItemReaderBuilder<Object[]>()
                .queryString("SELECT p.region, COUNT(s) AS referenceCount FROM Spot s JOIN s.place p WHERE s.createdAt >= :startDate AND s.createdAt < :endDate GROUP BY p.region")
                .entityManagerFactory(entityManagerFactory)
                .name("dailyRegionItemReader")
                .parameterValues(parameters)
                .pageSize(30)
                .build();
    }

    @Bean
    public ItemProcessor<Object[], DailyRegion> dailyRegionItemProcessor() {
        return regionCount -> {
            Region region = (Region) regionCount[0];
            long referenceCount = (long) regionCount[1];
            return new DailyRegion(LocalDate.now().minusDays(1), region, referenceCount);
        };
    }

    @Bean
    public RepositoryItemWriter<DailyRegion> dailyRegionItemWriter() {
        return new RepositoryItemWriterBuilder<DailyRegion>()
                .repository(dailyRegionRepository)
                .methodName("save")
                .build();
    }
}
