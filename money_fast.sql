-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 08 juil. 2026 à 10:16
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
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `pays`
--

INSERT INTO `pays` (`id_pays`, `libelle`) VALUES
(1, 'Madagascar'),
(2, 'United States of America'),
(3, 'Afghanistan'),
(4, 'Albania'),
(5, 'Algeria'),
(6, 'Andorra'),
(7, 'Angola'),
(8, 'Antigua & Deps'),
(9, 'Argentina'),
(10, 'Armenia'),
(11, 'Australia'),
(12, 'Austria'),
(13, 'Azerbaijan'),
(14, 'Bahamas'),
(15, 'Bahrain'),
(16, 'Bangladesh'),
(17, 'Barbados'),
(18, 'Belarus'),
(19, 'Belgium'),
(20, 'Belize'),
(21, 'Benin'),
(22, 'Bhutan'),
(23, 'Bolivia'),
(24, 'Bosnia Herzegovina'),
(25, 'Botswana'),
(26, 'Brazil'),
(27, 'Brunei'),
(28, 'Bulgaria'),
(29, 'Burkina'),
(30, 'Burma'),
(31, 'Burundi'),
(32, 'Cambodia'),
(33, 'Cameroon'),
(34, 'Canada'),
(35, 'Cape Verde'),
(36, 'Central African Rep'),
(37, 'Chad'),
(38, 'Chile'),
(39, 'Republic of China'),
(40, 'Colombia'),
(41, 'Comoros'),
(42, 'Democratic Republic of the Congo'),
(43, 'Republic of the Congo'),
(44, 'Costa Rica'),
(45, 'Croatia'),
(46, 'Cuba'),
(47, 'Cyprus'),
(48, 'Czech Republic'),
(49, 'Danzig'),
(50, 'Denmark'),
(51, 'Djibouti'),
(52, 'Dominica'),
(53, 'Dominican Republic'),
(54, 'East Timor'),
(55, 'Ecuador'),
(56, 'Egypt'),
(57, 'El Salvador'),
(58, 'Equatorial Guinea'),
(59, 'Eritrea'),
(60, 'Estonia'),
(61, 'Ethiopia'),
(62, 'Fiji'),
(63, 'Finland'),
(64, 'France'),
(65, 'Gabon'),
(66, 'Gaza Strip'),
(67, 'The Gambia'),
(68, 'Georgia'),
(69, 'Germany'),
(70, 'Ghana'),
(71, 'Greece'),
(72, 'Grenada'),
(73, 'Guatemala'),
(74, 'Guinea'),
(75, 'Guinea-Bissau'),
(76, 'Guyana'),
(77, 'Haiti'),
(78, 'Holy Roman Empire'),
(79, 'Honduras'),
(80, 'Hungary'),
(81, 'Iceland'),
(82, 'India'),
(83, 'Indonesia'),
(84, 'Iran'),
(85, 'Iraq'),
(86, 'Republic of Ireland'),
(87, 'Israel'),
(88, 'Italy'),
(89, 'Ivory Coast'),
(90, 'Jamaica'),
(91, 'Japan'),
(92, 'Jonathanland'),
(93, 'Jordan'),
(94, 'Kazakhstan'),
(95, 'Kenya'),
(96, 'Kiribati'),
(97, 'North Korea'),
(98, 'South Korea'),
(99, 'Kosovo'),
(100, 'Kuwait'),
(101, 'Kyrgyzstan'),
(102, 'Laos'),
(103, 'Latvia'),
(104, 'Lebanon'),
(105, 'Lesotho'),
(106, 'Liberia'),
(107, 'Libya'),
(108, 'Liechtenstein'),
(109, 'Lithuania'),
(110, 'Luxembourg'),
(111, 'Macedonia'),
(112, 'Malawi'),
(113, 'Malaysia'),
(114, 'Maldives'),
(115, 'Mali'),
(116, 'Malta'),
(117, 'Marshall Islands'),
(118, 'Mauritania'),
(119, 'Mauritius'),
(120, 'Mexico'),
(121, 'Micronesia'),
(122, 'Moldova'),
(123, 'Monaco'),
(124, 'Mongolia'),
(125, 'Montenegro'),
(126, 'Morocco'),
(127, 'Mount Athos'),
(128, 'Mozambique'),
(129, 'Namibia'),
(130, 'Nauru'),
(131, 'Nepal'),
(132, 'Newfoundland'),
(133, 'Netherlands'),
(134, 'New Zealand'),
(135, 'Nicaragua'),
(136, 'Niger'),
(137, 'Nigeria'),
(138, 'Norway'),
(139, 'Oman'),
(140, 'Ottoman Empire'),
(141, 'Pakistan'),
(142, 'Palau'),
(143, 'Panama'),
(144, 'Papua New Guinea'),
(145, 'Paraguay'),
(146, 'Peru'),
(147, 'Philippines'),
(148, 'Poland'),
(149, 'Portugal'),
(150, 'Prussia'),
(151, 'Qatar'),
(152, 'Romania'),
(153, 'Rome'),
(154, 'Russian Federation'),
(155, 'Rwanda'),
(156, 'Grenadines'),
(157, 'Samoa'),
(158, 'San Marino'),
(159, 'Sao Tome & Principe'),
(160, 'Saudi Arabia'),
(161, 'Senegal'),
(162, 'Serbia'),
(163, 'Seychelles'),
(164, 'Sierra Leone'),
(165, 'Singapore'),
(166, 'Slovakia'),
(167, 'Slovenia'),
(168, 'Solomon Islands'),
(169, 'Somalia'),
(170, 'South Africa'),
(171, 'Spain'),
(172, 'Sri Lanka'),
(173, 'Sudan'),
(174, 'Suriname'),
(175, 'Swaziland'),
(176, 'Sweden'),
(177, 'Switzerland'),
(178, 'Syria'),
(179, 'Tajikistan'),
(180, 'Tanzania'),
(181, 'Thailand'),
(182, 'Togo'),
(183, 'Tonga'),
(184, 'Trinidad & Tobago'),
(185, 'Tunisia'),
(186, 'Turkey'),
(187, 'Turkmenistan'),
(188, 'Tuvalu'),
(189, 'Uganda'),
(190, 'Ukraine'),
(191, 'United Arab Emirates'),
(192, 'United Kingdom'),
(193, 'Uruguay'),
(194, 'Uzbekistan'),
(195, 'Vanuatu'),
(196, 'Vatican City'),
(197, 'Venezuela'),
(198, 'Vietnam'),
(199, 'Yemen'),
(200, 'Zambia'),
(201, 'Zimbabwe');

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
