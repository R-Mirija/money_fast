<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Boolean hasAdmin = (Boolean) request.getAttribute("hasAdmin");
    if (hasAdmin == null) hasAdmin = false;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Espace Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f7f7f7; font-family: -apple-system, sans-serif; color: #111; }
        .portal-card {
            background: #ffffff;
            border-radius: 32px;
            box-shadow: 0 1px 20px rgba(0, 0, 0, 0.06);
            padding: 40px;
            max-width: 450px;
            margin: 80px auto;
        }
        .form-control {
            border-radius: 12px;
            border: 2px solid #efefef;
            padding: 12px 16px;
        }
        .btn-admin {
            background-color: #111111; 
            color: white;
            border: none;
            border-radius: 999px;
            padding: 12px 28px;
            font-weight: 700;
            width: 100%;
            transition: background-color 0.2s ease;
        }
        .btn-admin:hover { background-color: #333333; color: white; }
        
        /* Toasts Flottants */
        .toast-pinterest {
            background-color: #e60023; color: white; border-radius: 20px; padding: 14px 24px;
            display: flex; align-items: center; justify-content: space-between; font-weight: 600;
            position: fixed; top: 24px; right: 24px; z-index: 1050; min-width: 320px;
        }
        .btn-close-toast { background: none; border: none; color: white; font-size: 22px; cursor: pointer; }
    </style>
</head>
<body>

<%-- TOAST ERREUR --%>
<%
    String messageErreur = (String) session.getAttribute("erreur");
    if (messageErreur != null) {
        session.removeAttribute("erreur");
%>
    <div id="toast-error" class="toast-pinterest" role="alert">
        <span><%= messageErreur %></span>
        <button type="button" class="btn-close-toast" onclick="closeToast()">&times;</button>
    </div>
<%
    }
%>

<div class="container">
    <div class="portal-card">
        <h2 class="fw-bold text-center mb-2" style="color: #111111;">Money Fast</h2>
        <% if (!hasAdmin) { %>
            <div class="alert alert-warning py-3 text-center mb-4" style="border-radius: 12px; font-size: 13px;">
                 <strong>Premier lancement détecté !</strong><br>Veuillez configurer le premier compte administrateur.
            </div>

            <form action="admin-portal" method="post">
                <input type="hidden" name="action" value="register">
                <div class="mb-3">
                    <label class="form-label fw-bold" style="font-size: 13px;">Nom d'utilisateur</label>
                    <input type="text" name="username" class="form-control" placeholder="ex: admin_principal" required>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-bold" style="font-size: 13px;">Adresse Email</label>
                    <input type="email" name="email" class="form-control" placeholder="ex: admin@moneyfast.com" required>
                </div>
                <div class="mb-4">
                    <label class="form-label fw-bold" style="font-size: 13px;">Mot de passe</label>
                    <input type="password" name="password" class="form-control" placeholder="Mot de passe" required>
                </div>
                <button type="submit" class="btn-admin">Initialiser l'Admin</button>
            </form>

        <% } else { %>
            <form action="admin-portal" method="post">
                <input type="hidden" name="action" value="login">
                <div class="mb-3">
                    <label class="form-label fw-bold" style="font-size: 13px;">Nom d'utilisateur</label>
                    <input type="text" name="username" class="form-control" placeholder="Votre identifiant" required>
                </div>
                <div class="mb-4">
                    <label class="form-label fw-bold" style="font-size: 13px;">Mot de passe</label>
                    <input type="password" name="password" class="form-control" placeholder="Votre mot de passe" required>
                </div>
                <button type="submit" class="btn-admin">Se Connecter</button>
            </form>
        <% } %>
    </div>
</div>

<script>
    function closeToast() {
        const toast = document.getElementById('toast-error');
        if (toast) {
            toast.style.opacity = '0';
            setTimeout(() => { toast.style.display = 'none'; }, 500);
        }
    }
    window.addEventListener('DOMContentLoaded', () => {
        const toast = document.getElementById('toast-error');
        if (toast) { setTimeout(() => { closeToast(); }, 5000); }
    });
</script>

</body>
</html>