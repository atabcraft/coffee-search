package com.example.search.coffee.config;

import com.example.search.coffee.domain.Coffee;
import com.example.search.coffee.repository.CoffeeRepository;
import com.example.search.coffee.repository.es.CoffeeSearchRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Alternative to whole reindexing service.
 * Since this app is here just for demo, simple Applicaton runner is used to fill the ES with initial data.
 * @author Armin
 */
@Component
public class ElasticsearchDataLoader implements ApplicationRunner {

    private CoffeeSearchRepository coffeeSearchRepository;
    
    private CoffeeRepository coffeRepository;

    public ElasticsearchDataLoader(CoffeeSearchRepository coffeeSearchRepository, CoffeeRepository coffeRepository) {
        this.coffeeSearchRepository = coffeeSearchRepository;
        this.coffeRepository = coffeRepository;
    }
    
    
    private static final Logger log = LoggerFactory.getLogger(ElasticsearchDataLoader.class);

    @Override
    public void run(ApplicationArguments args) {
        log.info("Clearing all documents from index coffee!");
        coffeeSearchRepository.deleteAll();
        List<Coffee> coffees = coffeRepository.findAll();
        log.info("Fillining elasticsearch with PostgresSQL data, found {} rows, ", coffees.size());
        coffeeSearchRepository.saveAll(coffees);
    }

}