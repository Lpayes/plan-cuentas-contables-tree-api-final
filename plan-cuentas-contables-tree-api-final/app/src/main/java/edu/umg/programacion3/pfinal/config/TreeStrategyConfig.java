package edu.umg.programacion3.pfinal.config;

import edu.umg.programacion3.pfinal.treeengine.custom.CustomTreeStrategy;
import edu.umg.programacion3.pfinal.treeengine.strategy.TreeAlgorithmStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TreeStrategyConfig {

    @Bean
    public TreeAlgorithmStrategy treeAlgorithmStrategy() {
        return new CustomTreeStrategy();
    }

}