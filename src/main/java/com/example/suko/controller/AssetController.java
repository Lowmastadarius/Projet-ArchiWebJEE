package com.example.suko.controller;

import com.example.suko.entity.UiAsset;
import com.example.suko.service.UiAssetService;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private final UiAssetService uiAssetService;

    public AssetController(UiAssetService uiAssetService) {
        this.uiAssetService = uiAssetService;
    }

    @GetMapping("/{key:.+}")
    public ResponseEntity<byte[]> getAsset(@PathVariable("key") String key) {
        return uiAssetService.findByKey(key)
                .map(this::okResponse)
                .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<byte[]> okResponse(UiAsset asset) {
        MediaType mediaType;
        try {
            mediaType = MediaType.parseMediaType(asset.getContentType());
        } catch (Exception ex) {
            mediaType = MediaType.IMAGE_PNG;
        }
        return ResponseEntity.ok()
                .contentType(mediaType)
                .cacheControl(CacheControl.maxAge(Duration.ofHours(2)))
                .body(asset.getData());
    }
}
