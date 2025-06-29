package com.spring.blog.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// класс Статьи
@Entity
@AllArgsConstructor
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ElementCollection
    private List<String> images;
    @ElementCollection
    private List<String> codeBlocks;
    @ElementCollection
    private List<String> tasks;
    private LocalDate publishedDate;
    private String source;
    @ElementCollection
    private List<String> links;

    // инициализируем списки при создании
    public Article() {
        this.images = images;
        this.codeBlocks = codeBlocks;
    }
}
