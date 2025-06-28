package com.spring.blog.controller;

import com.spring.blog.model.Article;
import com.spring.blog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = articleService.getById(id);
        if (article == null) return "not-found";
        model.addAttribute("article", article);
        return "article";
    }
    // для формы создания статьи - показывает пустую форму (мы кладём в модель новый Article)
    @GetMapping("/new")
    public String showNewArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "new-article";
    }
    // получает заполненный объект Article, добавляет его в память и делает редирект на главную страницу
    @PostMapping("/new")
    public String createArticle(@ModelAttribute Article article) {
        articleService.addArticle(article);
        return "redirect:/";
    }
}