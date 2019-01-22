/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.search.coffee.rest;


import com.example.search.coffee.domain.Coffee;
import com.example.search.coffee.domain.CoffeeType;
import com.example.search.coffee.domain.es.CoffeeDocument;
import com.example.search.coffee.service.CoffeeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Armin
 */
@RestController
@RequestMapping("/api")
public class CoffeeResource {
    
    private Logger log = LoggerFactory.getLogger(CoffeeResource.class);
    
    private CoffeeService coffeeService;

    public CoffeeResource(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    
    @GetMapping("/coffees/_search/coffee-with-type")
    public ResponseEntity<List<CoffeeDocument>> searchCoffeeWithType(Pageable pageable, @RequestParam String query, @RequestParam CoffeeType coffeeType ){
        
        Page<CoffeeDocument> coffeePage = coffeeService.searchAllCoffeeDocuments(pageable, coffeeType, query);
        
        return new ResponseEntity<>(coffeePage.getContent(), HttpStatus.OK);
        
    }
    
    @GetMapping("/coffees/{id}")
    public ResponseEntity<Coffee> getCoffee(@PathVariable Long id){
        log.info("Fetching coffee with id {}", id);
        Optional<Coffee> possibleCoffee = coffeeService.findCoffeeById(id);
        if( !possibleCoffee.isPresent() ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(possibleCoffee.get(), HttpStatus.OK);
    }
    
    @PutMapping("/coffees")
    public ResponseEntity<Coffee> updateCoffee(@RequestBody Coffee coffee){
        if( coffee.getId() == null ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Coffee> possibleCurrentCoffee = coffeeService.findCoffeeById(coffee.getId());
 
        if( !possibleCurrentCoffee.isPresent() ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Coffee currentCoffee = possibleCurrentCoffee.get();
        currentCoffee.setCoffeeType(coffee.getCoffeeType());
        currentCoffee.setName(coffee.getName());
        currentCoffee.setImage(coffee.getImage());
        currentCoffee.setOrigin(coffee.getOrigin());
        
        currentCoffee = coffeeService.createUpdate(currentCoffee);
        
        return new ResponseEntity<>(currentCoffee, HttpStatus.OK);
    }
    
}
