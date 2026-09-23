package com.akshat.urlshortener.controller;

import com.akshat.urlshortener.entity.UrlMapping;
import com.akshat.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public UrlMapping createShortUrl(@RequestBody String url) {
        return urlService.createShortUrl(url);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        UrlMapping urlMapping = urlService.getByShortCode(shortCode);

        if (urlMapping == null) {
            return ResponseEntity.notFound().build();
        }

        URI location = URI.create(urlMapping.getUrl());

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(location)
                .build();
    }
}
