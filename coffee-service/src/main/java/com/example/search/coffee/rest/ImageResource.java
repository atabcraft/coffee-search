package com.example.search.coffee.rest;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.search.coffee.repository.ImageRepository;
import com.example.search.coffee.service.ImageService;
import com.example.search.coffee.domain.Image;

/**
 * REST controller for managing Image entity.
 */
@RestController
@RequestMapping("/api")
public class ImageResource {

    public ImageService imageService;

    public ImageRepository imageRepository;

    public ImageResource(ImageService imageService, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    @PostMapping("/images")
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        Image processedImage = imageService.createUpdateImage(image);
        return Optional.ofNullable(processedImage).map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }

    /**
     * Method that streams the Image entity byte array without being in transaction,
     * this is on purpose because being in transaction slows down reading of image from db. 
     * @param imageId Id of image.
     * @return Streaming response entity image.
     */
    @GetMapping(value = "/images/{imageId}")
    public ResponseEntity<InputStreamResource> getImageById(@PathVariable Long imageId) {
        Optional<Image> possibleImage = imageRepository.findById(imageId);
        if( possibleImage.isPresent() ){
            Image image = possibleImage.get();
            byte[] bimage = image.getImage();
            return ResponseEntity.ok().contentLength(bimage.length)
                .contentType(MediaType.parseMediaType(image.getImageContentType()))
                .body(new InputStreamResource(new ByteArrayInputStream(bimage)));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
