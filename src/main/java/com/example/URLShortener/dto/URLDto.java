package com.example.URLShortener.dto;

import lombok.*;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class URLDto {
    private String shortUrl;
    private String longUrl;
}