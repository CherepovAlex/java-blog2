package com.spring.blog.controller;

import com.spring.blog.model.Article;
import com.spring.blog.service.ArticleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public String viewArticle(@PathVariable Long id, Model model) {
        Optional<Article> article = articleService.findById(id);
        if (article.isPresent()) {
            model.addAttribute("article", article.get());
            return "article"; // article.html должен существовать
        } else {
            return "not-found"; // not-found.html должен существовать
        }
    }

    @PostMapping("/new")
    public String saveArticle(@ModelAttribute Article article) {
        Article saved = articleService.save(article);
        return "redirect:/article/" + saved.getId();
    }

    // для формы создания статьи - показывает пустую форму (мы кладём в модель новый Article)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String showNewArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "new-article";
    }

    // получает заполненный объект Article, добавляет его в память и делает редирект на главную страницу
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public String createArticle(
            @ModelAttribute Article article,
            @RequestParam(value = "codeBlock", required = false) List<String> codeBlocks,
            @RequestParam(value = "imageFile", required = false) List<MultipartFile> images
    ) throws IOException {
        List<String> savedImagePaths = new ArrayList<>();
        List<String> savedCodeBlocks = codeBlocks != null ? codeBlocks : new ArrayList<>();
        if (images != null) {
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    String filename = System.currentTimeMillis() + "-" + image.getOriginalFilename();
                    Path path = Paths.get("src/main/resources/static/uploads", filename);
                    Files.createDirectories(path.getParent());
                    Files.write(path, image.getBytes());

                    savedImagePaths.add("/uploads/" + filename);
                }
            }
        }
        articleService.addArticle(article, codeBlocks, savedImagePaths);
        return "redirect:/";
    }
}