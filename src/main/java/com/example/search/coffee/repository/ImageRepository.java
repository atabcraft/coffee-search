/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.search.coffee.repository;

import com.example.search.coffee.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author atiric
 */
public interface ImageRepository extends JpaRepository<Image, Long>{
 
}
