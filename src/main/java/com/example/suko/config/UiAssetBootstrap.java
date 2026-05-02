package com.example.suko.config;

import com.example.suko.service.UiAssetService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class UiAssetBootstrap {

    private static final Logger LOGGER = Logger.getLogger(UiAssetBootstrap.class.getName());
    private final UiAssetService uiAssetService;

    public UiAssetBootstrap(UiAssetService uiAssetService) {
        this.uiAssetService = uiAssetService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void loadAssetsFromImagesFolder() {
        String catalinaBase = System.getProperty("catalina.base", "");
        List<Path> candidateDirs = List.of(
                Paths.get("images"),
                catalinaBase.isBlank() ? Paths.get("images") : Paths.get(catalinaBase, "images"),
                Paths.get(System.getProperty("user.dir", "."), "images"),
                Paths.get("E:/Projects/ProjectMigrated/images")
        );

        Path baseDir = candidateDirs.stream()
                .filter(Files::exists)
                .filter(Files::isDirectory)
                .findFirst()
                .orElse(null);

        if (baseDir != null) {
            LOGGER.info("UI assets loaded from directory: " + baseDir.toAbsolutePath());
        } else {
            LOGGER.warning("No external images directory found. Fallback to classpath /images.");
        }

        Map<String, String> files = Map.of(
                "background.png", "background.png",
                "hourglass.png", "hourglass.png",
                "howtoicon.png", "howtoicon.png"
        );

        files.forEach((assetKey, fileName) -> {
            try {
                byte[] bytes;
                String contentType;

                if (baseDir != null && Files.exists(baseDir.resolve(fileName))) {
                    Path imagePath = baseDir.resolve(fileName);
                    bytes = Files.readAllBytes(imagePath);
                    contentType = Files.probeContentType(imagePath);
                } else {
                    ClassPathResource resource = new ClassPathResource("images/" + fileName);
                    if (!resource.exists()) {
                        LOGGER.warning("Missing UI asset: " + fileName);
                        return;
                    }
                    try (InputStream inputStream = resource.getInputStream()) {
                        bytes = inputStream.readAllBytes();
                    }
                    contentType = Files.probeContentType(Paths.get(fileName));
                }

                if (contentType == null) {
                    contentType = "image/png";
                }
                uiAssetService.upsert(assetKey, contentType, bytes);
            } catch (IOException ex) {
                LOGGER.warning("Unable to load UI asset " + fileName + ": " + ex.getMessage());
            }
        });
    }
}
