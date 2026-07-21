<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.moneyfast.model.Admin" %>
<%@ page import="com.moneyfast.model.Client" %>
<%@ page import="com.moneyfast.model.Compte" %>
<%@ page import="com.moneyfast.model.Pays" %>
<%@ page import="com.moneyfast.model.Devise" %>
<%@ page import="com.moneyfast.model.TauxDeChange" %>
<%@ page import="com.moneyfast.repository.CompteRepository" %>
<%@ page import="com.moneyfast.repository.MySQLCompteRepository" %>
<%@ page import="com.moneyfast.repository.MetadataRepository" %>
<%@ page import="com.moneyfast.repository.MySQLMetadataRepository" %>
<%
    Admin admin = (Admin) session.getAttribute("userAdmin");
    List<Client> listeClients = (List<Client>) request.getAttribute("listeClients");
    List<Pays> listePays = (List<Pays>) request.getAttribute("listePays");
    List<TauxDeChange> listeTaux = (List<TauxDeChange>) request.getAttribute("listeTaux");
    List<Devise> listeDevises = (List<Devise>) request.getAttribute("listeDevises");
    Double totalRecettes = (Double) request.getAttribute("totalRecettes");
    if (totalRecettes == null) totalRecettes = 0.00;
    
    CompteRepository compteRepository = new MySQLCompteRepository();
    MetadataRepository metadataRepository = new MySQLMetadataRepository();
    DecimalFormat df = new DecimalFormat("0.######"); 
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <link rel='stylesheet' href='https://cdn-uicons.flaticon.com/2.1.0/uicons-regular-rounded/css/uicons-regular-rounded.css'>
    
    <style>
        body {
            background-color: #f3f4f6;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
            color: #111111;
            overflow-x: hidden;
        }
		
		.swipe-container {
            position: relative;
            overflow: hidden;
            border-radius: 16px;
            background-color: #e60023; 
        }

        .swipe-behind {
            position: absolute;
            left: 0;
            top: 0;
            width: 80px;
            height: 100%;
            z-index: 1;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .swipe-front {
            position: relative;
            z-index: 2;
            background-color: #f8f9fa;
            border-radius: 16px;
            transition: transform 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
            cursor: grab;
            user-select: none; 
        }

        .swipe-front:active {
            cursor: grabbing;
        }
        
        .sidebar {
            width: 80px;
            background-color: #111111;
            border-radius: 28px;
            margin: 20px 0 20px 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: space-between;
            padding: 30px 0;
            height: calc(100vh - 40px);
            position: fixed;
            left: 0;
            top: 0;
        }
        
        .sidebar-logo {
            font-size: 24px;
            color: #ffffff;
            font-weight: 800;
            text-decoration: none;
            margin-bottom: 30px;
        }

        .sidebar-menu {
            display: flex;
            flex-direction: column;
            gap: 24px;
        }

        .sidebar-icon {
            width: 44px;
            height: 44px;
            border-radius: 14px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #888888;
            font-size: 20px;
            text-decoration: none;
            transition: all 0.2s ease;
        }

        .sidebar-icon:hover, .sidebar-icon.active {
            background-color: rgba(255, 255, 255, 0.1);
            color: #ffffff;
        }

        .main-content {
            margin-left: 110px;
            margin-right: 340px;
            padding: 30px 20px;
            min-height: 100vh;
        }

        .header-section {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .overview-card {
            background: linear-gradient(135deg, #e60023 0%, #ad001a 100%);
            border-radius: 28px;
            color: #ffffff;
            padding: 30px;
            position: relative;
            overflow: hidden;
            box-shadow: 0 4px 20px rgba(230, 0, 35, 0.15);
            height: 100%;
        }

        .sub-card {
            background-color: #ffffff;
            border-radius: 28px;
            padding: 25px;
            border: none;
            box-shadow: 0 1px 15px rgba(0,0,0,0.04);
            height: 100%;
            transition: transform 0.2s ease;
        }

        .bottom-card {
            background-color: #ffffff;
            border-radius: 24px;
            padding: 25px;
            border: none;
            box-shadow: 0 1px 15px rgba(0,0,0,0.04);
            position: relative;
            margin-top: 20px;
            height: 100%;
        }

        .badge-top-icon {
            width: 48px;
            height: 48px;
            background-color: #111111;
            color: white;
            border-radius: 14px;
            display: flex;
            align-items: center;
            justify-content: center;
            position: absolute;
            top: -24px;
            left: 25px;
            font-size: 20px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }

        .right-panel {
            width: 320px;
            background-color: #ffffff;
            border-radius: 32px;
            position: fixed;
            right: 20px;
            top: 20px;
            height: calc(100vh - 40px);
            padding: 35px 25px;
            box-shadow: -2px 0 20px rgba(0,0,0,0.02);
            overflow-y: auto;
        }

        .avatar-circle {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background-color: #efefef;
            color: #111111;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            font-size: 13px;
        }

        .btn-admin-submit {
            background-color: #111111;
            color: #ffffff;
            border-radius: 12px;
            padding: 8px 16px;
            font-size: 13px;
            font-weight: 600;
            border: none;
            width: 100%;
        }
        .btn-admin-submit:hover {
            background-color: #333333;
        }

        .form-control-dashboard {
            border-radius: 12px;
            border: 2px solid #f2f2f2;
            padding: 8px 12px;
            font-size: 13px;
        }

        .toast-pinterest {
            background-color: #e60023; color: white; border-radius: 20px; padding: 14px 24px;
            display: flex; align-items: center; justify-content: space-between; font-weight: 600;
            position: fixed; top: 24px; right: 350px; z-index: 1050; min-width: 320px;
        }
        .toast-success-pinterest {
            background-color: #00875a; color: white; border-radius: 20px; padding: 14px 24px;
            display: flex; align-items: center; justify-content: space-between; font-weight: 600;
            position: fixed; top: 24px; right: 350px; z-index: 1050; min-width: 320px;
        }
        .btn-close-toast { background: none; border: none; color: white; font-size: 22px; cursor: pointer; }

        .fab-admin {
            position: fixed;
            bottom: 30px;
            right: 360px; 
            width: 56px;
            height: 56px;
            background-color: #e60023;
            color: #ffffff;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 22px;
            box-shadow: 0 4px 16px rgba(230, 0, 35, 0.3);
            border: none;
            cursor: pointer;
            z-index: 1000;
            transition: all 0.2s ease;
        }
        .fab-admin:hover {
            background-color: #ad001a;
            transform: scale(1.05);
        }

        .admin-popup-panel {
            position: fixed;
            bottom: 100px;
            right: 360px;
            width: 320px;
            background-color: #ffffff;
            border-radius: 24px;
            box-shadow: 0 4px 24px rgba(0, 0, 0, 0.1);
            border: 1px solid #efefef;
            padding: 25px;
            z-index: 1000;
            display: none; 
            opacity: 0;
            transform: translateY(20px);
            transition: opacity 0.3s ease, transform 0.3s ease;
        }
        .admin-popup-panel.show {
            display: block;
            opacity: 1;
            transform: translateY(0);
        }
        .btn-close-popup {
            background: none; border: none; color: #7a7a7a; font-size: 20px; cursor: pointer; padding: 0; line-height: 1;
        }
        .btn-close-popup:hover { color: #111; }
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

<div class="sidebar">
    <a href="#" class="sidebar-logo">  </a>
    <div class="sidebar-menu">
        <a href="#" class="sidebar-icon active" title="Dashboard">
            <i class="fi fi-rr-home"></i>
        </a>
        <a href="admin-portal?action=logout" class="sidebar-icon" title="Déconnexion">
            <i class="fi fi-rr-exit"></i>
        </a>
    </div>
    <div style="font-size: 11px; color: #555;">  </div>
</div>

<div class="main-content">

    <div class="row g-4 mb-5">
        <div class="col-lg-6">
            <div class="overview-card d-flex flex-column justify-content-between" style="min-height: 250px;">
                <div>
                    <h5 class="fw-bold text-uppercase mb-1" style="font-size: 11px; letter-spacing: 1px; opacity: 0.8;">Votre solde</h5>
                    <h2 class="fw-extrabold mb-0" style="font-size: 38px;"><%= totalRecettes %> EUR</h2>
                </div>
                <div style="font-size: 11px; opacity: 0.7; text-transform: uppercase; border-top: 1px solid rgba(255,255,255,0.15);" class="pt-2">
                    Money Fast
                </div>
            </div>
        </div>

        <div class="col-lg-6">
            <div class="sub-card d-flex flex-column justify-content-between" style="min-height: 250px;">
                <h6 class="fw-bold text-uppercase text-muted mb-2" style="font-size: 11px; letter-spacing: 0.5px;">Ajouter un Taux</h6>
                <form action="admin-dashboard" method="post">
                    <input type="hidden" name="action" value="addTaux">
                    
                    <div class="row g-2 mb-2">
                        <div class="col-3">
                            <input type="text" class="form-control form-control-dashboard text-center fw-bold bg-light" value="1" readonly>
                        </div>
                        <div class="col-9">
                            <select name="deviseSource" class="form-select form-control-dashboard" required>
                                <% if (listeDevises != null) {
                                       for (Devise d : listeDevises) { %>
                                        <option value="<%= d.getIdDevise() %>"><%= d.getLibelle() %></option>
                                <% } } %>
                            </select>
                        </div>
                    </div>

                    <div class="row g-2 mb-3">
                        <div class="col-3">
                            <input type="text" name="valeurTaux" class="form-control form-control-dashboard text-center fw-bold" placeholder="Taux" required>
                        </div>
                        <div class="col-9">
                            <select name="deviseDestination" class="form-select form-control-dashboard" required>
                                <% if (listeDevises != null) {
                                       for (Devise d : listeDevises) { %>
                                        <option value="<%= d.getIdDevise() %>"><%= d.getLibelle() %></option>
                                <% } } %>
                            </select>
                        </div>
                    </div>

                    <button type="submit" class="btn-admin-submit">Enregistrer</button>
                </form>
            </div>
        </div>
    </div>

    <div class="row g-4 mt-2">
        <div class="col-lg-6">
            <div class="bottom-card" style="min-height: 380px;">
                <div class="badge-top-icon">
                    <i class="fi fi-rr-globe"></i>
                </div>
                
                <div class="d-flex justify-content-between align-items-center mb-3 mt-3">
                    <h6 class="fw-bold text-uppercase text-muted mb-0" style="font-size: 11px; letter-spacing: 0.5px;">Restrictions de Pays</h6>

                    <input type="text" id="countrySearch" class="form-control form-control-dashboard py-1" style="max-width: 180px; font-size: 12px;" placeholder="Chercher un pays..." onkeyup="filterCountries()">
                </div>
                
                <div style="max-height: 250px; overflow-y: auto;">
                    <table class="table align-middle" style="font-size: 12px; margin-bottom: 0;" id="countryTable">
                        <tbody>
                            <% if (listePays != null) {
                                   for (Pays p : listePays) {
                                       boolean isAutorise = "autorise".equals(p.getStatut());
                            %>
                                <tr>
                                    <td class="fw-bold"><%= p.getLibelle() %></td>
                                    <td>
                                        <span class="badge <%= isAutorise ? "bg-success-subtle text-success" : "bg-danger-subtle text-danger" %>" style="font-size: 10px;">
                                            <%= isAutorise ? "Autorisé" : "Interdit" %>
                                        </span>
                                    </td>
                                    <td class="text-end">
                                        <form action="admin-dashboard" method="post" class="d-inline">
                                            <input type="hidden" name="action" value="togglePays">
                                            <input type="hidden" name="idPays" value="<%= p.getIdPays() %>">
                                            <input type="hidden" name="statut" value="<%= p.getStatut() %>">
                                            <button type="submit" class="btn btn-sm btn-outline-dark rounded-pill px-2 py-1" style="font-size: 10px;">
                                                Modifier
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            <% } } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="col-lg-6">
            <div class="bottom-card" style="min-height: 380px;">
                <div class="badge-top-icon">
                    <i class="fi fi-rr-chart-histogram"></i>
                </div>
                <h6 class="fw-bold text-uppercase text-muted mb-3 mt-3" style="font-size: 11px; letter-spacing: 0.5px;">Taux de Change</h6>
                <div style="max-height: 250px; overflow-y: auto;">
                    <table class="table align-middle" style="font-size: 12px; margin-bottom: 0;">
                        <thead>
                            <tr>
                                <th>Devise to Devise</th>
                                <th>Taux</th>
                                <th class="text-end">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (listeTaux != null) {
                                   for (TauxDeChange t : listeTaux) {
                                       String labelSrc = metadataRepository.findLibelleDevise(t.getDeviseSource());
                                       String labelDest = metadataRepository.findLibelleDevise(t.getDeviseDestination());
                            %>
                                <tr>
                                    <!-- Colonne 1 : Devise to Devise (Ex: USD to MGA) -->
                                    <td class="fw-bold"><%= labelSrc %> to <%= labelDest %></td>
                                    
                                    <td>
                                        <form action="admin-dashboard" method="post" class="d-flex align-items-center">
                                            <input type="hidden" name="action" value="updateTaux">
                                            <input type="hidden" name="idTaux" value="<%= t.getIdTaux() %>">
                                            <div class="input-group input-group-sm" style="max-width: 140px;">
                                                <span class="input-group-text bg-light fw-bold" style="font-size: 11px;">1=</span>
                                                <input type="text" name="nouveauTaux" class="form-control text-center fw-bold" value="<%= df.format(t.getTauxApplication()) %>" required style="font-size: 11px;">
                                                <button class="btn btn-dark d-flex align-items-center justify-content-center" type="submit" title="Mettre à jour la valeur" style="padding: 9px 12px;">
    												<i class="fi fi-rr-disk" style="font-size: 13px; line-height: 1;"></i>
												</button>
                                            </div>
                                        </form>
                                    </td>
                                    
                                    <td class="text-end">
                                        <form action="admin-dashboard" method="post" onsubmit="return confirm('Voulez-vous supprimer définitivement ce taux de change ?');" class="d-inline">
                                            <input type="hidden" name="action" value="deleteTaux">
                                            <input type="hidden" name="idTaux" value="<%= t.getIdTaux() %>">
                                            <button type="submit" class="btn btn-sm btn-outline-danger rounded-circle d-inline-flex align-items-center justify-content-center" style="width: 30px; height: 30px; padding: 0;" title="Supprimer définitivement">
											    <i class="fi fi-rr-trash" style="font-size: 11px; line-height: 1;"></i>
											</button>
                                            
                                        </form>
                                    </td>
                                </tr>
                            <% } } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>

<div class="right-panel">
    <h5 class="fw-bold mb-4">Clients</h5>
    <div style="max-height: calc(100vh - 120px); overflow-y: auto; padding-right: 5px;">
        <% if (listeClients != null && !listeClients.isEmpty()) {
               for (Client c : listeClients) {
                   Compte co = compteRepository.findByTelephone(c.getNumeroTelephone());
                   double solde = co != null ? co.getSolde() : 0.00;
                   String initiales = "" + c.getNom().charAt(0) + c.getPrenom().charAt(0);
        %>

            <div class="swipe-container mb-3" id="swipe-container-<%= c.getIdClient() %>">
                
                <div class="swipe-behind">
                    <form action="admin-dashboard" method="post" id="delete-form-<%= c.getIdClient() %>" onsubmit="return confirmDelete(event, '<%= c.getIdClient() %>')">
                        <input type="hidden" name="action" value="deleteClient">
                        <input type="hidden" name="idClient" value="<%= c.getIdClient() %>">
                        <button type="submit" class="border-0 bg-transparent text-white w-100 h-100 d-flex align-items-center justify-content-center" style="width: 80px;">
                            <i class="fi fi-rr-trash" style="font-size: 16px;"></i>
                        </button>
                    </form>
                </div>
                
                <div class="swipe-front d-flex align-items-center justify-content-between p-2" 
                     id="swipe-front-<%= c.getIdClient() %>"
                     onmousedown="startSwipe(event, '<%= c.getIdClient() %>')"
                     ontouchstart="startSwipe(event, '<%= c.getIdClient() %>')">
                    
                    <div class="d-flex align-items-center gap-2">
                        <div class="avatar-circle"><%= initiales.toUpperCase() %></div>
                        <div>
                            <div class="fw-bold" style="font-size: 13px;"><%= c.getNom().toUpperCase() %> <%= c.getPrenom() %></div>
                            <div class="text-muted" style="font-size: 11px;"><%= c.getNumeroTelephone() %></div>
                        </div>
                    </div>
                    <div class="text-end pe-2">
                        <div class="fw-bold text-success" style="font-size: 13px;"><%= solde %> MGA</div>
                        <div class="text-muted" style="font-size: 9px; opacity: 0.7;">Glisser ➔</div>
                    </div>
                </div>

            </div>
        <% } } else { %>
            <div class="text-muted text-center py-4" style="font-size: 13px;">Aucun client</div>
        <% } %>
    </div>
</div>

<button class="fab-admin" onclick="toggleAdminPopup()" title="Équipe d'Administration">
    <i class="fi fi-rr-users-alt" style="line-height: 1; display: block; margin-top: 4px;"></i>
</button>

<div id="adminPopup" class="admin-popup-panel">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h6 class="fw-bold text-uppercase text-muted mb-0" style="font-size: 11px; letter-spacing: 0.5px;">Nouveau Admin</h6>
        <button type="button" class="btn-close-popup" onclick="toggleAdminPopup()">&times;</button>
    </div>
    <form action="admin-dashboard" method="post">
        <input type="hidden" name="action" value="addAdmin">
        <div class="mb-2">
            <input type="text" name="username" class="form-control form-control-dashboard" placeholder="Nom d'utilisateur" required>
        </div>
        <div class="mb-2">
            <input type="email" name="email" class="form-control form-control-dashboard" placeholder="Adresse Email" required>
        </div>
        <div class="mb-3">
            <input type="password" name="password" class="form-control form-control-dashboard" placeholder="Mot de passe" required>
        </div>
        <button type="submit" class="btn-admin-submit py-2">Enregistrer</button>
    </form>
</div>

<script>
    function toggleAdminPopup() {
        const popup = document.getElementById('adminPopup');
        if (popup.classList.contains('show')) {
            popup.classList.remove('show');
            setTimeout(() => { popup.style.display = 'none'; }, 300);
        } else {
            popup.style.display = 'block';
            setTimeout(() => { popup.classList.add('show'); }, 10);
        }
    }

    function filterCountries() {
        let input = document.getElementById("countrySearch");
        let filter = input.value.toLowerCase();
        let table = document.getElementById("countryTable");
        let tr = table.getElementsByTagName("tr");
        
        for (let i = 0; i < tr.length; i++) {
            let td = tr[i].getElementsByTagName("td")[0]; 
            if (td) {
                let textValue = td.textContent || td.innerText;
                if (textValue.toLowerCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
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
    });
//swip
    let startX = 0;
    let currentX = 0;
    let activeClientId = null;
    let activeFrontElement = null;
    let isDragging = false;

    function startSwipe(e, clientId) {
        isDragging = true;
        activeClientId = clientId;
        activeFrontElement = document.getElementById('swipe-front-' + clientId);
        
        startX = e.type.startsWith('touch') ? e.touches[0].clientX : e.clientX;
        activeFrontElement.style.transition = 'none'; 

        document.addEventListener('mousemove', moveSwipe);
        document.addEventListener('mouseup', endSwipe);
        document.addEventListener('touchmove', moveSwipe);
        document.addEventListener('touchend', endSwipe);
    }

    function moveSwipe(e) {
        if (!isDragging || !activeFrontElement) return;
        currentX = e.type.startsWith('touch') ? e.touches[0].clientX : e.clientX;
        let diffX = currentX - startX;

        if (diffX < 0) diffX = 0;
        if (diffX > 80) diffX = 80;

        activeFrontElement.style.transform = "translateX(" + diffX + "px)";
    }

    function endSwipe() {
        if (!isDragging || !activeFrontElement) return;
        isDragging = false;

        activeFrontElement.style.transition = 'transform 0.3s cubic-bezier(0.25, 0.8, 0.25, 1)';

        let diffX = currentX - startX;
        
        if (diffX > 40) {
            activeFrontElement.style.transform = 'translateX(80px)';
        } else {
            activeFrontElement.style.transform = 'translateX(0)';
        }

        document.removeEventListener('mousemove', moveSwipe);
        document.removeEventListener('mouseup', endSwipe);
        document.removeEventListener('touchmove', moveSwipe);
        document.removeEventListener('touchend', endSwipe);
    }

    function confirmDelete(event, clientId) {
        event.preventDefault(); 
        
        if (confirm("Voulez-vous supprimer définitivement ce client et son compte associé ?")) {
            event.target.submit();
        } else {
            const front = document.getElementById('swipe-front-' + clientId);
            if (front) {
                front.style.transform = 'translateX(0)';
            }
        }
    }
</script>

</body>
</html>