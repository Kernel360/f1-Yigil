package kr.co.yigil.batch.job;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.place.domain.DemographicPlace;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.infrastructure.DemographicPlaceRepository;
import kr.co.yigil.travel.infrastructure.SpotRepository;
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
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class DemographicPlaceJobConfig {

    private final SpotRepository spotRepository;
    private final DemographicPlaceRepository demographicPlaceRepository;

    @Bean
    public Job demographicPlaceJob(
            JobRepository jobRepository,
            Step calculateDemographicPlacesStep,
            Step clearDemographicPlacesStep
    ) {
        return new JobBuilder("demographicPlaceJob", jobRepository)
                .start(clearDemographicPlacesStep)
                .next(calculateDemographicPlacesStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step clearDemographicPlacesStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("clearDemographicPlacesStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    demographicPlaceRepository.deleteAll();
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step calculateDemographicPlacesStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            ItemReader<Object[]> demographicPlaceItemReader,
            ItemProcessor<Object[], DemographicPlace> demographicPlaceItemProcessor,
            ItemWriter<DemographicPlace> demographicPlaceItemWriter
    ) {
        return new StepBuilder("calculateDemographicPlacesStep", jobRepository)
                .<Object[], DemographicPlace>chunk(10, platformTransactionManager)
                .reader(demographicPlaceItemReader)
                .processor(demographicPlaceItemProcessor)
                .writer(demographicPlaceItemWriter)
                .build();
    }

    @Bean
    public ItemReader<Object[]> demographicPlaceItemReader() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusWeeks(1);

        return new RepositoryItemReaderBuilder<Object[]>()
                .name("demographicPlaceItemReader")
                .repository(spotRepository)
                .methodName("findPlaceReferenceCountGroupByDemographicBetweenDates")
                .pageSize(10)
                .arguments(List.of(startDate, endDate))
                .sorts(Map.of("referenceCount", Direction.DESC))
                .build();
    }

    @Bean
    public ItemProcessor<Object[], DemographicPlace> demographicPlaceItemProcessor() {
        return item -> {
            Place place = (Place) item[0];
            long referenceCount = (long) item[1];
            Ages ages = (Ages) item[2];
            Gender gender = (Gender) item[3];
            return new DemographicPlace(place, referenceCount, ages, gender);
        };
    }

    @Bean
    public RepositoryItemWriter<DemographicPlace> demographicPlaceItemWriter() {
        return new RepositoryItemWriterBuilder<DemographicPlace>()
                .repository(demographicPlaceRepository)
                .methodName("save")
                .build();
    }
}

