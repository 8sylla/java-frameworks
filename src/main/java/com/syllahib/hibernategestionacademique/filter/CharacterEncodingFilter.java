package com.syllahib.hibernategestionacademique.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/**
    * Filtre pour forcer l'encodage UTF-8 sur toutes les requêtes et réponses
    * Compatible avec tous les serveurs (Tomcat, WildFly, JBoss, etc.)
 */
@WebFilter(urlPatterns = "/*")
public class CharacterEncodingFilter implements Filter {

    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null && !encodingParam.trim().isEmpty()) {
            this.encoding = encodingParam;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Forcer l'encodage UTF-8 pour la requête
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(encoding);
        }

        // Forcer l'encodage UTF-8 pour la réponse
        response.setCharacterEncoding(encoding);
        response.setContentType("text/html; charset=" + encoding);

        // Continuer la chaîne de filtres
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Rien à nettoyer
    }
}
