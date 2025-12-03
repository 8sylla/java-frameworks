package com.example.demo1.controller;



import com.example.demo1.model.Article;
import com.example.demo1.model.DaoArticle;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/app")
public class FrontController extends HttpServlet {
    private DaoArticle dao;

    @Override
    public void init() throws ServletException {
        dao = DaoArticle.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }



    //##################################
    //          ACTIONS
    //##################################

    private void listArticles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Article> articles = dao.findAll();
        request.setAttribute("articles", articles);
        request.getRequestDispatcher("/WEB-INF/views/listeArticles.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/Article.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        Optional<Article> article = dao.findByCode(code);

        if (article.isPresent()) {
            request.setAttribute("article", article.get());
            request.getRequestDispatcher("/WEB-INF/views/EditArticle.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Article non trouvé!");
            listArticles(request, response);
        }
    }

    private void createArticle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        String designation = request.getParameter("designation");
        double prix = Double.parseDouble(request.getParameter("prix"));

        Article article = new Article(code, designation, prix);
        boolean success = dao.create(article);

        if (success) {
            request.setAttribute("message", "Article créé avec succès!");
        } else {
            request.setAttribute("error", "Code article déjà existant!");
        }
        listArticles(request, response);
    }

    private void updateArticle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        String designation = request.getParameter("designation");
        double prix = Double.parseDouble(request.getParameter("prix"));

        Article article = new Article(code, designation, prix);
        boolean success = dao.update(article);

        if (success) {
            request.setAttribute("message", "Article mis à jour avec succès!");
        } else {
            request.setAttribute("error", "Erreur lors de la mise à jour!");
        }
        listArticles(request, response);
    }

    private void deleteArticle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        boolean success = dao.delete(code);

        if (success) {
            request.setAttribute("message", "Article supprimé avec succès!");
        } else {
            request.setAttribute("error", "Erreur lors de la suppression!");
        }
        listArticles(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null){
            listArticles(request, response);
        }else {
            switch (action) {
                case "create": createArticle(request, response);
                    break;
                case "update": updateArticle(request, response);
                    break;
                case "new": showNewForm(request, response);
                    break;
                case "edit": showEditForm(request, response);
                    break;
                case "delete": deleteArticle(request, response);
                    break;
                case "list": listArticles(request, response);
            }
        }

    }
}