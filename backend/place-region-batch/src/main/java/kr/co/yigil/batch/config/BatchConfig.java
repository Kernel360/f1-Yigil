package kr.co.yigil.batch.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.infrastructure.PlaceRepository;
import kr.co.yigil.region.domain.Division;
import kr.co.yigil.region.domain.DongDivision;
import kr.co.yigil.region.domain.Region;
import kr.co.yigil.region.infrastructure.DivisionRepository;
import kr.co.yigil.region.infrastructure.DongDivisionRepository;
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
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BatchConfig {
    private final PlaceRepository placeRepository;
    private final DivisionRepository divisionRepository;
    private final DongDivisionRepository dongDivisionRepository;
    @Bean
    public Job updateRegionJob(JobRepository jobRepository, Step updateRegionStep) {
        return new JobBuilder("updateRegionJob", jobRepository)
                .start(updateRegionStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step updateRegionStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            ItemReader<Place> placeItemReader,
            ItemProcessor<Place, Place> placeItemProcessor,
            ItemWriter<Place> placeItemWriter
    ) {
        return new StepBuilder("updateRegionStep", jobRepository)
                .<Place, Place>chunk(10, platformTransactionManager)
                .reader(placeItemReader)
                .processor(placeItemProcessor)
                .writer(placeItemWriter)
                .build();
    }

    @Bean
    public RepositoryItemReader<Place> placeItemReader() {
        return new RepositoryItemReaderBuilder<Place>()
                .name("placeItemReader")
                .repository(placeRepository)
                .methodName("findByRegionIsNull")
                .pageSize(10)
                .arguments(List.of())
                .sorts(Collections.singletonMap("id", Direction.DESC))
                .build();
    }

    @Bean
    public ItemProcessor<Place, Place> placeItemProcessor() {
        return place -> {
            Region region = findRegionForPlace(place);
            if (region != null) {
                place.updateRegion(region);
                return place;
            }
            return null;
        };
    }

    @Bean
    public ItemWriter<Place> placeItemWriter(EntityManagerFactory entityManagerFactory) {
        return places -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            for (Place place : places) {
                if (place != null) {
                    entityManager.merge(place);
                }
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        };
    }


    public Region findRegionForPlace(Place place) {
        Optional<Division> divisionOptional = divisionRepository.findContainingDivision(place.getLocation());

        if(divisionOptional.isEmpty()) return null;

        Division division = divisionOptional.get();

        if(division.isSeoul()) {
            Optional<DongDivision> dongDivisionOptional = dongDivisionRepository.findContainingDivision(place.getLocation());

            if(dongDivisionOptional.isEmpty()) return null;

            DongDivision dongDivision = dongDivisionOptional.get();

            return dongDivision.getRegion();
        }
        return division.getRegion();
    }

}
