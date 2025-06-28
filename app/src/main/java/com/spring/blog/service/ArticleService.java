package com.spring.blog.service;

import com.spring.blog.model.Article;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// создаём временное хранилище
@Service
public class ArticleService {

    private final List<Article> articles = new ArrayList<>();

    public ArticleService() {
        // Пример одной статьи
        Article a = new Article();
        a.setId(1L);
        a.setTitle("Введение в Java");
        a.setContent("Это первая статья...");
        a.setPublishedDate(LocalDate.now());
        a.setSource("JavaBook");
        articles.add(a);

        Article b = new Article();
        b.setId(2L);
        b.setTitle("Введение не в Java");
        b.setContent("Это вторая статья...");
        b.setPublishedDate(LocalDate.now());
        b.setSource("JavaArticle");
        articles.add(b);
    }
    // добавление статьи
    public void addArticle(Article article) {
        article.setId((long) (articles.size() + 1)); // простой автоинкремент
        article.setPublishedDate(LocalDate.now());
        articles.add(article);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public Article getById(Long id) {
        return articles.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    // добавим больше статей
    public List<Article> getArticleBySource(String source) {
        return articles.stream()
                .filter(a -> a.getSource().equalsIgnoreCase(source))
                .toList();
    }
    // добавим группировку по источнику
    public List<String> getSource() {
        return articles.stream()
                .map(Article::getSource)
                .distinct()
                .toList();
    }

}
