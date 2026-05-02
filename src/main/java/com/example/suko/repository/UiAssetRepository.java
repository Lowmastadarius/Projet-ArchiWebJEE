package com.example.suko.repository;

import com.example.suko.entity.UiAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UiAssetRepository extends JpaRepository<UiAsset, Long> {
    Optional<UiAsset> findByAssetKey(String assetKey);
}
