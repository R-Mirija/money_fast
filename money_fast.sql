-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 01 juil. 2026 à 18:31
-- Version du serveur : 8.3.0
-- Version de PHP : 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `money_fast`
--

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

DROP TABLE IF EXISTS `clients`;
CREATE TABLE IF NOT EXISTS `clients` (
  `id_client` int NOT NULL AUTO_INCREMENT,
  `numero_telephone` varchar(20) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `sexe` set('homme','femme','autre') NOT NULL,
  `pays` int NOT NULL,
  `devise_preferee` int NOT NULL,
  `mail` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `date_inscription` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `statut_client` set('actif','suspendu','bloqué') NOT NULL,
  `date_derniere_connexion` timestamp NOT NULL,
  PRIMARY KEY (`id_client`),
  UNIQUE KEY `unique_mail` (`mail`),
  UNIQUE KEY `unique_phone_number` (`numero_telephone`),
  KEY `fk_clients_pays` (`pays`),
  KEY `fk_clients_devise` (`devise_preferee`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `comptes`
--

DROP TABLE IF EXISTS `comptes`;
CREATE TABLE IF NOT EXISTS `comptes` (
  `id_compte` int NOT NULL AUTO_INCREMENT,
  `id_client` int NOT NULL,
  `numero_compte` int NOT NULL,
  `devise` int NOT NULL,
  `solde` double NOT NULL,
  `plalfond_journalier` double UNSIGNED NOT NULL,
  `plafond_transaction` double UNSIGNED NOT NULL,
  `date_ouverture` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `statut_compte` set('actif','suspendu','fermé') NOT NULL,
  PRIMARY KEY (`id_compte`),
  KEY `fk_comptes_client` (`id_client`),
  KEY `fk_comptes_devise` (`devise`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `devises`
--

DROP TABLE IF EXISTS `devises`;
CREATE TABLE IF NOT EXISTS `devises` (
  `id_devise` int NOT NULL AUTO_INCREMENT,
  `libelle` varchar(100) NOT NULL,
  PRIMARY KEY (`id_devise`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `devises`
--

INSERT INTO `devises` (`id_devise`, `libelle`) VALUES
(1, 'Ariary');

-- --------------------------------------------------------

--
-- Structure de la table `frais_envoi`
--

DROP TABLE IF EXISTS `frais_envoi`;
CREATE TABLE IF NOT EXISTS `frais_envoi` (
  `id_frais` int NOT NULL AUTO_INCREMENT,
  `code_frais` int NOT NULL,
  `devise_frais` int NOT NULL,
  `montant_min` double UNSIGNED NOT NULL,
  `montant_max` double UNSIGNED NOT NULL,
  `type_frais` int NOT NULL,
  `valeur_frais` double UNSIGNED NOT NULL,
  `date_debut_validite` timestamp NOT NULL,
  `date_fin_validite` timestamp NOT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_frais`),
  UNIQUE KEY `unique_frais` (`code_frais`),
  KEY `fk_frais_devise` (`devise_frais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
CREATE TABLE IF NOT EXISTS `notifications` (
  `id_notification` int NOT NULL AUTO_INCREMENT,
  `id_transfert` int NOT NULL,
  `destinataire` int NOT NULL,
  `type_notification` set('SMS','Mail') NOT NULL,
  `message` text NOT NULL,
  `date_envoi` timestamp NOT NULL,
  `statut_envoi` set('envoyé','échoué','en attente') NOT NULL,
  `erreur_message` text NOT NULL,
  PRIMARY KEY (`id_notification`),
  KEY `fk_notifications_transfert` (`id_transfert`),
  KEY `fk_notifications_client` (`destinataire`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `pays`
--

DROP TABLE IF EXISTS `pays`;
CREATE TABLE IF NOT EXISTS `pays` (
  `id_pays` int NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) NOT NULL,
  PRIMARY KEY (`id_pays`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `pays`
--

INSERT INTO `pays` (`id_pays`, `libelle`) VALUES
(1, 'Madagascar');

-- --------------------------------------------------------

--
-- Structure de la table `taux_de_change`
--

DROP TABLE IF EXISTS `taux_de_change`;
CREATE TABLE IF NOT EXISTS `taux_de_change` (
  `id_taux` int NOT NULL AUTO_INCREMENT,
  `code_taux` int NOT NULL,
  `devise_source` int NOT NULL,
  `devise_destination` int NOT NULL,
  `montant_min` double UNSIGNED NOT NULL,
  `montant_max` int UNSIGNED NOT NULL,
  `taux_application` float NOT NULL,
  `date_debut_validite` timestamp NOT NULL,
  `date_fin_validite` timestamp NOT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_taux`),
  UNIQUE KEY `unique_taux` (`code_taux`),
  KEY `fk_taux_devise_source` (`devise_source`),
  KEY `fk_taux_devise_dest` (`devise_destination`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `transferts`
--

DROP TABLE IF EXISTS `transferts`;
CREATE TABLE IF NOT EXISTS `transferts` (
  `id_transfert` int NOT NULL AUTO_INCREMENT,
  `code_transfert` int NOT NULL,
  `id_compte_source` int NOT NULL,
  `id_compte_destination` int NOT NULL,
  `montant_envoye` double UNSIGNED NOT NULL,
  `montant_recu` double UNSIGNED NOT NULL,
  `frais` double UNSIGNED NOT NULL,
  `taux_applique` int NOT NULL,
  `devise_source` int NOT NULL,
  `devise_destination` int NOT NULL,
  `date_transfert` timestamp NOT NULL,
  `raison` text NOT NULL,
  `statut_transfert` set('en attente','confirmé','en cours','complété','échoué','annulé') NOT NULL,
  `date_confirmation` timestamp NOT NULL,
  `reference_externe` varchar(255) NOT NULL,
  PRIMARY KEY (`id_transfert`),
  UNIQUE KEY `unique_transfert` (`code_transfert`),
  KEY `fk_transferts_compte_source` (`id_compte_source`),
  KEY `fk_transferts_compte_dest` (`id_compte_destination`),
  KEY `fk_transferts_devise_source` (`devise_source`),
  KEY `fk_transferts_devise_dest` (`devise_destination`),
  KEY `fk_transferts_taux` (`taux_applique`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `clients`
--
ALTER TABLE `clients`
  ADD CONSTRAINT `fk_clients_devise` FOREIGN KEY (`devise_preferee`) REFERENCES `devises` (`id_devise`),
  ADD CONSTRAINT `fk_clients_pays` FOREIGN KEY (`pays`) REFERENCES `pays` (`id_pays`);

--
-- Contraintes pour la table `comptes`
--
ALTER TABLE `comptes`
  ADD CONSTRAINT `fk_comptes_client` FOREIGN KEY (`id_client`) REFERENCES `clients` (`id_client`),
  ADD CONSTRAINT `fk_comptes_devise` FOREIGN KEY (`devise`) REFERENCES `devises` (`id_devise`);

--
-- Contraintes pour la table `frais_envoi`
--
ALTER TABLE `frais_envoi`
  ADD CONSTRAINT `fk_frais_devise` FOREIGN KEY (`devise_frais`) REFERENCES `devises` (`id_devise`);

--
-- Contraintes pour la table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `fk_notifications_client` FOREIGN KEY (`destinataire`) REFERENCES `clients` (`id_client`),
  ADD CONSTRAINT `fk_notifications_transfert` FOREIGN KEY (`id_transfert`) REFERENCES `transferts` (`id_transfert`);

--
-- Contraintes pour la table `taux_de_change`
--
ALTER TABLE `taux_de_change`
  ADD CONSTRAINT `fk_taux_devise_dest` FOREIGN KEY (`devise_destination`) REFERENCES `devises` (`id_devise`),
  ADD CONSTRAINT `fk_taux_devise_source` FOREIGN KEY (`devise_source`) REFERENCES `devises` (`id_devise`);

--
-- Contraintes pour la table `transferts`
--
ALTER TABLE `transferts`
  ADD CONSTRAINT `fk_transferts_compte_dest` FOREIGN KEY (`id_compte_destination`) REFERENCES `comptes` (`id_compte`),
  ADD CONSTRAINT `fk_transferts_compte_source` FOREIGN KEY (`id_compte_source`) REFERENCES `comptes` (`id_compte`),
  ADD CONSTRAINT `fk_transferts_devise_dest` FOREIGN KEY (`devise_destination`) REFERENCES `devises` (`id_devise`),
  ADD CONSTRAINT `fk_transferts_devise_source` FOREIGN KEY (`devise_source`) REFERENCES `devises` (`id_devise`),
  ADD CONSTRAINT `fk_transferts_taux` FOREIGN KEY (`taux_applique`) REFERENCES `taux_de_change` (`id_taux`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
