package com.example.demo1.controller;

import com.example.demo1.model.Article;
import com.example.demo1.model.IDao;
import com.example.demo1.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/app")
public class FrontController {

    private final ArticleService articleService;

    public FrontController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping({"", "/"})
    public String listArticles(Model model, @RequestParam(value = "message", required = false) String message,
                               @RequestParam(value = "error", required = false) String error, @RequestParam(value = "action", required = false) String action) {
        List<Article> articles = articleService.getAllArticles();
        model.addAttribute("articles", articles);
        if (message != null) model.addAttribute("message", message);
        if (error != null) model.addAttribute("error", error);

        return "listeArticles";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("article", new Article());
        return "Article";
    }

    @PostMapping
    public String createArticle(@RequestParam String code, @RequestParam String designation,
                                @RequestParam double prix, RedirectAttributes redirectAttributes) {
        Article article = new Article(code, designation, prix);
        articleService.saveArticle(article, redirectAttributes);
        return "redirect:/app";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam String code, Model model, RedirectAttributes redirectAttributes) {
        Article article = articleService.getArticleByCode(code);

        if (article != null) {
            model.addAttribute("article", article);
            return "EditArticle";
        } else {
            redirectAttributes.addAttribute("error", "Article non trouvé!");
            return "redirect:/app";
        }
    }


    @PostMapping("/update")
    public String updateArticle(@RequestParam String code, @RequestParam String designation,
                                @RequestParam double prix, RedirectAttributes redirectAttributes) {

        Article article = new Article(code, designation, prix);
        articleService.updateArticle(article, redirectAttributes);

        return "redirect:/app";
    }

    @GetMapping("/delete")
    public String deleteArticle(@RequestParam String code, RedirectAttributes redirectAttributes) {
        boolean success = articleService.deleteArticle(code);

        if (success) {
            redirectAttributes.addAttribute("message", "Article supprimé avec succès!");
        } else {
            redirectAttributes.addAttribute("error", "Erreur lors de la suppression!");
        }

        return "redirect:/app";
    }
}
