package com.example.URLShortener.service;

import com.example.URLShortener.models.URL;
import com.example.URLShortener.repository.URLRepository;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class UrlServiceImplement implements UrlService{
    @Autowired
    private URLRepository urlRepository;

    @Override
    public String generateShortUrl(String url) {
        String shortUrl = Hashing.murmur3_32().hashString(url, Charset.defaultCharset()).toString();
        return shortUrl;
    }

    @Override
    public URL getEncodedUrl(String shortUrl) {
        URL longUrlToRet = urlRepository.findByShortUrl(shortUrl);
        return longUrlToRet;
    }

    @Override
    public void deleteShortLink(URL url) {

        urlRepository.delete(url);
    }

    @Override
    public boolean checkShortUrl(String shortUrl) {

        return urlRepository.findByShortUrl(shortUrl) != null;
    }

    @Override
    public String changeShortUrl(String shortUrl) {
        StringBuilder sb = new StringBuilder(shortUrl).delete(1,3).append((int)(Math.random()*100));
        return sb.toString();
    }
}
