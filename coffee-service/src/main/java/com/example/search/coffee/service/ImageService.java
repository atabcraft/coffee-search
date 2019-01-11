package com.example.search.coffee.service;

import com.example.search.coffee.repository.ImageRepository;
import com.example.search.coffee.domain.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for Image manipulation.
 *
 * @author atiric
 */
@Service
@Transactional
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final ImageRepository imageRepository;
    
    private final ImageUrlHelper imageUrlHelper;

    public ImageService(ImageRepository imageRepository, ImageUrlHelper imageUrlHelper) {
        this.imageRepository = imageRepository;
        this.imageUrlHelper = imageUrlHelper;
    }

    public Image createUpdateImage(Image image) {
        return imageRepository.save(image);
    }
    
    @Transactional(propagation = Propagation.NEVER)
    public String createUrlOfImage( Long id){
        return imageUrlHelper.createImageUrl(id);
    }
   
}
