package com.example.URLShortener;

import com.example.URLShortener.models.URL;
import com.example.URLShortener.repository.URLRepository;
import com.example.URLShortener.service.UrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class TestDatabase {
    @Autowired
    URLRepository urlRepository;

    @Autowired
    UrlService urlService;


    @Test
    public void urlInDatabase() throws Exception {
        URL url = new URL();
        url.setLongUrl("https://github.com/iralica/URL_Shortener");
        String shortUrl = urlService.generateShortUrl(url.getLongUrl());
        while (urlService.checkShortUrl(shortUrl)) {
            shortUrl = urlService.changeShortUrl(shortUrl);
        }
        url.setShortUrl(shortUrl);
        urlRepository.save(url);
        System.out.println(urlRepository.findByShortUrl(shortUrl));

        assertEquals(url, urlRepository.findByShortUrl(shortUrl));
    }


    @Test
    public void theSameLengthUrlInDatabase() throws Exception {
        URL url = new URL();
        url.setLongUrl("https://habr.com/ru/post/5862/");
        String shortUrl = urlService.generateShortUrl(url.getLongUrl());
        while (urlService.checkShortUrl(shortUrl) ){
            shortUrl = urlService.changeShortUrl(shortUrl);
        }
        url.setShortUrl(shortUrl);
        urlRepository.save(url);

        URL url2 = new URL();
        url2.setLongUrl("https://github.com/iralica/URL_Shortener");
        String shortUrl2 = urlService.generateShortUrl(url2.getLongUrl());
        while (urlService.checkShortUrl(shortUrl2) ){
            shortUrl2 = urlService.changeShortUrl(shortUrl2);
        }
        url2.setShortUrl(shortUrl2);
        urlRepository.save(url2);
        List<URL> list = urlRepository.findAllByLongUrl("https://github.com/iralica/URL_Shortener");
        Assertions.assertEquals(list.size(), list.stream().map(URL::getShortUrl).collect(Collectors.toSet()).size());

    }
}
