<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - Gestion Académique</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: rgb(227, 242, 253);
            min-height: 100vh;
        }

        .hero {
            text-align: center;
            color: #263238;
            padding: 4rem 2rem;
        }

        .hero h1 {
            font-size: 3rem;
            margin-bottom: 1rem;
        }

        .hero p {
            font-size: 1.2rem;
            opacity: 0.9;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }

        .modules-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 2rem;
            margin-top: 2rem;
        }

        .module-card {
            background: white;
            border-radius: 12px;
            padding: 2rem;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            transition: transform 0.3s, box-shadow 0.3s;
            text-align: center;
        }

        .module-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 8px 24px rgba(0,0,0,0.2);
        }

        .module-icon {
            font-size: 3rem;
            margin-bottom: 1rem;
        }

        .module-card h3 {
            color: #667eea;
            font-size: 1.5rem;
            margin-bottom: 1rem;
        }

        .module-card p {
            color: #6c757d;
            margin-bottom: 1.5rem;
            line-height: 1.6;
        }

        .btn {
            display: inline-block;
            padding: 0.8rem 1.5rem;
            background: rgb(0, 87, 163);
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 500;
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
        }

        .features {
            background: white;
            border-radius: 12px;
            padding: 2rem;
            margin-top: 2rem;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        .features h2 {
            color: #667eea;
            text-align: center;
            margin-bottom: 2rem;
            font-size: 2rem;
        }

        .features ul {
            list-style: none;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1rem;
        }

        .features li {
            padding: 1rem;
            background: #f8f9fa;
            border-radius: 8px;
            border-left: 4px solid #667eea;
        }

        .features li::before {
            content: "✓ ";
            color: #28a745;
            font-weight: bold;
            margin-right: 0.5rem;
        }
    </style>
</head>
<body>

<div class="hero">
    <h1>🎓 Bienvenue dans Gestion Académique</h1>
    <p>Système de gestion des filières, élèves, cours et dossiers administratifs</p>
</div>

<div class="container">
    <div class="modules-grid">
        <div class="module-card">
            <div class="module-icon">📚</div>
            <h3>Filières</h3>
            <p>Gérez les différentes filières académiques avec leurs codes et descriptions.</p>
            <a href="${pageContext.request.contextPath}/filieres?action=list" class="btn">Accéder</a>
        </div>

        <div class="module-card">
            <div class="module-icon">👨‍🎓</div>
            <h3>Élèves</h3>
            <p>Administrez les informations des élèves et leurs inscriptions aux filières.</p>
            <a href="${pageContext.request.contextPath}/eleves?action=list" class="btn">Accéder</a>
        </div>

        <div class="module-card">
            <div class="module-icon">📖</div>
            <h3>Cours</h3>
            <p>Gérez le catalogue des cours proposés dans les différentes filières.</p>
            <a href="${pageContext.request.contextPath}/cours?action=list" class="btn">Accéder</a>
        </div>

        <div class="module-card">
            <div class="module-icon">📋</div>
            <h3>Dossiers</h3>
            <p>Consultez et gérez les dossiers administratifs des élèves.</p>
            <a href="${pageContext.request.contextPath}/dossiers?action=list" class="btn">Accéder</a>
        </div>
    </div>

</div>
</body>
</html>