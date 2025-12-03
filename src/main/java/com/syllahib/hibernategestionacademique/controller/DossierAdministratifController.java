package com.syllahib.hibernategestionacademique.controller;

import com.syllahib.hibernategestionacademique.model.DossierAdministratif;
import com.syllahib.hibernategestionacademique.model.Eleve;
import com.syllahib.hibernategestionacademique.service.DossierAdministratifService;
import com.syllahib.hibernategestionacademique.service.EleveService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Contrôleur pour gérer les opérations CRUD sur les Dossiers Administratifs
 */
@WebServlet("/dossiers")
public class DossierAdministratifController extends HttpServlet {

    private DossierAdministratifService dossierService;
    private EleveService eleveService;

    @Override
    public void init() throws ServletException {
        super.init();
        dossierService = new DossierAdministratifService();
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
                case "list":
                    listDossiers(request, response);
                    break;
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteDossier(request, response);
                    break;
                case "view":
                    viewDossier(request, response);
                    break;
                default:
                    listDossiers(request, response);
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
                createDossier(request, response);
            } else if ("update".equals(action)) {
                updateDossier(request, response);
            } else {
                listDossiers(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listDossiers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<DossierAdministratif> dossiers = dossierService.findAll();
        request.setAttribute("dossiers", dossiers);
        request.setAttribute("totalDossiers", dossiers.size());
        request.getRequestDispatcher("/WEB-INF/views/dossier/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Eleve> eleves = eleveService.findAll();
        request.setAttribute("eleves", eleves);
        request.getRequestDispatcher("/WEB-INF/views/dossier/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        DossierAdministratif dossier = dossierService.findById(id);

        if (dossier == null) {
            request.getSession().setAttribute("errorMessage", "Dossier non trouvé");
            response.sendRedirect(request.getContextPath() + "/dossiers?action=list");
            return;
        }

        request.setAttribute("dossier", dossier);
        request.getRequestDispatcher("/WEB-INF/views/dossier/form.jsp").forward(request, response);
    }

    private void viewDossier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        DossierAdministratif dossier = dossierService.findById(id);

        if (dossier == null) {
            request.getSession().setAttribute("errorMessage", "Dossier non trouvé");
            response.sendRedirect(request.getContextPath() + "/dossiers?action=list");
            return;
        }

        request.setAttribute("dossier", dossier);
        request.getRequestDispatcher("/WEB-INF/views/dossier/view.jsp").forward(request, response);
    }

    private void createDossier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long eleveId = Long.parseLong(request.getParameter("eleveId"));
        String statut = request.getParameter("statut");
        String remarques = request.getParameter("remarques");

        DossierAdministratif dossier = new DossierAdministratif();

        Eleve eleve = new Eleve();
        eleve.setId(eleveId);
        dossier.setEleve(eleve);
        dossier.setStatut(statut);
        dossier.setRemarques(remarques);

        try {
            dossierService.createDossier(dossier);
            request.getSession().setAttribute("successMessage", "Dossier créé avec succès");
            response.sendRedirect(request.getContextPath() + "/dossiers?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("dossier", dossier);
            request.setAttribute("eleves", eleveService.findAll());
            request.getRequestDispatcher("/WEB-INF/views/dossier/form.jsp").forward(request, response);
        }
    }

    private void updateDossier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        String statut = request.getParameter("statut");
        String remarques = request.getParameter("remarques");

        DossierAdministratif dossier = dossierService.findById(id);
        if (dossier == null) {
            request.getSession().setAttribute("errorMessage", "Dossier non trouvé");
            response.sendRedirect(request.getContextPath() + "/dossiers?action=list");
            return;
        }

        dossier.setStatut(statut);
        dossier.setRemarques(remarques);

        try {
            dossierService.updateDossier(dossier);
            request.getSession().setAttribute("successMessage", "Dossier mis à jour avec succès");
            response.sendRedirect(request.getContextPath() + "/dossiers?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("dossier", dossier);
            request.getRequestDispatcher("/WEB-INF/views/dossier/form.jsp").forward(request, response);
        }
    }

    private void deleteDossier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));

        try {
            dossierService.deleteDossier(id);
            request.getSession().setAttribute("successMessage", "Dossier supprimé avec succès");
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/dossiers?action=list");
    }
}
