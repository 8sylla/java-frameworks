<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Filières</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: rgb(227, 242, 253);
            color: #263238;
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
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 2rem;
        }

        .alert {
            padding: 1rem;
            margin-bottom: 1rem;
            border-radius: 4px;
            border-left: 4px solid;
        }

        .alert-success {
            background-color: #d4edda;
            border-color: #28a745;
            color: #155724;
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
            margin-bottom: 2rem;
        }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
            padding-bottom: 1rem;
            border-bottom: 2px solid #f0f0f0;
        }

        .card-header h2 {
            color: #667eea;
            font-size: 1.5rem;
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

        .btn-success {
            background-color: #28a745;
            color: white;
        }

        .btn-danger {
            background-color: #dc3545;
            color: white;
        }

        .btn-sm {
            padding: 0.4rem 0.8rem;
            font-size: 0.85rem;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        table th {
            background-color: #f8f9fa;
            padding: 0.75rem;
            text-align: left;
            font-weight: 600;
            color: #495057;
            border-bottom: 2px solid #dee2e6;
        }

        table td {
            padding: 0.75rem;
            border-bottom: 1px solid #dee2e6;
        }

        table tr:hover {
            background-color: #f8f9fa;
        }

        .actions {
            display: flex;
            gap: 0.5rem;
        }

        .badge {
            display: inline-block;
            padding: 0.25rem 0.6rem;
            font-size: 0.75rem;
            font-weight: 600;
            border-radius: 12px;
            background-color: #17a2b8;
            color: white;
        }

        .empty-state {
            text-align: center;
            padding: 3rem;
            color: #6c757d;
        }

        .empty-state-icon {
            font-size: 4rem;
            margin-bottom: 1rem;
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
    <c:if test="${not empty sessionScope.successMessage}">
        <div class="alert alert-success">
                ${sessionScope.successMessage}
        </div>
        <c:remove var="successMessage" scope="session"/>
    </c:if>

    <c:if test="${not empty sessionScope.errorMessage}">
        <div class="alert alert-error">
                ${sessionScope.errorMessage}
        </div>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>

    <div class="card">
        <div class="card-header">
            <h2>📚 Liste des Filières (${totalFilieres})</h2>
            <a href="${pageContext.request.contextPath}/filieres?action=new" class="btn btn-primary">
                ➕ Nouvelle Filière
            </a>
        </div>

        <c:choose>
            <c:when test="${not empty filieres}">
                <table>
                    <thead>
                    <tr>
                        <th>Code</th>
                        <th>Nom</th>
                        <th>Description</th>
                        <th>Nombre d'Élèves</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="filiere" items="${filieres}">
                        <tr>
                            <td><span class="badge">${filiere.code}</span></td>
                            <td><strong>${filiere.nom}</strong></td>
                            <td>${filiere.description != null ? filiere.description : '-'}</td>
                            <td>"{eleves.size()}"</td>
                            <td>
                                <div class="actions">
                                    <a href="${pageContext.request.contextPath}/filieres?action=edit&id=${filiere.id}"
                                       class="btn btn-success btn-sm">✏️ Modifier</a>
                                    <a href="${pageContext.request.contextPath}/filieres?action=delete&id=${filiere.id}"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette filière ?')">
                                        🗑️ Supprimer
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <div class="empty-state-icon">📚</div>
                    <h3>Aucune filière trouvée</h3>
                    <p>Commencez par créer votre première filière</p>
                    <a href="${pageContext.request.contextPath}/filieres?action=new" class="btn btn-primary">
                        ➕ Créer une Filière
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
