package com.spring.blog.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ElementCollection
    @CollectionTable(name = "article_images", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "image_path", columnDefinition = "TEXT")
    private List<String> images = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "article_code_blocks", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "code_block", columnDefinition = "TEXT")
    private List<String> codeBlocks = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "article_tasks", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "task", columnDefinition = "TEXT")
    private List<String> tasks = new ArrayList<>();

    private LocalDate publishedDate;

    private String source;

    @ElementCollection
    @CollectionTable(name = "article_links", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "link", columnDefinition = "TEXT")
    private List<String> links = new ArrayList<>();

    public Article() {
        // инициализация коллекций уже сделана выше
    }
}