package com.spring.blog.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// класс Статьи
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Article {

    private Long id;
    private String title;
    private String content;
    private List<String> images;
    private List<String> codeBlocks;
    private List<String> tasks;
    private LocalDate publishedDate;
    private String source;
    private List<String> links;


}
