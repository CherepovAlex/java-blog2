package com.spring.blog.controller;

import com.spring.blog.model.Article;
import com.spring.blog.repository.ArticleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/{id}")
    public String viewArticle(@PathVariable Long id, Model model) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            model.addAttribute("article", article.get());
            return "article";
        } else {
            return "not-found";
        }
    }

    @GetMapping("/all")
    public String listArticles(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/source/{source}")
    public String getSourcePage(@PathVariable String source, Model model) {
        List<Article> articles = articleRepository.findAll()
                .stream()
                .filter(a -> source.equalsIgnoreCase(a.getSource()))
                .toList();
        model.addAttribute("articles", articles);
        model.addAttribute("source", source);
        return "source";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String showNewArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "new-article";
    }

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
                    Path path = Paths.get("uploads", filename);
                    Files.createDirectories(path.getParent());
                    Files.write(path, image.getBytes());
                    savedImagePaths.add("/uploads/" + filename);
                }
            }
        }

        article.setPublishedDate(LocalDate.now());
        article.setCodeBlocks(savedCodeBlocks);
        article.setImages(savedImagePaths);
        article.setCreatedAt(LocalDateTime.now());

        Article saved = articleRepository.save(article);
        return "redirect:/article/" + saved.getId();
    }
}