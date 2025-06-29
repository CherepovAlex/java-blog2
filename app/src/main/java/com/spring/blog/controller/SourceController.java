package com.spring.blog.controller;

import com.spring.blog.model.Article;
import com.spring.blog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/source")
public class SourceController {

    private final ArticleService articleService;

    public SourceController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{source}")
    public String getSourcePage(@PathVariable String source, Model model) {
        List<Article> articles = articleService.getArticleBySource(source);
        model.addAttribute("articles", articles);
        model.addAttribute("source", source);
        return "source";
    }
}

