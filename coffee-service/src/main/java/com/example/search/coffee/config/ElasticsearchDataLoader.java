package com.example.search.coffee.config;

import com.example.search.coffee.domain.Coffee;
import com.example.search.coffee.domain.CoffeeType;
import com.example.search.coffee.repository.es.CoffeeSearchRepository;
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

    public ElasticsearchDataLoader(CoffeeSearchRepository coffeeSearchRepository) {
        this.coffeeSearchRepository = coffeeSearchRepository;
    }
    
    
    private static final Logger log = LoggerFactory.getLogger(ElasticsearchDataLoader.class);

    @Override
    public void run(ApplicationArguments args) {
        log.info("Filling elasticsearch");
        coffeeSearchRepository.save(new Coffee(1l, "Prva kava", CoffeeType.ROBUSTA, null));
    }

}