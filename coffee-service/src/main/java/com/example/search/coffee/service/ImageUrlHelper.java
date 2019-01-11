/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.search.coffee.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Helper component for generation of image URL-s for image resource
 *
 * @author Armin
 */
@Component
public class ImageUrlHelper {

    private final Logger log = LoggerFactory.getLogger(ImageUrlHelper.class);
    
    @Autowired
    private Environment environment;

    public  String createImageUrl(Long id) {
        
        StringBuilder sb = new StringBuilder();

        sb.append("http://");
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            log.warn(e.getMessage());
            e.printStackTrace();
        }
        
        sb.append(ip == null? "localhost": ip.getHostAddress()).append(":");
        sb.append(environment.getProperty("server.port"));
        sb.append("/api/images/").append(id);
        
        return sb.toString();

    }

}
