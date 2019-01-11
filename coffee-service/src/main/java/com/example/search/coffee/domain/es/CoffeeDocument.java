package com.example.search.coffee.domain.es;

import com.example.search.coffee.domain.Coffee;
import com.example.search.coffee.domain.CoffeeType;
import com.example.search.coffee.service.ImageUrlHelper;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 *
 * @author Armin
 */
@Document(indexName = "coffee")
public class CoffeeDocument {

    public CoffeeDocument(Long id, String name, CoffeeType coffeeType, String origin, String imageUrl) {
        this.id = id;
        this.name = name;
        this.coffeeType = coffeeType;
        this.origin = origin;
        this.imageUrl = imageUrl;
    }

    public CoffeeDocument() {
    }
    
    public CoffeeDocument(Coffee coffee) {
        this.id = coffee.getId();
        this.name = coffee.getName();
        this.coffeeType = coffee.getCoffeeType();
        this.origin = coffee.getOrigin();
    }
    
    
    @Id
    private Long id;
    
    @Field(type = FieldType.Text)
    private String name;
    
    @Field(type = FieldType.Text)
    private CoffeeType coffeeType;
    
    @Field( type = FieldType.Text)
    private String origin;
    
    /**
     * It is not recommended to store whole image in Elasticsearch because it defeats the purpose of searching so mapping to its URL is recommended.
     */
    @Field( type = FieldType.Text)
    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final CoffeeDocument other = (CoffeeDocument) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CoffeeDocument{" + "id=" + id + ", name=" + name + ", coffeeType=" + coffeeType + ", origin=" + origin + ", imageUrl=" + imageUrl + '}';
    }
    
}
