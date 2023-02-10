package com.example.URLShortener.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Table(name = "url")
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "shortUrl")
    private String shortUrl;
    @Column(name = "longUrl", columnDefinition = "varchar(3000)")
    @NotBlank
    private String longUrl;
    @Column(name = "time")
    private Long millis = System.currentTimeMillis();
}
