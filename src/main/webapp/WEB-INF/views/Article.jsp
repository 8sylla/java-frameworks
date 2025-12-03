<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 01/10/2025
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Nouvel Article</title>
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
      border-bottom: 3px solid #4CAF50;
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
      border-color: #4CAF50;
    }
    .required {
      color: red;
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
    .btn-primary {
      background-color: #4CAF50;
      color: white;
    }
    .btn-primary:hover {
      background-color: #45a049;
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
  <h1>Ajouter un Nouvel Article</h1>

  <form action="app" method="post">
    <input type="hidden" name="action" value="create">

    <div class="form-group">
      <label for="code">Code <span class="required">*</span></label>
      <input type="text" id="code" name="code" required
             placeholder="Ex: A004">
    </div>

    <div class="form-group">
      <label for="designation">Désignation <span class="required">*</span></label>
      <input type="text" id="designation" name="designation" required
             placeholder="Ex: Écran LED 24 pouces">
    </div>

    <div class="form-group">
      <label for="prix">Prix (DH) <span class="required">*</span></label>
      <input type="number" id="prix" name="prix" step="0.01" required
             placeholder="Ex: 150.00">
    </div>

    <div class="button-group">
      <button type="submit" class="btn btn-primary">Enregistrer</button>
      <a href="app?action=list" class="btn btn-secondary">Annuler</a>
    </div>
  </form>
</div>
</body>
</html>