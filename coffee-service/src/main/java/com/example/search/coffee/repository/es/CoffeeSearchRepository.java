package com.example.search.coffee.repository.es;

import com.example.search.coffee.domain.Coffee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author Armin
 */
public interface CoffeeSearchRepository extends ElasticsearchRepository<Coffee, Long> {
    
}
