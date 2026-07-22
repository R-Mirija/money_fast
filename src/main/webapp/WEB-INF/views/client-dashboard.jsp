<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.moneyfast.model.Client" %>
<%@ page import="com.moneyfast.model.Compte" %>
<%@ page import="com.moneyfast.model.Transfert" %>
<%@ page import="com.moneyfast.model.Pays" %>
<%@ page import="com.moneyfast.model.Devise" %>
<%
    Client client = (Client) session.getAttribute("userClient");
    Compte compte = (Compte) request.getAttribute("userCompte");
    
    @SuppressWarnings("unchecked")
    List<Transfert> operations = (List<Transfert>) request.getAttribute("operations");
    String deviseLabel = (String) request.getAttribute("deviseLabel");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Espace clt</title>
    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Importation en ligne des icônes Flaticon Uicons -->
    <link rel='stylesheet' href='https://cdn-uicons.flaticon.com/2.1.0/uicons-regular-rounded/css/uicons-regular-rounded.css'>
    
    <!-- Importation en ligne des icônes Bootstrap (Pour l'œil de mot de passe) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    
    <style>
        body {
            background-color: #f3f4f6;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
            color: #111111;
        }
        
        .top-navbar {
            background: #ffffff;
            border-bottom: 1px solid #efefef;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .app-logo {
            font-size: 24px;
            font-weight: 800;
            color: #e60023;
            text-decoration: none;
        }
        
        .balance-card {
            background-color: #111111;
            border-radius: 28px;
            color: #ffffff;
            padding: 30px 25px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
            text-align: center;
        }
        
        .balance-title {
            font-size: 13px;
            font-weight: 600;
            color: #888888;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 6px;
        }
        
        .balance-value-app {
            font-size: 36px;
            font-weight: 800;
            letter-spacing: -1px;
            margin-bottom: 25px;
        }
        
        .actions-container {
            display: flex;
            justify-content: space-between;
            gap: 8px;
        }
        
        .btn-balance-action {
            background-color: rgba(255, 255, 255, 0.08);
            border: none;
            border-radius: 18px;
            color: #ffffff;
            padding: 12px 6px;
            font-size: 11px;
            font-weight: 700;
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 6px;
            flex: 1;
            transition: all 0.2s ease;
        }
        
        .btn-balance-action:hover, .btn-balance-action.active {
            background-color: #e60023; 
            color: #ffffff;
        }
        
        .btn-balance-action i {
            font-size: 18px;
            line-height: 1;
        }
        
        .dashboard-card {
            background: #ffffff;
            border-radius: 28px;
            border: none;
            box-shadow: 0 1px 15px rgba(0, 0, 0, 0.04);
            padding: 30px;
            display: none; 
            opacity: 0;
            transform: translateY(10px);
            transition: opacity 0.3s ease, transform 0.3s ease;
        }
        .dashboard-card.show {
            display: block;
            opacity: 1;
            transform: translateY(0);
        }
        
        .form-control, .form-select {
            border-radius: 14px;
            border: 2px solid #efefef;
            padding: 12px 16px;
            font-size: 15px;
        }
        .form-control:focus, .form-select:focus {
            border-color: #a7a7a7;
            box-shadow: none;
        }
        
        .btn-pinterest {
            background-color: #e60023;
            color: white;
            border: none;
            border-radius: 999px;
            padding: 14px 28px;
            font-weight: 700;
            width: 100%;
        }
        .btn-pinterest:hover { background-color: #ad001a; color: white; }

        .history-card {
            background: #ffffff;
            border-radius: 28px;
            border: none;
            box-shadow: 0 1px 15px rgba(0, 0, 0, 0.04);
            padding: 30px;
            height: 100%;
        }
        
        .activity-item {
            background: #ffffff;
            border-radius: 20px;
            padding: 14px 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            border: 1px solid #f8f9fa;
            margin-bottom: 12px;
            transition: all 0.2s ease;
        }
        
        .activity-item:hover {
            box-shadow: 0 2px 12px rgba(0, 0, 0, 0.03);
            border-color: #efefef;
        }
        
        .icon-circle-debit {
            width: 44px;
            height: 44px;
            border-radius: 50%;
            background-color: #ffebe9;
            color: #e60023;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
        }
        
        .icon-circle-credit {
            width: 44px;
            height: 44px;
            border-radius: 50%;
            background-color: #e6f6ec;
            color: #00875a;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
        }
        
        .btn-pdf-icon {
            background-color: #f3f4f6;
            color: #111111;
            border: none;
            width: 36px;
            height: 36px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            transition: all 0.2s ease;
        }
        .btn-pdf-icon:hover {
            background-color: #e2e2e2;
            color: #e60023;
        }

        .toast-pinterest {
            background-color: #e60023; color: white; border-radius: 20px; padding: 14px 24px;
            display: flex; align-items: center; justify-content: space-between; font-weight: 600;
            position: fixed; top: 24px; right: 24px; z-index: 1050; min-width: 320px;
        }
        .toast-success-pinterest {
            background-color: #00875a; color: white; border-radius: 20px; padding: 14px 24px;
            display: flex; align-items: center; justify-content: space-between; font-weight: 600;
            position: fixed; top: 24px; right: 24px; z-index: 1050; min-width: 320px;
        }
        .btn-close-toast { background: none; border: none; color: white; font-size: 22px; cursor: pointer; }

        /* NOUVEAUTÉ : MODAL POPUP PERSISTANT POUR MODIFIER LE MOT DE PASSE */
        .modal-backdrop-custom {
            position: fixed; top: 0; left: 0; width: 100vw; height: 100vh;
            background: rgba(0, 0, 0, 0.4); z-index: 1090; display: none; opacity: 0;
            transition: opacity 0.3s ease;
        }
        .modal-backdrop-custom.show { display: block; opacity: 1; }

        .persistent-popup-panel {
            position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%) scale(0.9);
            width: 380px; background-color: #ffffff; border-radius: 28px;
            box-shadow: 0 4px 30px rgba(0, 0, 0, 0.15); border: 1px solid #efefef;
            padding: 30px; z-index: 1100; display: none; opacity: 0;
            transition: all 0.3s ease;
        }
        .persistent-popup-panel.show { display: block; opacity: 1; transform: translate(-50%, -50%) scale(1); }
        .btn-close-popup { background: none; border: none; color: #7a7a7a; font-size: 20px; cursor: pointer; padding: 0; line-height: 1; }
        .btn-close-popup:hover { color: #111; }

        /* Style pour les conteneurs de mot de passe à œil */
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
            font-size: 1.1rem;
            padding: 0;
            z-index: 5;
        }
        .password-toggle-btn:focus { outline: none; }
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

<%-- TOAST SUCCÈS --%>
<%
    String messageSucces = (String) session.getAttribute("succes");
    if (messageSucces != null) {
        session.removeAttribute("succes");
%>
    <div id="toast-success" class="toast-success-pinterest" role="alert">
        <span><%= messageSucces %></span>
        <button type="button" class="btn-close-toast" onclick="closeSuccessToast()">&times;</button>
    </div>
<%
    }
%>

<!-- BARRE DE NAVIGATION HAUTE -->
<div class="top-navbar mb-5">
    <a href="#" class="app-logo"><%= client.getNumeroTelephone() %></a>
    <div class="d-flex align-items-center">
        <span class="fw-bold me-4"> </span>
        <a href="client-portal?action=logout" class="btn btn-outline-danger rounded-pill px-4">Déconnexion</a>
    </div>
</div>

<div class="container pb-5">
    <div class="row g-4">
        
        <!-- SECTION DE GAUCHE : SOLDE ET FORMULAIRES -->
        <div class="col-md-5">
            <div class="row g-4">
                
                <!-- CARTE 1 : SOLDE EN TEMPS RÉEL ET BOUTONS DE RACCOURCIS -->
                <div class="col-12">
                    <div class="balance-card">
                        <div class="balance-title">Votre solde</div>
                        <div class="balance-value-app"><%= compte.getSolde() %> <%= deviseLabel %></div>
                        
                        <!-- Raccourcis d'actions (Dépôt, Relevé, Profil, Transfert) -->
                        <div class="actions-container">
                            <button type="button" id="btn-depot" class="btn-balance-action" onclick="switchForm('depot-card', this)">
                                <i class="fi fi-rr-arrow-circle-down"></i>
                                <span>Dépôt</span>
                            </button>
                            <button type="button" id="btn-releve" class="btn-balance-action" onclick="switchForm('releve-card', this)">
                                <i class="fi fi-rr-document"></i>
                                <span>Relevé</span>
                            </button>
                            <button type="button" id="btn-profil" class="btn-balance-action" onclick="switchForm('profil-card', this)">
                                <i class="fi fi-rr-user"></i>
                                <span>Profil</span>
                            </button>
                            <button type="button" id="btn-transfert" class="btn-balance-action active" onclick="switchForm('transfert-card', this)">
                                <i class="fi fi-rr-arrow-circle-up"></i>
                                <span>Transfert</span>
                            </button>
                        </div>

                        <% Integer dernierCode = (Integer) session.getAttribute("dernierCodeTransfert");
                           if (dernierCode != null) {
                               session.removeAttribute("dernierCodeTransfert");
                        %>
                            <a href="recu-pdf?code=<%= dernierCode %>" target="_blank" class="btn btn-success rounded-pill px-4 py-2 w-100 fw-bold mt-4" style="font-size: 13px;">
                                Télécharger le Reçu 
                            </a>
                        <% } %>
                    </div>
                </div>

                <!-- DÉPÔT -->
                <div class="col-12">
                    <div class="dashboard-card" id="depot-card">
                        <h5 class="fw-bold mb-3">Faire un dépôt</h5>
                        <form action="client-dashboard" method="post" class="row g-2 align-items-center">
                            <input type="hidden" name="action" value="depot">
                            <div class="col-sm-8">
                                <input type="text" name="montantDepot" class="form-control" placeholder="0.00" pattern="[0-9]+([.][0-9]{1,2})?" title="Nombre requis" required>
                            </div>
                            <div class="col-sm-4">
                                <button type="submit" class="btn btn-dark w-100 rounded-pill py-2">Déposer</button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- TRANSFERT -->
                <div class="col-12">
                    <div class="dashboard-card show" id="transfert-card">
                        <h5 class="fw-bold mb-4">Transfert</h5>
                        <form action="client-dashboard" method="post">
                            <input type="hidden" name="action" value="transfert">
                            <div class="mb-3">
                                <label class="form-label fw-bold">Destinataire</label>
                                <input type="tel" name="compteDest" class="form-control" placeholder="03xxxxxxxx" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Envoyer</label>
                                <input type="text" name="montant" class="form-control" placeholder="0.00" pattern="[0-9]+([.][0-9]{1,2})?" title="Nombre requis" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Motif</label>
                                <input type="text" name="raison" class="form-control" placeholder="Cadeau" required>
                            </div>
                            <div class="mb-4">
                                <label class="form-label fw-bold text-danger">Confirmer la transaction</label>
                                <div class="password-container">
                                    <input type="password" id="txPassword" name="passwordConfirm" class="form-control" placeholder="Entrez votre mot de passe" required>
                                    <button type="button" class="password-toggle-btn" onclick="togglePassword('txPassword', 'txEyeIcon')" aria-label="Afficher ou masquer le mot de passe">
                                        <i id="txEyeIcon" class="bi bi-eye"></i>
                                    </button>
                                </div>
                            </div>
                            <button type="submit" class="btn-pinterest">Envoyer</button>
                        </form>
                    </div>
                </div>
                <!--RELEVÉ MENSUEL -->
                <div class="col-12">
                    <div class="dashboard-card" id="releve-card">
                        <h5 class="fw-bold mb-3">Télécharger Relevé</h5>
                        <form action="releve-pdf" method="get" target="_blank" class="row g-2 align-items-center">
                            <input type="hidden" name="telephone" value="<%= client.getNumeroTelephone() %>">
                            <div class="col-sm-8">
                                <input type="month" name="mois" class="form-control" required>
                            </div>
                            <div class="col-sm-4">
                                <button type="submit" class="btn btn-outline-dark w-100 rounded-pill py-2">Exporter</button>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- MODIF -->
                <div class="col-12">
                    <div class="dashboard-card" id="profil-card">
                        <h5 class="fw-bold mb-3">Modifier les informations</h5>
                        <form action="client-dashboard" method="post">
                            <input type="hidden" name="action" value="updateProfile">
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Nom</label>
                                    <input type="text" name="nom" class="form-control" value="<%= client.getNom() %>" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Prénom</label>
                                    <input type="text" name="prenom" class="form-control" value="<%= client.getPrenom() %>" required>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Téléphone</label>
                                    <input type="tel" name="telephone" class="form-control" value="<%= client.getNumeroTelephone() %>" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Adresse Email</label>
                                    <input type="email" name="mail" class="form-control" value="<%= client.getMail() %>" required>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Pays de résidence</label>
                                    <select name="pays" class="form-select" required>
                                        <% 
                                            @SuppressWarnings("unchecked")
                                            List<Pays> listPays = (List<Pays>) request.getAttribute("listePays");
                                            if (listPays != null) {
                                                for (Pays p : listPays) {
                                                    String selected = (p.getIdPays() == client.getPays()) ? "selected" : "";
                                        %>
                                            <option value="<%= p.getIdPays() %>" <%= selected %>><%= p.getLibelle() %></option>
                                        <% 
                                                } 
                                            } 
                                        %>
                                    </select>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Devise</label>
                                    <select name="devise" class="form-select" required>
                                        <% 
                                            @SuppressWarnings("unchecked")
                                            List<Devise> listDevises = (List<Devise>) request.getAttribute("listeDevises");
                                            if (listDevises != null) {
                                                for (Devise d : listDevises) {
                                                    String selected = (d.getIdDevise() == client.getDevisePreferee()) ? "selected" : "";
                                        %>
                                            <option value="<%= d.getIdDevise() %>" <%= selected %>><%= d.getLibelle() %></option>
                                        <% 
                                                } 
                                            } 
                                        %>
                                    </select>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label class="form-label fw-bold text-danger">Confirmer les modifications </label>
                                <div class="password-container">
                                    <input type="password" id="profilePassword" name="passwordConfirm" class="form-control" placeholder="Entrez votre mot de passe pour valider" required>
                                    <button type="button" class="password-toggle-btn" onclick="togglePassword('profilePassword', 'profileEyeIcon')" aria-label="Afficher ou masquer le mot de passe">
                                        <i id="profileEyeIcon" class="bi bi-eye"></i>
                                    </button>
                                </div>
                            </div>

                            <button type="submit" class="btn-pinterest">Sauvegarder</button>
                            
                            <!-- BOUTON SECONDAIRE POUR DÉCLENCHER LE CHANGEMENT DE MOT DE PASSE -->
                            <div class="text-center mt-3">
                                <button type="button" class="btn btn-link text-decoration-none fw-bold" style="color: #e60023; font-size: 13px;" onclick="togglePasswordModal(true)">
                                    Modifier le mot de passe ?
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>

        <!-- HISTORIQUE -->
        <div class="col-md-7">
            <div class="history-card">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h5 class="fw-bold mb-0">Historique des Transactions</h5>
                </div>
                
                <div style="max-height: 520px; overflow-y: auto; padding-right: 5px;">
                    <% if (operations != null && !operations.isEmpty()) {
                           for (Transfert t : operations) {
                               boolean isDebit = t.getIdCompteSource().equals(compte.getIdCompte());
                    %>
                        <div class="activity-item">
                            <div class="d-flex align-items-center gap-3">
                                <div class="<%= isDebit ? "icon-circle-debit" : "icon-circle-credit" %>">
                                    <i class="<%= isDebit ? "fi fi-rr-arrow-circle-up" : "fi fi-rr-arrow-circle-down" %>"></i>
                                </div>
                                <div>
                                    <div class="fw-bold" style="font-size: 14px;"><%= isDebit ? "DÉBIT" : "CRÉDIT" %></div>
                                    <div class="text-muted" style="font-size: 11px;"><%= t.getRaison() %> le <%= t.getDateTransfert().toLocalDate() %></div>
                                </div>
                            </div>
                            
                            <div class="d-flex align-items-center gap-3">
                                <span class="fw-bold <%= isDebit ? "text-danger" : "text-success" %>" style="font-size: 15px;">
                                    <%= isDebit ? "-" + t.getMontantEnvoye() + " " + deviseLabel : "+" + t.getMontantRecu() + " MGA" %>
                                </span>
                                <a href="recu-pdf?code=<%= t.getCodeTransfert() %>" target="_blank" class="btn-pdf-icon" title="Télécharger le reçu PDF">
                                    <i class="fi fi-rr-file-pdf" style="font-size: 15px;"></i>
                                </a>
                            </div>
                        </div>
                    <% } } else { %>
                        <div class="text-center text-muted py-5">Aucune transaction trouvée.</div>
                    <% } %>
                </div>
            </div>
        </div>

    </div>
</div>

<!-- CHANGE PWD -->
<div id="backdrop" class="modal-backdrop-custom"></div>

<div id="passwordModal" class="persistent-popup-panel">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h5 class="fw-bold mb-0">Changer de mot de passe</h5>
        <button type="button" class="btn-close-popup" onclick="togglePasswordModal(false)">&times;</button>
    </div>
    <form action="client-dashboard" method="post">
        <input type="hidden" name="action" value="changePassword">
        <div class="mb-3">
            <label class="form-label fw-bold" style="font-size: 13px;">Mot de passe actuel</label>
            <div class="password-container">
                <input type="password" id="modalCurrentPassword" name="currentPassword" class="form-control form-control-dashboard" placeholder="Saisir mot de passe actuel" required>
                <button type="button" class="password-toggle-btn" onclick="togglePassword('modalCurrentPassword', 'modalCurrentEyeIcon')" aria-label="Afficher ou masquer le mot de passe">
                    <i id="modalCurrentEyeIcon" class="bi bi-eye"></i>
                </button>
            </div>
        </div>
        <div class="mb-4">
            <label class="form-label fw-bold" style="font-size: 13px;">Nouveau mot de passe</label>
            <div class="password-container">
                <input type="password" id="modalNewPassword" name="newPassword" class="form-control form-control-dashboard" placeholder="Minimum 6 caractères" required>
                <button type="button" class="password-toggle-btn" onclick="togglePassword('modalNewPassword', 'modalNewEyeIcon')" aria-label="Afficher ou masquer le mot de passe">
                    <i id="modalNewEyeIcon" class="bi bi-eye"></i>
                </button>
            </div>
        </div>
        <button type="submit" class="btn-pinterest">CHANGER</button>
    </form>
</div>

<script>
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

    function togglePasswordModal(show) {
        const modal = document.getElementById('passwordModal');
        const backdrop = document.getElementById('backdrop');
        
        if (show) {
            modal.style.display = 'block';
            backdrop.style.display = 'block';
            setTimeout(() => {
                modal.classList.add('show');
                backdrop.classList.add('show');
            }, 10);
        } else {
            modal.classList.remove('show');
            backdrop.classList.remove('show');
            setTimeout(() => {
                modal.style.display = 'none';
                backdrop.style.display = 'none';
            }, 300);
        }
    }

    function switchForm(cardId, button) {
        document.querySelectorAll('.dashboard-card').forEach(card => {
            card.classList.remove('show');
            setTimeout(() => { card.style.display = 'none'; }, 10);
        });

        document.querySelectorAll('.btn-balance-action').forEach(btn => {
            btn.classList.remove('active');
        });

        button.classList.add('active');

        const activeCard = document.getElementById(cardId);
        setTimeout(() => {
            activeCard.style.display = 'block';
            setTimeout(() => { activeCard.classList.add('show'); }, 10);
        }, 10);
    }

    function closeToast() {
        const toast = document.getElementById('toast-error');
        if (toast) { toast.style.opacity = '0'; setTimeout(() => { toast.style.display = 'none'; }, 500); }
    }
    function closeSuccessToast() {
        const toast = document.getElementById('toast-success');
        if (toast) { toast.style.opacity = '0'; setTimeout(() => { toast.style.display = 'none'; }, 500); }
    }
    window.addEventListener('DOMContentLoaded', () => {
        const err = document.getElementById('toast-error');
        if (err) { setTimeout(() => { closeToast(); }, 5000); }
        const succ = document.getElementById('toast-success');
        if (succ) { setTimeout(() => { closeSuccessToast(); }, 5000); }
        
        const defaultBtn = document.getElementById('btn-transfert');
        if (defaultBtn) {
            defaultBtn.classList.add('active');
        }
    });
</script>

</body>
</html>
