<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${dossier != null ? 'Modifier' : 'Créer'} un Dossier Administratif</title>
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
        .container { max-width: 800px; margin: 2rem auto; padding: 0 2rem; }
        .alert { padding: 1rem; margin-bottom: 1rem; border-radius: 4px; border-left: 4px solid; }
        .alert-error { background-color: #f8d7da; border-color: #dc3545; color: #721c24; }
        .card { background: white; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 2rem; }
        .card-header { margin-bottom: 1.5rem; padding-bottom: 1rem; border-bottom: 2px solid #f0f0f0; }
        .card-header h2 { color: #667eea; font-size: 1.5rem; }
        .form-group { margin-bottom: 1.5rem; }
        .form-group label { display: block; margin-bottom: 0.5rem; font-weight: 500; color: #495057; }
        .form-group label .required { color: #dc3545; }
        .form-group input, .form-group select, .form-group textarea { width: 100%; padding: 0.6rem; border: 1px solid #ced4da; border-radius: 4px; font-size: 1rem; transition: border-color 0.3s; font-family: inherit; }
        .form-group input:focus, .form-group select:focus, .form-group textarea:focus { outline: none; border-color: #667eea; box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1); }
        .form-group textarea { resize: vertical; min-height: 100px; }
        .form-group input[readonly] { background-color: #e9ecef; cursor: not-allowed; }
        .form-actions { display: flex; gap: 1rem; margin-top: 2rem; }
        .btn { padding: 0.6rem 1.2rem; border: none; border-radius: 4px; cursor: pointer; font-size: 0.9rem; text-decoration: none; display: inline-block; transition: all 0.3s; font-weight: 500; }
        .btn-primary { background: rgb(0, 87, 163); color: white; }
        .btn-primary:hover { transform: translateY(-2px); box-shadow: 0 4px 8px rgba(102, 126, 234, 0.4); }
        .btn-secondary { background-color: #6c757d; color: white; }
        .btn-secondary:hover { background-color: #5a6268; }
        .info-box { background-color: #d1ecf1; border-left: 4px solid #17a2b8; padding: 1rem; margin-bottom: 1.5rem; border-radius: 4px; }
        .info-box p { margin: 0; color: #0c5460; }
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
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">${errorMessage}</div>
    </c:if>

    <div class="card">
        <div class="card-header">
            <h2>${dossier != null ? '✏️ Modifier' : '➕ Créer'} un Dossier Administratif</h2>
        </div>

        <c:if test="${dossier == null}">
            <div class="info-box">
                <p><strong>ℹ️ Information:</strong> Le numéro d'inscription sera généré automatiquement lors de la création.</p>
            </div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/dossiers">
            <input type="hidden" name="action" value="${dossier != null ? 'update' : 'create'}">
            <c:if test="${dossier != null}">
                <input type="hidden" name="id" value="${dossier.id}">
            </c:if>

            <c:if test="${dossier != null}">
                <div class="form-group">
                    <label for="numeroInscription">Numéro d'Inscription</label>
                    <input type="text" id="numeroInscription" value="${dossier.numeroInscription}" readonly>
                </div>

                <div class="form-group">
                    <label>Élève Associé</label>
                    <input type="text" value="${dossier.eleve.prenom} ${dossier.eleve.nom} (${dossier.eleve.matricule})" readonly>
                </div>
            </c:if>

            <c:if test="${dossier == null}">
                <div class="form-group">
                    <label for="eleveId">Élève <span class="required">*</span></label>
                    <select id="eleveId" name="eleveId" required>
                        <option value="">-- Sélectionner un élève --</option>
                        <c:forEach var="eleve" items="${eleves}">
                            <option value="${eleve.id}">
                                    ${eleve.matricule} - ${eleve.prenom} ${eleve.nom} (${eleve.filiere.nom})
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </c:if>

            <div class="form-group">
                <label for="statut">Statut <span class="required">*</span></label>
                <select id="statut" name="statut" required>
                    <option value="">-- Sélectionner un statut --</option>
                    <option value="ACTIF" ${dossier != null && dossier.statut == 'ACTIF' ? 'selected' : ''}>✅ ACTIF</option>
                    <option value="EN_ATTENTE" ${dossier != null && dossier.statut == 'EN_ATTENTE' ? 'selected' : ''}>⏳ EN ATTENTE</option>
                    <option value="INACTIF" ${dossier != null && dossier.statut == 'INACTIF' ? 'selected' : ''}>❌ INACTIF</option>
                </select>
            </div>

            <div class="form-group">
                <label for="remarques">Remarques</label>
                <textarea id="remarques" name="remarques" placeholder="Remarques sur le dossier administratif...">${dossier != null ? dossier.remarques : ''}</textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    ${dossier != null ? '💾 Mettre à jour' : '➕ Créer'}
                </button>
                <a href="${pageContext.request.contextPath}/dossiers?action=list" class="btn btn-secondary">❌ Annuler</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>

