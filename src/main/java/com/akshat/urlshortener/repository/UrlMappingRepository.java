package com.akshat.urlshortener.repository;

import com.akshat.urlshortener.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long>{

    Optional<UrlMapping> findByShortCode(String shortCode);

    }

