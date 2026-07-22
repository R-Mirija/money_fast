<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.moneyfast.model.Pays" %>
<%@ page import="com.moneyfast.model.Devise" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Espace Clt</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
        <style>
            body { background-color: #f7f7f7; font-family: -apple-system, sans-serif; color: #111; }
            .portal-card {
                background: #ffffff;
                border-radius: 32px;
                box-shadow: 0 1px 20px rgba(0, 0, 0, 0.06);
                padding: 40px;
                max-width: 500px;
                margin: 50px auto;
            }
            .form-control, .form-select {
                border-radius: 16px;
                border: 2px solid #efefef;
                padding: 12px 16px;
            }
            .btn-pinterest {
                background-color: #e60023;
                color: white;
                border: none;
                border-radius: 999px;
                padding: 12px 28px;
                font-weight: 700;
                width: 100%;
            }
            .btn-pinterest:hover { background-color: #ad001a; color: white; }
            
            /* Style pour les champs mot de passe avec bouton d'œil */
            .password-container {
                position: relative;
            }
            .password-toggle-btn {
                position: absolute;
                right: 15px;
                top: 50%;
                transform: translateY(-50%);
                background: none;
                border: none;
                color: #6c757d;
                cursor: pointer;
                font-size: 1.2rem;
                padding: 0;
                z-index: 5;
            }
            .password-toggle-btn:focus { outline: none; }
            
            /* Toasts d'erreur */
            .toast-pinterest {
                background-color: #e60023; color: white; border-radius: 20px; padding: 14px 24px;
                display: flex; align-items: center; justify-content: space-between; font-weight: 600;
                position: fixed; top: 24px; right: 24px; z-index: 1050; min-width: 320px;
            }
            .btn-close-toast { background: none; border: none; color: white; font-size: 22px; cursor: pointer; }
            
            .register-section {
                max-height: 0;
                opacity: 0;
                overflow: hidden;
                transition: max-height 0.5s cubic-bezier(0.25, 0.8, 0.25, 1), opacity 0.3s ease;
            }
            .register-section.show {
                max-height: 1200px;
                opacity: 1;
            }
        </style>
    </head>
    <body class="py-5">

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
                <h2 class="fw-bold text-center mb-2" style="color: #e60023;">Money Fast</h2>
                <p class="text-center text-muted mb-4" style="font-size: 14px;">Connectez-vous à votre espace client</p>

                <form action="client-portal" method="post" class="mb-4">
                    <input type="hidden" name="action" value="login">
                    <div class="mb-3">
                        <label class="form-label fw-bold">Numéro de téléphone</label>
                        <input type="tel" name="telephone" class="form-control mb-3" placeholder="03x xx xxx xx" required>

                        <label class="form-label fw-bold">Mot de passe</label>
                        <div class="password-container">
                            <input type="password" id="loginPassword" name="password" class="form-control" placeholder="********" required>
                            <button type="button" class="password-toggle-btn" onclick="togglePassword('loginPassword', 'loginEyeIcon')" aria-label="Afficher ou masquer le mot de passe">
                                <i id="loginEyeIcon" class="bi bi-eye"></i>
                            </button>
                        </div>
                    </div>
                    <button type="submit" class="btn-pinterest">Continuer</button>
                </form>

                <div class="text-center my-4">
                    <p class="text-muted mb-2" style="font-size: 13px;">S'inscrire ?</p>
                    <button type="button" class="btn btn-outline-dark rounded-pill px-4 fw-bold" id="toggleRegBtn" onclick="toggleRegister()" style="font-size: 13px;">
                        Remplir le formulaire
                    </button>
                </div>

                <div id="registerSection" class="register-section">
                    <hr class="text-muted my-4">

                    <h5 class="fw-bold text-center mb-3">Inscrivez-vous</h5>
                    <form action="client-portal" method="post">
                        <input type="hidden" name="action" value="register">

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-bold">Nom</label>
                                <input type="text" name="nom" class="form-control" placeholder="Votre nom" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-bold">Prénom</label>
                                <input type="text" name="prenom" class="form-control" placeholder="Votre prénom" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-bold">Genre</label>
                                <select name="sexe" class="form-select">
                                    <option value="homme">Homme</option>
                                    <option value="femme">Femme</option>
                                </select>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-bold">Téléphone</label>
                                <input type="tel" name="telephone" class="form-control" value="<%= request.getAttribute("prefilledPhone") != null ? request.getAttribute("prefilledPhone") : "" %>" placeholder="03x xx xxx xx" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-bold">Email</label>
                                <input type="email" name="mail" class="form-control" placeholder="exemple@mail.com" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-bold">Pays de résidence</label>
                                <select name="pays" class="form-select" required>
                                    <% List<Pays> listPays = (List<Pays>) request.getAttribute("listePays");
                                        if (listPays != null) {
                                        for (Pays p : listPays) { %>
                                        <option value="<%= p.getIdPays() %>"><%= p.getLibelle() %></option>
                                        <% } } %>
                                    </select>
                                </div>
                            </div>

                            <div class="row mb-4">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Devise</label>
                                    <select name="devise" class="form-select" required>
                                        <% List<Devise> listDevises = (List<Devise>) request.getAttribute("listeDevises");
                                            if (listDevises != null) {
                                            for (Devise d : listDevises) { %>
                                            <option value="<%= d.getIdDevise() %>"><%= d.getLibelle() %></option>
                                            <% } } %>
                                        </select>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label fw-bold">Mot de passe</label>
                                        <div class="password-container">
                                            <input type="password" id="regPassword" name="password" class="form-control" placeholder="********" required>
                                            <button type="button" class="password-toggle-btn" onclick="togglePassword('regPassword', 'regEyeIcon')" aria-label="Afficher ou masquer le mot de passe">
                                                <i id="regEyeIcon" class="bi bi-eye"></i>
                                            </button>
                                        </div>
                                    </div>
                                </div>

                                <button type="submit" class="btn-pinterest">Créer mon Compte</button>
                            </form>
                        </div>

                    </div>
                </div>

                <script>
                    // Fonction pour basculer le mot de passe entre texte et masqué
                    function togglePassword(inputId, iconId) {
                        const passwordInput = document.getElementById(inputId);
                        const eyeIcon = document.getElementById(iconId);
                        
                        if (passwordInput.type === 'password') {
                            passwordInput.type = 'text';
                            eyeIcon.classList.remove('bi-eye');
                            eyeIcon.classList.add('bi-eye-slash');
                        } else {
                            passwordInput.type = 'password';
                            eyeIcon.classList.remove('bi-eye-slash');
                            eyeIcon.classList.add('bi-eye');
                        }
                    }
                    
                    function toggleRegister(forceOpen = false) {
                        const section = document.getElementById('registerSection');
                        const btn = document.getElementById('toggleRegBtn');
                        
                        if (forceOpen || !section.classList.contains('show')) {
                            section.classList.add('show');
                            btn.innerText = "Voir moins";
                        } else {
                            section.classList.remove('show');
                            btn.innerText = "Remplir le formulaire";
                        }
                    }
                    
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
                        
                        <% if (request.getAttribute("prefilledPhone") != null) { %>
                        toggleRegister(true);
                        <% } %>
                    });
                </script>

            </body>
        </html>