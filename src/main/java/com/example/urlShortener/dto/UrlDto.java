package com.example.urlShortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Обьект для Post метода")
public class UrlDto {
    @NotNull
    @Pattern(regexp = "https://.*", message = "Введите ссылку в формате \"https://...\"")
    private String originalUrl;

    private int id;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setId(int id) {
        this.id = id;
    }
}
