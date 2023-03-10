package com.example.urlShortener.service;

import com.example.urlShortener.dto.UrlDto;
import com.example.urlShortener.entity.Url;
import com.example.urlShortener.exeptionsHandler.NoSuchUrlException;
import com.example.urlShortener.exeptionsHandler.UrlExpiredException;
import com.example.urlShortener.exeptionsHandler.UrlOverflowException;
import com.example.urlShortener.repository.UrlRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Api("Сервисный класс содержащий методы переобразования ссылок")
@org.springframework.stereotype.Service
public class UrlService implements Service {

    private final UrlRepository urlRepository;

    private final Conversion conversion;

    @Autowired
    public UrlService(UrlRepository urlRepository, Conversion conversion) {
        this.urlRepository = urlRepository;
        this.conversion = conversion;
    }

    @ApiOperation("Метод преобразующий коротку ссылку в оригинальную")
    @Override
    public Url getOriginalUrl(String shortUrl)  {
        Url originalUrl = urlRepository.findById((int) conversion.decode(shortUrl))
                .orElseThrow(() -> new NoSuchUrlException("Такого Url нет - " + shortUrl));
        if (originalUrl.getCreateDate().plusMinutes(10).plusSeconds(1).isBefore(LocalDateTime.now())) { // Проверка времени доступности ссылки
            throw new UrlExpiredException("Время жизни ссылки истекло!");
        }
        return originalUrl;
    }

    @ApiOperation("Метод добавляющий новый обьект с оригинальной ссылкой в таблицу")
    @Override
    public Url addOriginalUrl(UrlDto urlDto) {
        if (urlRepository.count() < 101) {  // Проверка количества обьектов в таблице, если больше 100, новые обьекты не будут добавлены
            Url url1 = urlRepository.findByOriginalUrl(urlDto.getOriginalUrl());  // Проверка наличия одинакового обьекта в таблице, реализация идемпотентности операции
            if (url1 == null) {
                Url url = new Url();
                url.setCreateDate(LocalDateTime.now());
                url.setOriginalUrl(urlDto.getOriginalUrl());
                urlRepository.save(url);
                System.out.println("localhost:8080/go-to/" + conversion.encode(url.getId())); // Вывод в консоль короткой ссылки
                return url1;
            } else {
                System.out.println("localhost:8080/go-to/" + conversion.encode(url1.getId())); // Вывод в консоль короткой ссылки
                return url1;
            }
        } else throw new UrlOverflowException("Невозможно создать больше 100 ссылок");
    }
}
