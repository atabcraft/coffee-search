package com.example.search.coffee.repository;

import com.example.search.coffee.domain.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring repository for basic operations on coffee tables.
 * @author Armin
 */
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    
}
