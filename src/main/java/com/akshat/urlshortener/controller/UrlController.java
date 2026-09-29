package com.akshat.urlshortener.controller;

import com.akshat.urlshortener.dto.CreateUrlRequest;
import com.akshat.urlshortener.dto.UpdateUrlRequest;
import com.akshat.urlshortener.entity.UrlMapping;
import com.akshat.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlMapping> createShortUrl(
            @Valid @RequestBody CreateUrlRequest request) {

        UrlMapping mapping = urlService.createShortUrl(request.getUrl());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapping);
    }

    @GetMapping("/shorten/{shortCode}")
    public ResponseEntity<UrlMapping> getShortUrl(
            @PathVariable String shortCode) {

        UrlMapping mapping = urlService.getByShortCode(shortCode);

        if (mapping == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mapping);
    }

    @PutMapping("/shorten/{shortCode}")
    public ResponseEntity<UrlMapping> updateShortUrl(
            @PathVariable String shortCode,
            @Valid @RequestBody UpdateUrlRequest request) {

        UrlMapping mapping =
                urlService.updateShortUrl(shortCode, request.getUrl());

        if (mapping == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mapping);
    }

    @DeleteMapping("/shorten/{shortCode}")
    public ResponseEntity<Void> deleteShortUrl(
            @PathVariable String shortCode) {

        boolean deleted = urlService.deleteShortUrl(shortCode);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/shorten/{shortCode}/stats")
    public ResponseEntity<UrlMapping> getStatistics(
            @PathVariable String shortCode) {

        UrlMapping mapping = urlService.getStatistics(shortCode);

        if (mapping == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mapping);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortCode) {

        UrlMapping mapping =
                urlService.getAndIncrementAccessCount(shortCode);

        if (mapping == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(
                        "Location",
                        mapping.getUrl()
                )
                .build();
    }
}