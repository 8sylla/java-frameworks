<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 01/10/2025
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.demo1.model.Article" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Modifier Article</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
      background-color: #f5f5f5;
    }
    .container {
      max-width: 600px;
      margin: 0 auto;
      background-color: white;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    h1 {
      color: #333;
      border-bottom: 3px solid #ff9800;
      padding-bottom: 10px;
    }
    .form-group {
      margin-bottom: 20px;
    }
    label {
      display: block;
      margin-bottom: 5px;
      color: #555;
      font-weight: bold;
    }
    input[type="text"],
    input[type="number"] {
      width: 100%;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 4px;
      box-sizing: border-box;
      font-size: 14px;
    }
    input[type="text"]:focus,
    input[type="number"]:focus {
      outline: none;
      border-color: #ff9800;
    }
    input[type="text"]:disabled {
      background-color: #f5f5f5;
      color: #666;
    }
    .required {
      color: red;
    }
    .info-text {
      color: #666;
      font-size: 12px;
      font-style: italic;
      margin-top: 5px;
    }
    .btn {
      padding: 12px 30px;
      margin: 5px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      text-decoration: none;
      display: inline-block;
      font-size: 16px;
    }
    .btn-warning {
      background-color: #ff9800;
      color: white;
    }
    .btn-warning:hover {
      background-color: #e68900;
    }
    .btn-secondary {
      background-color: #6c757d;
      color: white;
    }
    .btn-secondary:hover {
      background-color: #5a6268;
    }
    .button-group {
      margin-top: 30px;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Modifier l'Article</h1>

  <%
    Article article = (Article) request.getAttribute("article");
    if (article != null) {
  %>

    <form action="app" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="code" value="<%= article.getCode() %>">

    <div class="form-group">
      <label for="code">Code</label>
      <input type="text" id="code" value="<%= article.getCode() %>" disabled>
      <div class="info-text">Le code ne peut pas être modifié</div>
    </div>

    <div class="form-group">
      <label for="designation">Désignation <span class="required">*</span></label>
      <input type="text" id="designation" name="designation" required
             value="<%= article.getDesignation() %>">
    </div>

    <div class="form-group">
      <label for="prix">Prix (DH) <span class="required">*</span></label>
      <input type="number" id="prix" name="prix" step="0.01" required
             value="<%= article.getPrix() %>">
    </div>

    <div class="button-group">
      <button type="submit" class="btn btn-warning">Mettre à jour</button>
      <a href="app?action=list" class="btn btn-secondary">Annuler</a>
    </div>
  </form>

    <%} else {%>

    <p style="color: red;">Article introuvable</p>
  <a href="app?action=list" class="btn btn-secondary">Retour à la liste</a>

    <%}
%>
</div>
</body>
</html>
