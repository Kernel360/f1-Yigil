package kr.co.yigil.batch.job;


import jakarta.persistence.EntityManagerFactory;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PopularPlace;
import kr.co.yigil.place.infrastructure.PopularPlaceRepository;
import kr.co.yigil.travel.infrastructure.SpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PopularPlaceJobConfig {
    private final SpotRepository spotRepository;
    private final PopularPlaceRepository popularPlaceRepository;
    private final EntityManagerFactory entityManagerFactory;
    @Bean
    public Job popularPlaceJob(
            JobRepository jobRepository,
            Step calculatePopularPlacesStep,
            Step clearPopularPlacesStep
    ) {
        return new JobBuilder("popularPlaceJob", jobRepository)
                .start(clearPopularPlacesStep)
                .next(calculatePopularPlacesStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step clearPopularPlacesStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("clearPopularPlacesStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    popularPlaceRepository.deleteAll();
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step calculatePopularPlacesStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            ItemReader<Object[]> popularPlaceItemReader,
            ItemProcessor<Object[], PopularPlace> popularPlaceItemProcessor,
            ItemWriter<PopularPlace> popularPlaceItemWriter
    ) {
        return new StepBuilder("calculatePopularPlacesStep", jobRepository)
                .<Object[], PopularPlace>chunk(30, platformTransactionManager)
                .reader(popularPlaceItemReader)
                .processor(popularPlaceItemProcessor)
                .writer(popularPlaceItemWriter)
                .build();
    }

    @Bean
    public JpaPagingItemReader<Object[]> popularPlaceItemReader() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusWeeks(1);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        return new JpaPagingItemReaderBuilder<Object[]>()
                .queryString("SELECT s.place, COUNT(s) AS referenceCount FROM Spot s WHERE s.createdAt BETWEEN :startDate AND :endDate GROUP BY s.place ORDER BY referenceCount DESC")
                .entityManagerFactory(entityManagerFactory)
                .name("popularPlaceItemReader")
                .parameterValues(parameters)
                .pageSize(30)
                .build();
    }

    @Bean
    public ItemProcessor<Object[], PopularPlace> popularPlaceItemProcessor() {
        return placeCount -> {
            Place place = (Place) placeCount[0];
            long referenceCount = (long) placeCount[1];
            return new PopularPlace(place, referenceCount);
        };
    }

    @Bean
    public RepositoryItemWriter<PopularPlace> popularPlaceItemWriter() {
        return new RepositoryItemWriterBuilder<PopularPlace>()
                .repository(popularPlaceRepository)
                .methodName("save")
                .build();

    }
}
