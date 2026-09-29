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

        LocalDateTime timeNow = LocalDateTime.now();

        String shortCode = generateUniqueShortCode();

        mapping.setUrl(url);
        mapping.setShortCode(shortCode);
        mapping.setAccessCount(0L);
        mapping.setCreatedAt(timeNow);
        mapping.setUpdatedAt(timeNow);

        return urlMappingRepository.save(mapping);
    }

    public UrlMapping getByShortCode(String shortCode) {

        return urlMappingRepository
                .findByShortCode(shortCode)
                .orElse(null);
    }

    public UrlMapping updateShortUrl(String shortCode, String url) {

        UrlMapping mapping = urlMappingRepository
                .findByShortCode(shortCode)
                .orElse(null);

        if (mapping == null) {
            return null;
        }

        mapping.setUrl(url);
        mapping.setUpdatedAt(LocalDateTime.now());

        return urlMappingRepository.save(mapping);
    }

    public boolean deleteShortUrl(String shortCode) {

        UrlMapping mapping = urlMappingRepository
                .findByShortCode(shortCode)
                .orElse(null);

        if (mapping == null) {
            return false;
        }

        urlMappingRepository.delete(mapping);
        return true;
    }

    public UrlMapping getStatistics(String shortCode) {

        return urlMappingRepository
                .findByShortCode(shortCode)
                .orElse(null);
    }

    public UrlMapping getAndIncrementAccessCount(String shortCode) {

        UrlMapping mapping = urlMappingRepository
                .findByShortCode(shortCode)
                .orElse(null);

        if (mapping == null) {
            return null;
        }

        mapping.setAccessCount(mapping.getAccessCount() + 1);

        return urlMappingRepository.save(mapping);
    }

    private String generateUniqueShortCode() {

        String shortCode;

        do {
            shortCode = generateShortCode();
        } while (urlMappingRepository.findByShortCode(shortCode).isPresent());

        return shortCode;
    }

    private String generateShortCode() {

        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();

        StringBuilder shortCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            shortCode.append(characters.charAt(index));
        }

        return shortCode.toString();
    }
}