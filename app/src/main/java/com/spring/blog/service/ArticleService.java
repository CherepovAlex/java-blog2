package com.spring.blog.service;

import com.spring.blog.model.Article;
import com.spring.blog.repository.ArticleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// создаём временное хранилище
@Service
public class ArticleService {

    private final List<Article> articles = new ArrayList<>();

    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;

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
    public void addArticle(Article article, List<String> codeBlocks, List<String> imagePaths) {
        article.setPublishedDate(LocalDate.now());
        article.setCodeBlocks(codeBlocks);
        article.setImages(imagePaths);
        article.setCreatedAt(LocalDateTime.now());
        articleRepository.save(article);
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

    public List<Article> getLatestArticles(int count) {
        return articleRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, count));
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    public Article save(Article article) {
        return articleRepository.save(article);
    }

}
