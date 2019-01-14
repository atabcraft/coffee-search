package com.example.search.coffee.service;

import com.example.search.coffee.domain.CoffeeType;
import com.example.search.coffee.domain.es.CoffeeDocument;
import com.example.search.coffee.repository.CoffeeRepository;
import com.example.search.coffee.repository.es.CoffeeSearchRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Armin
 */
@Service
@Transactional
public class CoffeeService {
    
    private final CoffeeSearchRepository coffeeSerachRepository;
    
    private final CoffeeRepository coffeeRepository;
    
    private final ImageService imageService;
    
    private final Logger log = LoggerFactory.getLogger(CoffeeService.class);

    public CoffeeService(CoffeeSearchRepository coffeeSerachRepository, CoffeeRepository coffeeRepository, ImageService imageService) {
        this.coffeeSerachRepository = coffeeSerachRepository;
        this.coffeeRepository = coffeeRepository;
        this.imageService = imageService;
    }
    
    public Page<CoffeeDocument> searchAllCoffeeDocuments(Pageable pageable, CoffeeType coffeeType, String query){
        log.info("Starting search with query \"{}\" of type {} for pageable {}", query, coffeeType, pageable );
        QueryBuilder filterCoffeTypeQuery = null;
        if( coffeeType == CoffeeType.ANY ){
            filterCoffeTypeQuery = QueryBuilders.existsQuery("coffeeType");
        } else {
            filterCoffeTypeQuery = QueryBuilders.termQuery("coffeeType", coffeeType.name());
        }
        
        QueryBuilder searchQuery; 
        if( query.isEmpty() ){
            searchQuery = QueryBuilders.matchAllQuery();
        } else {
            searchQuery = multiMatchQuery(
                query, 
                "name",
                "origin"       
            );
        }
        
        QueryBuilder boolQueryBuilder = new BoolQueryBuilder()
                .filter( filterCoffeTypeQuery )
                .must(searchQuery);
        return coffeeSerachRepository.search(boolQueryBuilder, pageable);
    }
    
    
}
