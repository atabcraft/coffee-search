/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.search.coffee.rest;


import com.example.search.coffee.domain.CoffeeType;
import com.example.search.coffee.domain.es.CoffeeDocument;
import com.example.search.coffee.service.CoffeeService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    private CoffeeService coffeeService;

    public CoffeeResource(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    
    @GetMapping("/coffees/_search/coffee-with-type")
    public ResponseEntity<List<CoffeeDocument>> searchCoffeeWithType(Pageable pageable, @RequestParam String query, @RequestParam CoffeeType coffeeType ){
        
        Page<CoffeeDocument> coffeePage = coffeeService.searchAllCoffeeDocuments(pageable, coffeeType, query);
        
        return new ResponseEntity<>(coffeePage.getContent(), HttpStatus.OK);
        
    }
    
}
