<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 01/10/2025
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo1.model.Article" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Liste des Articles</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
      background-color: #f5f5f5;
    }
    .container {
      max-width: 1000px;
      margin: 0 auto;
      background-color: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    h1 {
      color: #333;
      border-bottom: 3px solid #4CAF50;
      padding-bottom: 10px;
    }
    .message {
      padding: 10px;
      margin: 10px 0;
      border-radius: 4px;
    }
    .success {
      background-color: #d4edda;
      color: #155724;
      border: 1px solid #c3e6cb;
    }
    .error {
      background-color: #f8d7da;
      color: #721c24;
      border: 1px solid #f5c6cb;
    }
    .btn {
      padding: 10px 20px;
      margin: 5px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      text-decoration: none;
      display: inline-block;
    }
    .btn-primary {
      background-color: #4CAF50;
      color: white;
    }
    .btn-primary:hover {
      background-color: #45a049;
    }
    .btn-warning {
      background-color: #ff9800;
      color: white;
    }
    .btn-warning:hover {
      background-color: #e68900;
    }
    .btn-danger {
      background-color: #f44336;
      color: white;
    }
    .btn-danger:hover {
      background-color: #da190b;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }
    th, td {
      padding: 12px;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }
    th {
      background-color: #4CAF50;
      color: white;
    }
    tr:hover {
      background-color: #f5f5f5;
    }
    .actions {
      white-space: nowrap;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Gestion des Articles</h1>

  <%
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");

    if (message != null) {
  %>
  <div class="message success"><%= message %></div>
  <%
    }
    if (error != null) {
  %>
  <div class="message error"><%= error %></div>
  <%
    }
  %>

  <div style="margin: 20px 0;">
    <a href="app?action=new" class="btn btn-primary">+ Nouvel Article</a>
  </div>

  <table>
    <thead>
    <tr>
      <th>Code</th>
      <th>Désignation</th>
      <th>Prix (DH)</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<Article> articles = (List<Article>) request.getAttribute("articles");
      if (articles != null && !articles.isEmpty()) {
        for (Article article : articles) {
    %>
    <tr>
      <td><%= article.getCode() %></td>
      <td><%= article.getDesignation() %></td>
      <td><%= String.format("%.2f", article.getPrix()) %></td>
      <td class="actions">
        <a href="app?action=edit&code=<%= article.getCode() %>"
           class="btn btn-warning">Modifier</a>
        <a href="app?action=delete&code=<%= article.getCode() %>"
           class="btn btn-danger"
           onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet article?')">
          Supprimer
        </a>
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="4" style="text-align: center; color: #999;">
        Aucun article disponible
      </td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>

  <div style="margin-top: 20px; color: #666;">
    Total: <%= articles != null ? articles.size() : 0 %> article(s)
  </div>
</div>
</body>
</html>