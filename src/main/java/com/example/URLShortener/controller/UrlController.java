package com.example.URLShortener.controller;

import com.example.URLShortener.models.URL;
import com.example.URLShortener.repository.URLRepository;
import com.example.URLShortener.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

@Controller
public class UrlController extends SQLException {
    @Autowired
    private URLRepository urlRepository;
    @Autowired
    private UrlService urlService;

    @GetMapping("/guest")
    public String getUrlShortnerPage(Model model) {
        model.addAttribute("url", new URL());
        return "guest";
    }
    @GetMapping("/your_urls/{shortUrl}")
    public String getAllUrl(@PathVariable(name = "shortUrl") String shortUrl, Model model) {
        URL url = urlRepository.findByShortUrl(shortUrl);

        model.addAttribute("shortUrl", url.getShortUrl());
        model.addAttribute("longUrl", url.getLongUrl());

        return "your_urls";
    }

    @PostMapping("/shorter")
    public String shortUrl(@ModelAttribute("url") @Valid URL url, BindingResult bindingResult, Model model) {
        URL u = (URL) model.getAttribute("url");

        if (bindingResult.hasErrors() || u == null) {
            return "redirect:/guest";
        }
        if (u.getShortUrl().equals("")) {  // норм так делать?
            String shortUrl = urlService.generateShortUrl(u.getLongUrl());
            while (urlService.checkShortUrl(shortUrl)) {
                shortUrl = urlService.changeShortUrl(shortUrl);
            }
            u.setShortUrl(shortUrl);
        }
        model.addAttribute("short", u.getShortUrl());
        try{
            urlRepository.save(u);
        }
        catch (Exception exeption){ // не получается вставаить SqLExeption  и close()
            return "redirect:/guest";
        }
        return "redirect:/your_urls/"+u.getShortUrl();
    }

    @GetMapping("/guest/{link}")
    public String getPageWithShortURL(@PathVariable(name = "link") String link) {
        URL url = urlRepository.findByShortUrl(link);
        return "redirect:" + url.getLongUrl();
    }

    @GetMapping("{link}")
    public String getPageWithLongURL(@PathVariable(name = "link") String link) {
        return "redirect:" + link;
    }
}
