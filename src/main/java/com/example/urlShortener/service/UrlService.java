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
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${UrlService.dateTimeExpiredInMinutes}")
    private int dateTimeExpiredInMinutes;
    @Value("${UrlService.maxCountOfUrls}")
    private long maxCountOfUrls;


    @ApiOperation("Метод проверяющий доступность ссылки по времени")
    private void isTimeExpired(LocalDateTime date) {
        if (date.plusMinutes(dateTimeExpiredInMinutes).plusSeconds(1).isBefore(LocalDateTime.now())) { // Сейчас время жизни сслыки установлено на 10 минту включительно
            throw new UrlExpiredException("Время жизни ссылки истекло!");
        }
    }

    @ApiOperation("метод создания нового обьекта Url для добавления в таблицу")
    private Url createNewUrlObject(UrlDto urlDto) {
        Url url = new Url();
        url.setCreationDate(LocalDateTime.now());
        url.setOriginalUrl(urlDto.getOriginalUrl());
        return url;
    }

    @ApiOperation(value = "Метод возвращающий обьект Url с оригинальной ссылкой ")
    @Override
    public Url getOriginalUrl(String shortUrl) {
        Url originalUrl = urlRepository
                .findById((int) conversion.decode(shortUrl))
                .orElseThrow(() -> new NoSuchUrlException("Такого Url нет - " + shortUrl));
        isTimeExpired(originalUrl.getCreationDate());
        return originalUrl;
    }

    @ApiOperation("Метод добавляющий только новые обьекты с оригинальной ссылкой в таблицу")
    @Override
    public Url addOriginalUrl(UrlDto urlDto) {
        if (urlRepository.count() < maxCountOfUrls) {
            Url newUrl = urlRepository.findByOriginalUrl(urlDto.getOriginalUrl()); // Проверка наличия одинакового обьекта в таблице, реализация идемпотентности операции
            if (newUrl == null) {
                Url url = createNewUrlObject(urlDto);
                urlRepository.save(url);
                System.out.println("localhost:8080/go-to/" + conversion.encode(url.getId()));
            }
            return newUrl;
        } else throw new UrlOverflowException("Невозможно создать больше 100 ссылок");
    }
}
