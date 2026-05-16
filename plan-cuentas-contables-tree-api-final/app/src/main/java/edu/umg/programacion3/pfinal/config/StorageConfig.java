package edu.umg.programacion3.pfinal.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.umg.programacion3.pfinal.infrastructure.mongo.MongoNodeSpringRepository;
import edu.umg.programacion3.pfinal.infrastructure.mongo.MongoTreeRepository;
import edu.umg.programacion3.pfinal.persistence.service.PostgresTreePersistenceService;
import edu.umg.programacion3.pfinal.treeengine.memory.CustomMemoryTreeRepository;

@Configuration
public class StorageConfig {

    @Bean
    @ConditionalOnProperty(name = "app.storage", havingValue = "mongo")
    public MongoTreeRepository mongoTreeRepository(
            MongoNodeSpringRepository mongoRepository) {

        return new MongoTreeRepository(mongoRepository);
    }

    @Bean
    @ConditionalOnProperty(name = "app.storage", havingValue = "memory")
    public CustomMemoryTreeRepository memoryTreeRepository() {

        return new CustomMemoryTreeRepository();
    }
    
    @Bean
    @ConditionalOnProperty(name = "app.storage", havingValue = "postgres")
    public PostgresTreePersistenceService postgresTreeRepository(
            PostgresTreePersistenceService postgresService) {

        return postgresService;
    }
}