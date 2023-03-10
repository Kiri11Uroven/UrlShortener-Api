package com.example.urlShortener.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "urldata", catalog = "postgres", schema = "public")
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "original link", nullable = false)
    private String originalUrl;

    @Column(name = "create date", nullable = false)
    private LocalDateTime createDate;

    public Url() {
    }

    public Url(int id, String originalUrl, LocalDateTime createDate) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
