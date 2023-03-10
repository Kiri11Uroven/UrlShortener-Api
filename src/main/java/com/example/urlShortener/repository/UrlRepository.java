package com.example.urlShortener.repository;

import com.example.urlShortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> {

    Url findByOriginalUrl(String origlink);

}
