package com.example.demo1.service;

import com.example.demo1.model.Article;
import com.example.demo1.model.IDao;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
public class ArticleService {
    public final IDao<Article> articleDao;

    public ArticleService(IDao<Article> articleDao) {
        this.articleDao = articleDao;
    }

    public List<Article> getAllArticles() {
        return articleDao.findAll();
    }
    public Article getArticleByCode(String code) {
        return articleDao.findByCode(code);
    }

    public void saveArticle(Article article, RedirectAttributes redirectAttributes) {
        boolean success = articleDao.create(article);

        if (success) {
            redirectAttributes.addAttribute("message", "Article créé avec succès!");
        } else {
            redirectAttributes.addAttribute("error", "Code article déjà existant!");
        }
    }

    public void updateArticle(Article article, RedirectAttributes redirectAttributes) {
        boolean success = articleDao.update(article);

        if (success) {
            redirectAttributes.addAttribute("message", "Article mis à jour avec succès!");
        } else {
            redirectAttributes.addAttribute("error", "Erreur lors de la mise à jour!");
        }
        articleDao.update(article);
    }
    public Boolean deleteArticle(String code) {
        return articleDao.delete(code);
    }

}
