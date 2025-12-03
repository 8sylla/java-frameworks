<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Détails du Dossier Administratif</title>
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: rgb(227, 242, 253); color: #333; line-height: 1.6; }
    .header { background: rgb(0, 87, 163); color: white; padding: 1rem 0; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
    .header-content { max-width: 1200px; margin: 0 auto; padding: 0 2rem; }
    .header h1 { font-size: 1.5rem; font-weight: 600; }
    .nav { background-color: white; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
    .nav ul { list-style: none; display: flex; max-width: 1200px; margin: 0 auto; padding: 0 2rem; }
    .nav li { margin-right: 2rem; }
    .nav a { display: block; padding: 1rem 0; color: #667eea; text-decoration: none; font-weight: 500; transition: color 0.3s; border-bottom: 3px solid transparent; }
    .nav a:hover, .nav a.active { color: #764ba2; border-bottom-color: #764ba2; }
    .container { max-width: 900px; margin: 2rem auto; padding: 0 2rem; }
    .card { background: white; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 2rem; margin-bottom: 2rem; }
    .card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem; padding-bottom: 1rem; border-bottom: 2px solid #f0f0f0; }
    .card-header h2 { color: #667eea; font-size: 1.5rem; }
    .detail-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1.5rem; }
    .detail-item { padding: 1rem; background-color: #f8f9fa; border-radius: 6px; border-left: 4px solid #667eea; }
    .detail-item label { display: block; font-weight: 600; color: #495057; margin-bottom: 0.5rem; font-size: 0.85rem; text-transform: uppercase; }
    .detail-item .value { font-size: 1.1rem; color: #333; }
    .badge { display: inline-block; padding: 0.4rem 0.8rem; font-size: 0.85rem; font-weight: 600; border-radius: 12px; color: white; }
    .badge-success { background-color: #28a745; }
    .badge-warning { background-color: #ffc107; color: #333; }
    .badge-danger { background-color: #dc3545; }
    .badge-info { background-color: #17a2b8; }
    .section { margin-top: 2rem; }
    .section-title { color: #667eea; font-size: 1.2rem; margin-bottom: 1rem; padding-bottom: 0.5rem; border-bottom: 2px solid #f0f0f0; }
    .btn { padding: 0.6rem 1.2rem; border: none; border-radius: 4px; cursor: pointer; font-size: 0.9rem; text-decoration: none; display: inline-block; transition: all 0.3s; font-weight: 500; margin-right: 0.5rem; }
    .btn-primary { background: rgb(0, 87, 163); color: white; }
    .btn-success { background-color: #28a745; color: white; }
    .btn-secondary { background-color: #6c757d; color: white; }
    .actions { margin-top: 2rem; padding-top: 1.5rem; border-top: 2px solid #f0f0f0; }
  </style>
</head>
<body>
<header class="header">
  <div class="header-content"><h1>🎓 Gestion Académique</h1></div>
</header>

<nav class="nav">
  <ul>
    <li><a href="${pageContext.request.contextPath}/">Accueil</a></li>
    <li><a href="${pageContext.request.contextPath}/filieres?action=list">Filières</a></li>
    <li><a href="${pageContext.request.contextPath}/eleves?action=list">Élèves</a></li>
    <li><a href="${pageContext.request.contextPath}/cours?action=list">Cours</a></li>
    <li><a href="${pageContext.request.contextPath}/dossiers?action=list" class="active">Dossiers</a></li>
  </ul>
</nav>

<div class="container">
  <div class="card">
    <div class="card-header">
      <h2>📋 Détails du Dossier Administratif</h2>
      <c:choose>
        <c:when test="${dossier.statut == 'ACTIF'}">
          <span class="badge badge-success">ACTIF</span>
        </c:when>
        <c:when test="${dossier.statut == 'EN_ATTENTE'}">
          <span class="badge badge-warning">EN ATTENTE</span>
        </c:when>
        <c:otherwise>
          <span class="badge badge-danger">INACTIF</span>
        </c:otherwise>
      </c:choose>
    </div>

    <!-- Informations du Dossier -->
    <div class="detail-grid">
      <div class="detail-item">
        <label>Numéro d'Inscription</label>
        <div class="value"><span class="badge badge-info">${dossier.numeroInscription}</span></div>
      </div>

      <div class="detail-item">
        <label>Date de Création</label>
        <div class="value">
          <fmt:formatDate value="${dossier.dateCreation}" pattern="dd/MM/yyyy à HH:mm"/>
        </div>
      </div>

      <div class="detail-item">
        <label>Statut</label>
        <div class="value">${dossier.statut}</div>
      </div>
    </div>

    <!-- Informations de l'Élève -->
    <div class="section">
      <h3 class="section-title">👨‍🎓 Informations de l'Élève</h3>
      <div class="detail-grid">
        <div class="detail-item">
          <label>Matricule</label>
          <div class="value">${dossier.eleve.matricule}</div>
        </div>

        <div class="detail-item">
          <label>Nom Complet</label>
          <div class="value">${dossier.eleve.prenom} ${dossier.eleve.nom}</div>
        </div>

        <div class="detail-item">
          <label>Email</label>
          <div class="value">${dossier.eleve.email}</div>
        </div>

        <div class="detail-item">
          <label>Filière</label>
          <div class="value">${dossier.eleve.filiere.nom}</div>
        </div>
      </div>
    </div>

    <!-- Remarques -->
    <c:if test="${not empty dossier.remarques}">
      <div class="section">
        <h3 class="section-title">📝 Remarques</h3>
        <div class="detail-item">
          <div class="value">${dossier.remarques}</div>
        </div>
      </div>
    </c:if>

    <!-- Actions -->
    <div class="actions">
      <a href="${pageContext.request.contextPath}/eleves?action=view&id=${dossier.eleve.id}" class="btn btn-primary">
        👁️ Voir l'Élève
      </a>
      <a href="${pageContext.request.contextPath}/dossiers?action=edit&id=${dossier.id}" class="btn btn-success">
        ✏️ Modifier
      </a>
      <a href="${pageContext.request.contextPath}/dossiers?action=list" class="btn btn-secondary">
        ⬅️ Retour à la Liste
      </a>
    </div>
  </div>
</div>
</body>
</html>
