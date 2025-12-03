<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${filiere != null ? 'Modifier' : 'Créer'} une Filière</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: rgb(227, 242, 253);
            color: #333;
            line-height: 1.6;
        }

        .header {
            background: rgb(0, 87, 163);
            color: white;
            padding: 1rem 0;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .header-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 2rem;
        }

        .header h1 {
            font-size: 1.5rem;
            font-weight: 600;
        }

        .nav {
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .nav ul {
            list-style: none;
            display: flex;
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 2rem;
        }

        .nav li {
            margin-right: 2rem;
        }

        .nav a {
            display: block;
            padding: 1rem 0;
            color: #667eea;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s;
            border-bottom: 3px solid transparent;
        }

        .nav a:hover, .nav a.active {
            color: #764ba2;
            border-bottom-color: #764ba2;
        }

        .container {
            max-width: 800px;
            margin: 2rem auto;
            padding: 0 2rem;
        }

        .alert {
            padding: 1rem;
            margin-bottom: 1rem;
            border-radius: 4px;
            border-left: 4px solid;
        }

        .alert-error {
            background-color: #f8d7da;
            border-color: #dc3545;
            color: #721c24;
        }

        .card {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            padding: 2rem;
        }

        .card-header {
            margin-bottom: 1.5rem;
            padding-bottom: 1rem;
            border-bottom: 2px solid #f0f0f0;
        }

        .card-header h2 {
            color: #667eea;
            font-size: 1.5rem;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: #495057;
        }

        .form-group label .required {
            color: #dc3545;
        }

        .form-group input,
        .form-group textarea {
            width: 100%;
            padding: 0.6rem;
            border: 1px solid #ced4da;
            border-radius: 4px;
            font-size: 1rem;
            transition: border-color 0.3s;
            font-family: inherit;
        }

        .form-group input:focus,
        .form-group textarea:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .form-group textarea {
            resize: vertical;
            min-height: 100px;
        }

        .form-actions {
            display: flex;
            gap: 1rem;
            margin-top: 2rem;
        }

        .btn {
            padding: 0.6rem 1.2rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 0.9rem;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s;
            font-weight: 500;
        }

        .btn-primary {
            background: rgb(0, 87, 163);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(102, 126, 234, 0.4);
        }

        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }

        .btn-secondary:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
<header class="header">
    <div class="header-content">
        <h1>🎓 Gestion Académique</h1>
    </div>
</header>

<nav class="nav">
    <ul>
        <li><a href="${pageContext.request.contextPath}/">Accueil</a></li>
        <li><a href="${pageContext.request.contextPath}/filieres?action=list" class="active">Filières</a></li>
        <li><a href="${pageContext.request.contextPath}/eleves?action=list">Élèves</a></li>
        <li><a href="${pageContext.request.contextPath}/cours?action=list">Cours</a></li>
        <li><a href="${pageContext.request.contextPath}/dossiers?action=list">Dossiers</a></li>
    </ul>
</nav>

<div class="container">
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">
                ${errorMessage}
        </div>
    </c:if>

    <div class="card">
        <div class="card-header">
            <h2>${filiere != null ? '✏️ Modifier' : '➕ Créer'} une Filière</h2>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/filieres">
            <input type="hidden" name="action" value="${filiere != null ? 'update' : 'create'}">
            <c:if test="${filiere != null}">
                <input type="hidden" name="id" value="${filiere.id}">
            </c:if>

            <div class="form-group">
                <label for="code">Code <span class="required">*</span></label>
                <input type="text"
                       id="code"
                       name="code"
                       value="${filiere != null ? filiere.code : ''}"
                       required
                       maxlength="20"
                       placeholder="Ex: INF101">
            </div>

            <div class="form-group">
                <label for="nom">Nom <span class="required">*</span></label>
                <input type="text"
                       id="nom"
                       name="nom"
                       value="${filiere != null ? filiere.nom : ''}"
                       required
                       maxlength="100"
                       placeholder="Ex: Informatique">
            </div>

            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description"
                          name="description"
                          placeholder="Description de la filière">${filiere != null ? filiere.description : ''}</textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    ${filiere != null ? '💾 Mettre à jour' : '➕ Créer'}
                </button>
                <a href="${pageContext.request.contextPath}/filieres?action=list" class="btn btn-secondary">
                    ❌ Annuler
                </a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
