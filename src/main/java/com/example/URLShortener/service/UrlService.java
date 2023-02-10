package com.example.URLShortener.service;

import com.example.URLShortener.models.URL;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {
    public String generateShortUrl(String url);
    public URL getEncodedUrl(String shortUrl);
    public  void  deleteShortLink(URL url);
    public boolean checkShortUrl(String shortUrl);
    public String changeShortUrl(String shortUrl);
}
