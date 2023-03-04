package com.example.urlShortener.controller;


import com.example.urlShortener.dto.UrlDto;
import com.example.urlShortener.entity.Url;
import com.example.urlShortener.service.Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Контроллер ссылок", description = "Содержит Endpoint'ы для преобразования ссылок")
@RestController
@RequestMapping("/")
public class Controller {

    private final Service urlService;

    @Autowired
    public Controller(Service urlService) {
        this.urlService = urlService;
    }

    @Operation(summary = "Преобразователь", description = "Преобразует длинную ссылку в короткую и добавляет обьект Url в таблицу")
    @PostMapping("/create/ShortUrl")
    public Url generateShortUrl(@RequestBody @Valid UrlDto urlDto) {
        return urlService.convertToShort(urlDto);
    }

    @Operation(summary = "Конвертер", description = "Преобразует коротку ссылку в оригинальную и переходит по этой ссылке")
    @GetMapping(value = "go-to/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable @Parameter(description = "Передаваемый идентификатор url") String shortUrl) {
        Url url = urlService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getOriginalUrl())).build();
    }

    @Operation(summary = "Получение обьекта с оригинальной ссылкой")
    @GetMapping(value = "get/OriginalUrl/{shortUrl}")
    public Url getOriginalUrl(@PathVariable @Parameter(description = "Передаваемый идентификатор url") String shortUrl) {
        Url url = urlService.getOriginalUrl(shortUrl);
        return url;
    }
}
