package com.example.suko.service;

import com.example.suko.entity.UiAsset;
import com.example.suko.repository.UiAssetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UiAssetService {

    private final UiAssetRepository uiAssetRepository;

    public UiAssetService(UiAssetRepository uiAssetRepository) {
        this.uiAssetRepository = uiAssetRepository;
    }

    public Optional<UiAsset> findByKey(String key) {
        return uiAssetRepository.findByAssetKey(key);
    }

    public void upsert(String key, String contentType, byte[] data) {
        UiAsset asset = uiAssetRepository.findByAssetKey(key).orElseGet(UiAsset::new);
        asset.setAssetKey(key);
        asset.setContentType(contentType);
        asset.setData(data);
        asset.setUpdatedAt(java.time.LocalDateTime.now());
        uiAssetRepository.save(asset);
    }
}
