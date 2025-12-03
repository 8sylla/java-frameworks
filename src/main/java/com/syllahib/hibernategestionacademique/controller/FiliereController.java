package com.syllahib.hibernategestionacademique.controller;

import com.syllahib.hibernategestionacademique.model.Eleve;
import com.syllahib.hibernategestionacademique.model.Filiere;
import com.syllahib.hibernategestionacademique.service.EleveService;
import com.syllahib.hibernategestionacademique.service.FiliereService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Contrôleur pour gérer les opérations CRUD sur les Filières
 */
@WebServlet("/filieres")
public class FiliereController extends HttpServlet {

    private FiliereService filiereService;
    private EleveService eleveService;

    @Override
    public void init() throws ServletException {
        super.init();
        filiereService = new FiliereService();
        eleveService = new EleveService();
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
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteFiliere(request, response);
                    break;
                default:
                    listFilieres(request, response);
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
                createFiliere(request, response);
            } else if ("update".equals(action)) {
                updateFiliere(request, response);
            } else {
                listFilieres(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Afficher la liste des filières
     */
    private void listFilieres(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Filiere> filieres = filiereService.findAll();
//        List<Eleve> eleves = eleveService.findByFiliere()
        request.setAttribute("filieres", filieres);
        request.setAttribute("totalFilieres", filieres.size());
        request.getRequestDispatcher("/WEB-INF/views/filiere/list.jsp").forward(request, response);
    }

    /**
     * Afficher le formulaire de création
     */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/filiere/form.jsp").forward(request, response);
    }

    /**
     * Afficher le formulaire d'édition
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Filiere filiere = filiereService.findById(id);

        if (filiere == null) {
            request.setAttribute("errorMessage", "Filière non trouvée");
            listFilieres(request, response);
            return;
        }

        request.setAttribute("filiere", filiere);
        request.getRequestDispatcher("/WEB-INF/views/filiere/form.jsp").forward(request, response);
    }

    /**
     * Créer une nouvelle filière
     */
    private void createFiliere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");

        Filiere filiere = new Filiere();
        filiere.setCode(code);
        filiere.setNom(nom);
        filiere.setDescription(description);

        try {
            filiereService.createFiliere(filiere);
            request.getSession().setAttribute("successMessage", "Filière créée avec succès");
            response.sendRedirect(request.getContextPath() + "/filieres?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("filiere", filiere);
            request.getRequestDispatcher("/WEB-INF/views/filiere/form.jsp").forward(request, response);
        }
    }

    /**
     * Mettre à jour une filière
     */
    private void updateFiliere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        String code = request.getParameter("code");
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");

        Filiere filiere = new Filiere();
        filiere.setId(id);
        filiere.setCode(code);
        filiere.setNom(nom);
        filiere.setDescription(description);

        try {
            filiereService.updateFiliere(filiere);
            request.getSession().setAttribute("successMessage", "Filière mise à jour avec succès");
            response.sendRedirect(request.getContextPath() + "/filieres?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("filiere", filiere);
            request.getRequestDispatcher("/WEB-INF/views/filiere/form.jsp").forward(request, response);
        }
    }

    /**
     * Supprimer une filière
     */
    private void deleteFiliere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));

        try {
            filiereService.deleteFiliere(id);
            request.getSession().setAttribute("successMessage", "Filière supprimée avec succès");
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/filieres?action=list");
    }
}
