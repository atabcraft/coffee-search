package com.example.search.coffee.domain;

import java.io.Serializable;
import java.util.Objects;
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

    private static final long serialVersionUID = 1L;

    public Coffee() {
        
    }
    
    public Coffee(Long id, String name, CoffeeType coffeeType, Image image) {
        this.id = id;
        this.name = name;
        this.coffeeType = coffeeType;
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
    private CoffeeType coffeeType;
    
    @Column(name = "coffee_origin")
    private String origin;
    
    /**
     * I'm aware that this will map to image_id in db as foreign key of Image, it a undirectional relationship because I want to access it only thru Coffee.
     */
    @OneToOne
    private Image image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoffeeType getCoffeeType() {
        return coffeeType;
    }

    public void setCoffeeType(CoffeeType coffeeType) {
        this.coffeeType = coffeeType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coffee other = (Coffee) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Coffee{" + "id=" + id + ", name=" + name + ", coffeeType=" + coffeeType + ", origin=" + origin + ", image=" + image + '}';
    }

    
}
