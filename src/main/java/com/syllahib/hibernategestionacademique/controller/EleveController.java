package com.syllahib.hibernategestionacademique.controller;

import com.syllahib.hibernategestionacademique.model.Cours;
import com.syllahib.hibernategestionacademique.model.DossierAdministratif;
import com.syllahib.hibernategestionacademique.model.Eleve;
import com.syllahib.hibernategestionacademique.model.Filiere;
import com.syllahib.hibernategestionacademique.service.CoursService;
import com.syllahib.hibernategestionacademique.service.DossierAdministratifService;
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
 * Contrôleur pour gérer les opérations CRUD sur les Élèves
 */
@WebServlet("/eleves")
public class EleveController extends HttpServlet {

    private EleveService eleveService;
    private FiliereService filiereService;
    private CoursService coursService;

    private DossierAdministratifService dossierAdministratifService;

    @Override
    public void init() throws ServletException {
        super.init();
        eleveService = new EleveService();
        filiereService = new FiliereService();
        coursService = new CoursService();
        dossierAdministratifService = new DossierAdministratifService();
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
                    deleteEleve(request, response);
                    break;
                case "view":
                    viewEleve(request, response);
                    break;
                default:
                    listEleves(request, response);
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
                createEleve(request, response);
            } else if ("update".equals(action)) {
                updateEleve(request, response);
            } else {
                listEleves(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Afficher la liste des élèves
     */
    private void listEleves(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filiereIdParam = request.getParameter("filiereId");
        List<Eleve> eleves;

        if (filiereIdParam != null && !filiereIdParam.isEmpty()) {
            Long filiereId = Long.parseLong(filiereIdParam);
            eleves = eleveService.findByFiliere(filiereId);
            Filiere filiere = filiereService.findById(filiereId);
            request.setAttribute("selectedFiliere", filiere);
        } else {
            eleves = eleveService.findAll();
        }

        request.setAttribute("eleves", eleves);
        request.setAttribute("totalEleves", eleves.size());
        request.setAttribute("filieres", filiereService.findAll());
        request.getRequestDispatcher("/WEB-INF/views/eleve/list.jsp").forward(request, response);
    }

    /**
     * Afficher le formulaire de création
     */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Filiere> filieres = filiereService.findAll();
        request.setAttribute("filieres", filieres);
        request.getRequestDispatcher("/WEB-INF/views/eleve/form.jsp").forward(request, response);
    }

    /**
     * Afficher le formulaire d'édition
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Eleve eleve = eleveService.findById(id);

        if (eleve == null) {
            request.getSession().setAttribute("errorMessage", "Élève non trouvé");
            response.sendRedirect(request.getContextPath() + "/eleves?action=list");
            return;
        }

        List<Filiere> filieres = filiereService.findAll();
        request.setAttribute("eleve", eleve);
        request.setAttribute("filieres", filieres);
        request.getRequestDispatcher("/WEB-INF/views/eleve/form.jsp").forward(request, response);
    }

    /**
     * Voir les détails d'un élève
     */
    private void viewEleve(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Eleve eleve = eleveService.findById(id);


        if (eleve == null) {
            request.getSession().setAttribute("errorMessage", "Élève non trouvé");
            response.sendRedirect(request.getContextPath() + "/eleves?action=list");
            return;
        }
        System.out.println(eleve);
        Filiere filiere = filiereService.findById(eleve.getFiliere().getId());
        List<Cours> courses = coursService.findByEleve(id);
        DossierAdministratif dossierAdministratif = dossierAdministratifService.findByEleveId(eleve.getId());
        request.setAttribute("eleve", eleve);
        request.setAttribute("filiere", filiere);
        request.setAttribute("courses", courses);
        request.setAttribute("dossierAdministratif", dossierAdministratif);
        request.getRequestDispatcher("/WEB-INF/views/eleve/view.jsp").forward(request, response);
    }

    /**
     * Créer un nouvel élève
     */
    private void createEleve(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String matricule = request.getParameter("matricule");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        Long filiereId = Long.parseLong(request.getParameter("filiereId"));

        Eleve eleve = new Eleve();
        eleve.setMatricule(matricule);
        eleve.setNom(nom);
        eleve.setPrenom(prenom);
        eleve.setEmail(email);

        Filiere filiere = new Filiere();
        filiere.setId(filiereId);
        eleve.setFiliere(filiere);

        try {
            eleveService.createEleve(eleve);
            request.getSession().setAttribute("successMessage", "Élève créé avec succès");
            response.sendRedirect(request.getContextPath() + "/eleves?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("eleve", eleve);
            request.setAttribute("filieres", filiereService.findAll());
            request.getRequestDispatcher("/WEB-INF/views/eleve/form.jsp").forward(request, response);
        }
    }

    /**
     * Mettre à jour un élève
     */
    private void updateEleve(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        String matricule = request.getParameter("matricule");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        Long filiereId = Long.parseLong(request.getParameter("filiereId"));

        Eleve eleve = new Eleve();
        eleve.setId(id);
        eleve.setMatricule(matricule);
        eleve.setNom(nom);
        eleve.setPrenom(prenom);
        eleve.setEmail(email);

        Filiere filiere = new Filiere();
        filiere.setId(filiereId);
        eleve.setFiliere(filiere);

        try {
            eleveService.updateEleve(eleve);
            request.getSession().setAttribute("successMessage", "Élève mis à jour avec succès");
            response.sendRedirect(request.getContextPath() + "/eleves?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("eleve", eleve);
            request.setAttribute("filieres", filiereService.findAll());
            request.getRequestDispatcher("/WEB-INF/views/eleve/form.jsp").forward(request, response);
        }
    }

    /**
     * Supprimer un élève
     */
    private void deleteEleve(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));

        try {
            eleveService.deleteEleve(id);
            request.getSession().setAttribute("successMessage", "Élève supprimé avec succès");
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/eleves?action=list");
    }
}