<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.moneyfast.model.Admin" %>
<%@ page import="com.moneyfast.model.Client" %>
<%@ page import="com.moneyfast.model.Compte" %>
<%@ page import="com.moneyfast.model.Statistics" %>
<%@ page import="com.moneyfast.model.ActiveClientStat" %>
<%@ page import="com.moneyfast.model.Pays" %>
<%@ page import="com.moneyfast.model.Devise" %>
<%@ page import="com.moneyfast.model.TauxDeChange" %>
<%@ page import="com.moneyfast.model.Frais" %>
<%@ page import="com.moneyfast.repository.CompteRepository" %>
<%@ page import="com.moneyfast.repository.MetadataRepository" %>
<%@ page import="com.moneyfast.repository.repository_impl.CompteRepositoryImpl" %>
<%@ page import="com.moneyfast.repository.repository_impl.MetadataRepositoryImpl" %>
<%
    Admin admin = (Admin) session.getAttribute("userAdmin");
    
    @SuppressWarnings("unchecked")
    List<Client> listeClients = (List<Client>) request.getAttribute("listeClients");
    @SuppressWarnings("unchecked")
    List<Pays> listePays = (List<Pays>) request.getAttribute("listePays");
    @SuppressWarnings("unchecked")
    List<TauxDeChange> listeTaux = (List<TauxDeChange>) request.getAttribute("listeTaux");
    @SuppressWarnings("unchecked")
    List<Devise> listeDevises = (List<Devise>) request.getAttribute("listeDevises");
    @SuppressWarnings("unchecked")
    List<Frais> listeFrais = (List<Frais>) request.getAttribute("listeFrais");
    
    Statistics stats = (Statistics) request.getAttribute("stats");
    
    CompteRepository compteRepository = new CompteRepositoryImpl();
    MetadataRepository metadataRepository = new MetadataRepositoryImpl();
    DecimalFormat df = new DecimalFormat("0.######");
    
    // Valeurs actuelles du filtre de dates (pour ré-afficher les inputs remplis)
    String dateDebutParam = request.getParameter("dateDebut");
    String dateFinParam = request.getParameter("dateFin");
    String filterParam = request.getParameter("filter");
    boolean filterByMontant = "montant".equals(filterParam);
    
    String activeTabParam = request.getParameter("tab");
    boolean statsActive = "stats".equals(activeTabParam);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel='stylesheet' href='https://cdn-uicons.flaticon.com/2.1.0/uicons-regular-rounded/css/uicons-regular-rounded.css'>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/admin-dashboard.css">
    </head>
    <body>

        <%@ include file="admin/_toasts.jspf" %>
        <%@ include file="admin/_sidebar.jspf" %>

        <div class="main-content">

            <%@ include file="admin/_topbar.jspf" %>

            <div class="tab-content-panel<%= statsActive ? " d-none" : "" %>" id="tab-overview">
                <%@ include file="admin/_overview-cards.jspf" %>
                <%@ include file="admin/_bottom-cards.jspf" %>
            </div>

            <div class="tab-content-panel<%= statsActive ? "" : " d-none" %>" id="tab-stats">
                <%@ include file="admin/_statistics.jspf" %>
            </div>

        </div>

        <%@ include file="admin/_admin-popup.jspf" %>

        <script src="<%= request.getContextPath() %>/assets/js/admin-dashboard.js"></script>
    </body>
</html>