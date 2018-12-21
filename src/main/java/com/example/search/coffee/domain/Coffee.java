package com.example.search.coffee.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * Entity that models coffee.
 * @author Armin
 */
@Entity
public class Coffee extends AuditEntity implements Serializable {

    public Coffee() {
        
    }
    
    public Coffee(Long id, String name, CoffeeType coffeType, Image image) {
        this.id = id;
        this.name = name;
        this.coffeType = coffeType;
        this.image = image;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @NotNull
    @Column( name = "name")
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "coffee_type", nullable = false)
    private CoffeeType coffeType;
    
    @Column(name = "coffee_origin")
    private String origin;
    
    /**
     * I'm aware that this will map to image_id as a unidirectional foreign key of Image
     */
    @OneToOne
    private Image image;
    
}
