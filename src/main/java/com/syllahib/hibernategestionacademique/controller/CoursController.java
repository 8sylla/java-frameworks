package com.syllahib.hibernategestionacademique.controller;

import com.syllahib.hibernategestionacademique.model.Cours;
import com.syllahib.hibernategestionacademique.service.CoursService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Contrôleur pour gérer les opérations CRUD sur les Cours
 */
@WebServlet("/cours")
public class CoursController extends HttpServlet {

    private CoursService coursService;

    @Override
    public void init() throws ServletException {
        super.init();
        coursService = new CoursService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "list":
                    listCours(request, response);
                    break;
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteCours(request, response);
                    break;
                default:
                    listCours(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                createCours(request, response);
            } else if ("update".equals(action)) {
                updateCours(request, response);
            } else {
                listCours(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Afficher la liste des cours
     */
    private void listCours(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cours> coursList = coursService.findAll();
        request.setAttribute("coursList", coursList);
        request.setAttribute("totalCours", coursList.size());
        request.getRequestDispatcher("/WEB-INF/views/cours/list.jsp").forward(request, response);
    }

    /**
     * Afficher le formulaire de création
     */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/cours/form.jsp").forward(request, response);
    }

    /**
     * Afficher le formulaire d'édition
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Cours cours = coursService.findById(id);

        if (cours == null) {
            request.setAttribute("errorMessage", "Cours non trouvé");
            listCours(request, response);
            return;
        }

        request.setAttribute("cours", cours);
        request.getRequestDispatcher("/WEB-INF/views/cours/form.jsp").forward(request, response);
    }

    /**
     * Créer un nouveau cours
     */
    private void createCours(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");
        String intitule = request.getParameter("intitule");
        String description = request.getParameter("description");
        String creditsStr = request.getParameter("credits");

        Cours cours = new Cours();
        cours.setCode(code);
        cours.setIntitule(intitule);
        cours.setDescription(description);

        if (creditsStr != null && !creditsStr.isEmpty()) {
            cours.setCredits(Integer.parseInt(creditsStr));
        }

        try {
            coursService.createCours(cours);
            request.getSession().setAttribute("successMessage", "Cours créé avec succès");
            response.sendRedirect(request.getContextPath() + "/cours?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("cours", cours);
            request.getRequestDispatcher("/WEB-INF/views/cours/form.jsp").forward(request, response);
        }
    }

    /**
     * Mettre à jour un cours
     */
    private void updateCours(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        String code = request.getParameter("code");
        String intitule = request.getParameter("intitule");
        String description = request.getParameter("description");
        String creditsStr = request.getParameter("credits");

        Cours cours = new Cours();
        cours.setId(id);
        cours.setCode(code);
        cours.setIntitule(intitule);
        cours.setDescription(description);

        if (creditsStr != null && !creditsStr.isEmpty()) {
            cours.setCredits(Integer.parseInt(creditsStr));
        }

        try {
            coursService.updateCours(cours);
            request.getSession().setAttribute("successMessage", "Cours mis à jour avec succès");
            response.sendRedirect(request.getContextPath() + "/cours?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("cours", cours);
            request.getRequestDispatcher("/WEB-INF/views/cours/form.jsp").forward(request, response);
        }
    }

    /**
     * Supprimer un cours
     */
    private void deleteCours(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));

        try {
            coursService.deleteCours(id);
            request.getSession().setAttribute("successMessage", "Cours supprimé avec succès");
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/cours?action=list");
    }
}
