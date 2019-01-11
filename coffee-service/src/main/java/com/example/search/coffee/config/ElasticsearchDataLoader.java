package com.example.search.coffee.config;

import com.example.search.coffee.domain.Coffee;
import com.example.search.coffee.domain.CoffeeType;
import com.example.search.coffee.domain.es.CoffeeDocument;
import com.example.search.coffee.repository.CoffeeRepository;
import com.example.search.coffee.repository.es.CoffeeSearchRepository;
import com.example.search.coffee.service.ImageService;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
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
    
    private ImageService imageService;
    
    private ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchDataLoader(CoffeeSearchRepository coffeeSearchRepository, CoffeeRepository coffeRepository, ImageService imageService, ElasticsearchTemplate elasticsearchTemplate) {
        this.coffeeSearchRepository = coffeeSearchRepository;
        this.coffeRepository = coffeRepository;
        this.imageService = imageService;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }
    
    
    private static final Logger log = LoggerFactory.getLogger(ElasticsearchDataLoader.class);

    @Override
    public void run(ApplicationArguments args) {
        log.info("Clearing all documents from index coffee!");
        if( !elasticsearchTemplate.indexExists(CoffeeDocument.class) ){
            elasticsearchTemplate.createIndex(CoffeeDocument.class);
        }
        coffeeSearchRepository.deleteAll();
        elasticsearchTemplate.putMapping(CoffeeDocument.class);
        List<CoffeeDocument> coffeeDocuments = coffeRepository.findAll().stream()
                .map(coffee -> {
                    //Hibernate proxy here will not request fetch!
                    String imageUrl = imageService.createUrlOfImage(coffee.getImage().getId());
                    return new CoffeeDocument(
                            coffee.getId(),
                            coffee.getName(),
                            coffee.getCoffeeType(),
                            coffee.getOrigin(),
                            imageUrl);
                        })
                .collect(Collectors.toList());
        log.info("Fillining elasticsearch with PostgresSQL data, found {} rows, ", coffeeDocuments.size());
        coffeeSearchRepository.saveAll(coffeeDocuments);
    }

}