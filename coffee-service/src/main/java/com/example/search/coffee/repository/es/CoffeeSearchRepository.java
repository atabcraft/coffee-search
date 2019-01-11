package com.example.search.coffee.repository.es;

import com.example.search.coffee.domain.Coffee;
import com.example.search.coffee.domain.es.CoffeeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author Armin
 */
public interface CoffeeSearchRepository extends ElasticsearchRepository<CoffeeDocument, Long> {
    
}
