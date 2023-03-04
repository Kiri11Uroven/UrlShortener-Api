package com.example.urlShortener.service;

import com.example.urlShortener.dto.UrlDto;
import com.example.urlShortener.entity.Url;

@org.springframework.stereotype.Service
public interface Service {

    Url getOriginalUrl(String shortUrl);

    Url convertToShort(UrlDto urlDto);

}
