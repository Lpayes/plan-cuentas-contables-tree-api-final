package edu.umg.programacion3.pfinal.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.umg.programacion3.pfinal.treeengine.collections.CollectionsTreeStrategy;
import edu.umg.programacion3.pfinal.treeengine.custom.CustomTreeStrategy;
import edu.umg.programacion3.pfinal.treeengine.strategy.TreeAlgorithmStrategy;

@Configuration
public class TreeStrategyConfig {

    @Bean
    @ConditionalOnProperty(name = "app.tree-strategy", havingValue = "custom")
    public TreeAlgorithmStrategy customTreeStrategy() {

        return new CustomTreeStrategy();
    }

    @Bean
    @ConditionalOnProperty(name = "app.tree-strategy", havingValue = "collections")
    public TreeAlgorithmStrategy collectionsTreeStrategy() {

        return new CollectionsTreeStrategy();
    }
}