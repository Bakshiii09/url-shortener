package com.akshat.urlshortener.service;

import com.akshat.urlshortener.entity.UrlMapping;
import com.akshat.urlshortener.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UrlService {
    private final UrlMappingRepository urlMappingRepository;

    public UrlService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public UrlMapping createShortUrl(String url) {
        UrlMapping mapping = new UrlMapping();

        LocalDateTime timeNow = java.time.LocalDateTime.now();

        String shortCode = generateShortCode();
        while (urlMappingRepository.findByShortCode(shortCode).isPresent()) {
            shortCode = generateShortCode();
        }
        mapping.setShortCode(shortCode);

        mapping.setUrl(url);
        mapping.setAccessCount(0L);
        mapping.setCreatedAt(timeNow);
        mapping.setUpdatedAt(timeNow);
        mapping = urlMappingRepository.save(mapping);
        return mapping;
    }

    private String generateShortCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();

        for (int i=0;i<=5;i++) {
            int index = random.nextInt(characters.length());
            shortCode.append(characters.charAt(index));
        }
        return shortCode.toString();
    }

    public UrlMapping getByShortCode(String shortCode) {

        return urlMappingRepository
                .findByShortCode(shortCode)
                .orElse(null);
    }


}
