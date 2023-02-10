package com.example.URLShortener;

import com.example.URLShortener.models.URL;
import com.example.URLShortener.repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShedulerMillis {
    @Autowired
    URLRepository urlRepository;

    @Scheduled(cron="@daily") // every day
    public void wakeUp() {
        long minMillis = System.currentTimeMillis() - 30*24*3600;  // older than a month
        List<URL> oldUrlsToDelete = urlRepository.findByMillisLessThan(minMillis);
        urlRepository.deleteAll(oldUrlsToDelete);
    }
}
