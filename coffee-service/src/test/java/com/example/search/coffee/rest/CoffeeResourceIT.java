package com.example.search.coffee.rest;

import com.example.search.coffee.CoffeeApplication;
import com.example.search.coffee.config.ElasticsearchConfig;
import com.example.search.coffee.domain.Coffee;
import com.example.search.coffee.domain.CoffeeType;
import com.example.search.coffee.repository.CoffeeRepository;
import com.example.search.coffee.service.CoffeeService;
import com.example.search.coffee.service.ImageService;
import com.example.search.coffee.service.ImageUrlHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {CoffeeApplication.class, ElasticsearchConfig.class, CoffeeService.class, ImageService.class, ImageUrlHelper.class})
public class CoffeeResourceIT {

    private Logger log = LoggerFactory.getLogger(CoffeeResourceIT.class);

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeService coffeeService;

    private MockMvc restUserMockMvc;

    private Coffee defautCoffee;

    private static final String DEFAULT_COFFEE_NAME = "Test coffee";


    @BeforeEach
    public void setup() {
        CoffeeResource coffeeResource = new CoffeeResource(coffeeService);
        restUserMockMvc = MockMvcBuilders.standaloneSetup(coffeeResource).build();
        this.defautCoffee = new Coffee();
        this.defautCoffee.setName(DEFAULT_COFFEE_NAME);
        this.defautCoffee.setCoffeeType(CoffeeType.ARABICA);
    }
    

    @Test
    @Transactional
    public void createAndFindCoffeeById_shouldReturnById() throws Exception {

        int sizeBeforeSave = this.coffeeRepository.findAll().size();
        Coffee savedCoffee = this.coffeeRepository.save(this.defautCoffee);
        List<Coffee> coffees = this.coffeeRepository.findAll();
        assertThat(coffees).hasSize(sizeBeforeSave + 1);
        assertThat(savedCoffee.getId()).isNotNull();
        this.restUserMockMvc.perform(get("/coffees/{id}", savedCoffee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(savedCoffee.getId()));

    }



}