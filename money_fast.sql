-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : mar. 21 juil. 2026 à 14:41
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

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
-- Structure de la table `admins`
--

CREATE TABLE `admins` (
  `id_admin` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `admins`
--

INSERT INTO `admins` (`id_admin`, `username`, `password`, `email`) VALUES
(1, 'Rakoto', '979d529308fac5311007da671d6b36d636dd10701abcceca2610cece94fbdc26', 'rakoto@gmail.com'),
(2, 'Mahiratra', '979d529308fac5311007da671d6b36d636dd10701abcceca2610cece94fbdc26', 'mahiratra@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

CREATE TABLE `clients` (
  `id_client` int(11) NOT NULL,
  `numero_telephone` varchar(20) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `sexe` set('homme','femme','autre') NOT NULL,
  `pays` int(11) NOT NULL,
  `devise_preferee` int(11) NOT NULL,
  `mail` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `date_inscription` timestamp NOT NULL DEFAULT current_timestamp(),
  `statut_client` set('actif','suspendu','bloqué') NOT NULL DEFAULT 'actif',
  `date_derniere_connexion` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `clients`
--

INSERT INTO `clients` (`id_client`, `numero_telephone`, `nom`, `prenom`, `sexe`, `pays`, `devise_preferee`, `mail`, `password`, `date_inscription`, `statut_client`, `date_derniere_connexion`) VALUES
(2, '0386816822', 'rakoto', 'tadiavina', 'femme', 2, 1, 'tadiavinah@gmail.com', '979d529308fac5311007da671d6b36d636dd10701abcceca2610cece94fbdc26', '2026-07-19 05:48:09', 'actif', NULL),
(3, '0341513106', 'harry', 'tadiavina', 'homme', 1, 1, 'mahiratra@gmail.com', '979d529308fac5311007da671d6b36d636dd10701abcceca2610cece94fbdc26', '2026-07-19 06:45:56', 'actif', NULL),
(4, '0327889811', 'Harry', 'Potter', 'homme', 72, 6, 'harrypotter@gmail.com', '979d529308fac5311007da671d6b36d636dd10701abcceca2610cece94fbdc26', '2026-07-21 02:40:39', 'actif', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `comptes`
--

CREATE TABLE `comptes` (
  `id_compte` int(11) NOT NULL,
  `id_client` int(11) NOT NULL,
  `numero_compte` int(11) NOT NULL,
  `devise` int(11) NOT NULL,
  `solde` double NOT NULL DEFAULT 0,
  `plafond_journalier` double UNSIGNED NOT NULL DEFAULT 5000,
  `plafond_transaction` double UNSIGNED NOT NULL DEFAULT 1000,
  `date_ouverture` timestamp NOT NULL DEFAULT current_timestamp(),
  `statut_compte` set('actif','suspendu','fermé') NOT NULL DEFAULT 'actif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `comptes`
--

INSERT INTO `comptes` (`id_compte`, `id_client`, `numero_compte`, `devise`, `solde`, `plafond_journalier`, `plafond_transaction`, `date_ouverture`, `statut_compte`) VALUES
(2, 2, 161140161, 1, 5000013, 5000, 1000, '2026-07-19 05:48:09', 'actif'),
(3, 3, 608707606, 1, 309095, 5000, 1000, '2026-07-19 06:45:56', 'actif'),
(4, 4, 234936214, 6, 985, 5000, 1000, '2026-07-21 02:40:39', 'actif');

-- --------------------------------------------------------

--
-- Structure de la table `devises`
--

CREATE TABLE `devises` (
  `id_devise` int(11) NOT NULL,
  `libelle` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `devises`
--

INSERT INTO `devises` (`id_devise`, `libelle`) VALUES
(1, 'Malagasy Ariary (MGA)'),
(2, 'Afghani (AFN)'),
(3, 'Rand (ZAR)'),
(4, 'Lek (ALL)'),
(5, 'Dinars algériens (DZD)'),
(6, 'Euro (EUR)'),
(7, 'Kwanza (AOA)'),
(8, 'Dollar des Caraïbes orientales (XCD)'),
(9, 'Riyal Saoudiens (SAR)'),
(10, 'Peso Argentin (ARS)'),
(11, 'Dram Armenien (AMD)'),
(12, 'Aruban Florin (AWG)'),
(13, 'Australian Dollar (AUD)'),
(14, 'Azerbaijanian Manat (AZN)'),
(15, 'Dollar Bahaméen (BSD)'),
(16, 'Dinar Bahraini (BHD)'),
(17, 'Taka (BDT)'),
(18, 'Dollars Barbados (BBD)'),
(19, 'Dollar de Bélize (BZD)'),
(20, 'CFA Franc BCEAO (XOF)'),
(21, 'Dollar Bermudien (BMD)'),
(22, 'Ngultrum (BTN)'),
(23, 'Indian Rupee (INR)'),
(24, 'Ruble Biélorusse (BYR)'),
(25, 'Mvdol (BOV)'),
(26, 'Boliviano (BOB)'),
(27, 'Mark Convertible (BAM)'),
(28, 'Pula (BWP)'),
(29, 'Couronne Norvégienne (NOK)'),
(30, 'Brunei Dollar (BND)'),
(31, 'Real Brésilien (BRL)'),
(32, 'Lev Bulgare (BGN)'),
(33, 'Franc Burundi (BIF)'),
(34, 'Cabo Verde Escudo (CVE)'),
(35, 'Riel (KHR)'),
(36, 'Franc CFA BEAC (XAF)'),
(37, 'Dollar Canadien (CAD)'),
(38, 'Cayman Islands Dollar (KYD)'),
(39, 'Unidad de Fomento (CLF)'),
(40, 'Peso Chilien (CLP)'),
(41, 'Yuan Renminbi (CNY)'),
(42, 'Peso Colombien (COP)'),
(43, 'Unidad de Valor Real (COU)'),
(44, 'Franc Comorien (KMF)'),
(45, 'Le Franc Congolais (CDF)'),
(46, 'Dollar Néo-Zélandais (NZD)'),
(47, 'Won (KRW)'),
(48, 'Won Nord-coréen (KPW)'),
(49, 'Costa Rican Colon (CRC)'),
(50, 'Kuna (HRK)'),
(51, 'Peso Convertible (CUC)'),
(52, 'Peso Cubain (CUP)'),
(53, 'Florin des Antilles néerlandaises (ANG)'),
(54, 'Couronne Danoise (DKK)'),
(55, 'Franc Djiboutien (DJF)'),
(56, 'Pound Égyptien (EGP)'),
(57, 'Dollar US (USD)'),
(58, 'Nakfa (ERN)'),
(59, 'Birr Éthiopienne (ETB)'),
(60, 'Livre des Îles Malouines (FKP)'),
(61, 'Dollar des Fiji (FJD)'),
(62, 'SDR (Droit de tirage spécial) (XDR)'),
(63, 'Dalasi (GMD)'),
(64, 'Lari (GEL)'),
(65, 'Cedi du Ghana (GHS)'),
(66, 'Pound de Gibraltar (GIP)'),
(67, 'Quetzal (GTQ)'),
(68, 'Livre Sterling (GBP)'),
(69, 'Franc Guinéen (GNF)'),
(70, 'Dollar guyanien (GYD)'),
(71, 'Gourde (HTG)'),
(72, 'Lempira (HNL)'),
(73, 'Dollar de Hong Kong (HKD)'),
(74, 'Forint (HUF)'),
(75, 'Rupiah (IDR)'),
(76, 'Rial Iranien (IRR)'),
(77, 'Dinar Iraquien (IQD)'),
(78, 'Couronne Islandaise (ISK)'),
(79, 'Nouveau Sheqel Israélien (ILS)'),
(80, 'Dollars Jamaicain (JMD)'),
(81, 'Yen (JPY)'),
(82, 'Dinars Jordanien (JOD)'),
(83, 'Tenge (KZT)'),
(84, 'Shilling Kenyan (KES)'),
(85, 'Som (KGS)'),
(86, 'Dinar Kowaitien (KWD)'),
(87, 'Kip (LAK)'),
(88, 'Le Colon Salvadorien (SVC)'),
(89, 'Loti (LSL)'),
(90, 'Pound Libanais (LBP)'),
(91, 'Dollar du Liberia (LRD)'),
(92, 'Dinars Libien (LYD)'),
(93, 'Swiss Franc (CHF)'),
(94, 'Pataca (MOP)'),
(95, 'Denar (MKD)'),
(96, 'Kwacha (MWK)'),
(97, 'Ringgi Malaisien (MYR)'),
(98, 'Rufiyaa (MVR)'),
(99, 'Mauritius Rupee (MUR)'),
(100, 'Ouguiya (MRO)'),
(101, 'Peso Mexicain (MXN)'),
(102, 'Mexican Unidad de Inversion (UDI) (MXV)'),
(103, 'Leu Moldavien (MDL)'),
(104, 'Tugrik (MNT)'),
(105, 'Metical (MZN)'),
(106, 'Kyat (MMK)'),
(107, 'Dollar Namibien (NAD)'),
(108, 'Cordoba (NIO)'),
(109, 'Naira (NGN)'),
(110, 'Rupee Népalais (NPR)'),
(111, 'Rial Omani (OMR)'),
(112, 'Shilling Ougandais (UGX)'),
(113, 'Sum d\'Oubekistan (UZS)'),
(114, 'Rupee du Pakistan (PKR)'),
(115, 'Balboa (PAB)'),
(116, 'Kina (PGK)'),
(117, 'Guarani (PYG)'),
(118, 'Unité de compte de la BAD (XUA)'),
(119, 'Nouveau Sol (PEN)'),
(120, 'Peso Phillipins (PHP)'),
(121, 'Zloty (PLN)'),
(122, 'Franc CFP (XPF)'),
(123, 'Rial Qatari (QAR)'),
(124, 'Leu Roumain (RON)'),
(125, 'Ruble Russe (RUB)'),
(126, 'Franc Rwandais (RWF)'),
(127, 'Peso Dominicain (DOP)'),
(128, 'Couronne Tchèque (CZK)'),
(129, 'Livre de Saint Helene (SHP)'),
(130, 'Tala (WST)'),
(131, 'Dobra (STD)'),
(132, 'Dinar Serbe (RSD)'),
(133, 'Rupee des Seychelles (SCR)'),
(134, 'Leone (SLL)'),
(135, 'Dollar Singaporien (SGD)'),
(136, 'Sucre (XSU)'),
(137, 'Dollar des îles Solomon (SBD)'),
(138, 'Shilling Somalien (SOS)'),
(139, 'Livre Soudanais (SDG)'),
(140, 'Livre sud-soudanaise (SSP)'),
(141, 'Rupee Sri Lankais (LKR)'),
(142, 'WIR Euro (CHE)'),
(143, 'Franc WIR (CHW)'),
(144, 'Dollars du Surinam (SRD)'),
(145, 'Couronne Suédoise (SEK)'),
(146, 'Lilangeni (SZL)'),
(147, 'Pound Syrien (SYP)'),
(148, 'Somoni (TJS)'),
(149, 'Nouveau dollars Taiwanais (TWD)'),
(150, 'Shilling Tanzanien (TZS)'),
(151, 'Baht (THB)'),
(152, 'Pa’anga (TOP)'),
(153, 'Dollars de Trinidad et Tobago (TTD)'),
(154, 'Dinars Tunisiens (TND)'),
(155, 'Turkménistan Nouveau Manat (TMT)'),
(156, 'Livre Turque (TRY)'),
(157, 'Hryvnia (UAH)'),
(158, 'Dirham UAE (AED)'),
(159, 'Uruguay Peso en Unidades Indexadas (UYI)'),
(160, 'Peso Uruguayen (UYU)'),
(161, 'Vatu (VUV)'),
(162, 'Bolivar (VEF)'),
(163, 'Dong (VND)'),
(164, 'Rial du Yemen (YER)'),
(165, 'Kwacha Zambien (ZMW)'),
(166, 'Dollars du Zimbabwe (ZWL)');

-- --------------------------------------------------------

--
-- Structure de la table `frais_envoi`
--

CREATE TABLE `frais_envoi` (
  `id_frais` int(11) NOT NULL,
  `code_frais` int(11) NOT NULL,
  `devise_frais` int(11) NOT NULL,
  `montant_min` double UNSIGNED NOT NULL,
  `montant_max` double UNSIGNED NOT NULL,
  `type_frais` int(11) NOT NULL,
  `valeur_frais` double UNSIGNED NOT NULL,
  `date_debut_validite` timestamp NULL DEFAULT NULL,
  `date_fin_validite` timestamp NULL DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `frais_envoi`
--

INSERT INTO `frais_envoi` (`id_frais`, `code_frais`, `devise_frais`, `montant_min`, `montant_max`, `type_frais`, `valeur_frais`, `date_debut_validite`, `date_fin_validite`, `active`) VALUES
(1, 2001, 6, 0, 10000, 1, 5, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `notifications`
--

CREATE TABLE `notifications` (
  `id_notification` int(11) NOT NULL,
  `id_transfert` int(11) NOT NULL,
  `destinataire` int(11) NOT NULL,
  `type_notification` set('SMS','Mail') NOT NULL,
  `message` text NOT NULL,
  `date_envoi` timestamp NOT NULL DEFAULT current_timestamp(),
  `statut_envoi` set('envoyé','échoué','en attente') NOT NULL DEFAULT 'en attente',
  `erreur_message` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `notifications`
--

INSERT INTO `notifications` (`id_notification`, `id_transfert`, `destinataire`, `type_notification`, `message`, `date_envoi`, `statut_envoi`, `erreur_message`) VALUES
(1, 4, 2, 'Mail', 'Bonjour. Votre transfert de 10.0 EUR a été validé avec succès. Frais appliqués : 5.0 EUR. Code transaction : 558903514. Merci d\'utiliser MoneyFast.', '2026-07-20 13:29:03', 'envoyé', ''),
(2, 4, 3, 'SMS', 'Bonjour. Vous avez reçu un transfert de 50000.0 MGA. Code transaction : 558903514. Veuillez consulter votre solde sur MoneyFast.', '2026-07-20 13:29:03', 'envoyé', ''),
(3, 5, 2, 'Mail', 'Bonjour. Votre transfert de 10.0 EUR a été validé avec succès. Frais appliqués : 5.0 EUR. Code transaction : 844286966. Merci d\'utiliser MoneyFast.', '2026-07-20 13:30:11', 'envoyé', ''),
(4, 5, 3, 'SMS', 'Bonjour. Vous avez reçu un transfert de 50000.0 MGA. Code transaction : 844286966. Veuillez consulter votre solde sur MoneyFast.', '2026-07-20 13:30:11', 'envoyé', ''),
(5, 6, 2, 'Mail', 'Votre transfert de 1.0 MGA au 0341513106 a été effectué avec succès. Motif : Frais', '2026-07-20 15:47:18', 'envoyé', ''),
(6, 6, 3, 'SMS', 'Vous avez reçu 5000.0 MGA de la part de 0386816822. Motif : Frais', '2026-07-20 15:47:18', 'envoyé', ''),
(7, 7, 3, 'Mail', 'Votre transfert de 1000.0 MGA au 0386816822 a été effectué avec succès. Motif : Frais', '2026-07-20 16:21:41', 'envoyé', ''),
(8, 7, 2, 'SMS', 'Vous avez reçu 5000000.0 MGA de la part de 0341513106. Motif : Frais', '2026-07-20 16:21:41', 'envoyé', ''),
(9, 8, 4, 'Mail', 'Votre transfert de 10.0 EUR au 0341513106 a été effectué avec succès. Motif : Frais', '2026-07-21 03:00:46', 'envoyé', ''),
(10, 8, 3, 'SMS', 'Vous avez reçu 50000.0 MGA de la part de 0327889811. Motif : Frais', '2026-07-21 03:00:46', 'envoyé', ''),
(11, 9, 2, 'Mail', 'Votre transfert de 10.0 MGA au 0341513106 a été effectué avec succès. Motif : Frais', '2026-07-21 04:08:53', 'envoyé', ''),
(12, 9, 3, 'SMS', 'Vous avez reçu 50000.0 MGA de la part de 0386816822. Motif : Frais', '2026-07-21 04:08:53', 'envoyé', '');

-- --------------------------------------------------------

--
-- Structure de la table `pays`
--

CREATE TABLE `pays` (
  `id_pays` int(11) NOT NULL,
  `libelle` varchar(255) NOT NULL,
  `statut` varchar(20) NOT NULL DEFAULT 'autorise'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `pays`
--

INSERT INTO `pays` (`id_pays`, `libelle`, `statut`) VALUES
(1, 'Madagascar', 'autorise'),
(2, 'Afghanistan', 'autorise'),
(3, 'Afrique du Sud', 'autorise'),
(4, 'Albanie', 'autorise'),
(5, 'Algérie', 'autorise'),
(6, 'Allemagne', 'autorise'),
(7, 'Andorre', 'autorise'),
(8, 'Angola', 'autorise'),
(9, 'Anguilla', 'autorise'),
(10, 'Antarctique', 'autorise'),
(11, 'Antigua-et-Barbuda', 'autorise'),
(12, 'Arabie Saoudite', 'autorise'),
(13, 'Argentine', 'autorise'),
(14, 'Arménie', 'autorise'),
(15, 'Aruba', 'autorise'),
(16, 'Australie', 'autorise'),
(17, 'Autriche', 'autorise'),
(18, 'Azerbaïdjan', 'autorise'),
(19, 'Bahamas', 'autorise'),
(20, 'Bahreïn', 'autorise'),
(21, 'Bangladesh', 'autorise'),
(22, 'Barbade', 'autorise'),
(23, 'Belgique', 'autorise'),
(24, 'Belize', 'autorise'),
(25, 'Bénin', 'autorise'),
(26, 'Bermudes', 'autorise'),
(27, 'Bhoutan', 'autorise'),
(28, 'Biélorussie', 'autorise'),
(29, 'Bolivie', 'autorise'),
(30, 'Bosnie-Herzégovine', 'autorise'),
(31, 'Botswana', 'autorise'),
(32, 'Bouvet Île', 'autorise'),
(33, 'Brunei Darussalam', 'autorise'),
(34, 'Brésil', 'autorise'),
(35, 'Bulgarie', 'autorise'),
(36, 'Burkina Faso', 'autorise'),
(37, 'Burundi', 'autorise'),
(38, 'Cabo Verde', 'autorise'),
(39, 'Cambodge', 'autorise'),
(40, 'Cameroun', 'autorise'),
(41, 'Canada', 'autorise'),
(42, 'Îles Caïmans', 'autorise'),
(43, 'République Centrafricaine', 'autorise'),
(44, 'Chili', 'autorise'),
(45, 'Chine', 'autorise'),
(46, 'Chypre', 'autorise'),
(47, 'Îles Cocos', 'autorise'),
(48, 'Colombie', 'autorise'),
(49, 'Comores', 'autorise'),
(50, 'Congo (RDC)', 'autorise'),
(51, 'Congo (République)', 'autorise'),
(52, 'Îles Cook', 'autorise'),
(53, 'Corée du Sud', 'autorise'),
(54, 'Corée du Nord', 'autorise'),
(55, 'Costa Rica', 'autorise'),
(56, 'Croatie', 'autorise'),
(57, 'Cuba', 'autorise'),
(58, 'Curaçao', 'autorise'),
(59, 'Côte d\'Ivoire', 'autorise'),
(60, 'Danemark', 'autorise'),
(61, 'Djibouti', 'autorise'),
(62, 'Dominique', 'autorise'),
(63, 'Égypte', 'autorise'),
(64, 'Équateur', 'autorise'),
(65, 'Érythrée', 'autorise'),
(66, 'Espagne', 'autorise'),
(67, 'Estonie', 'autorise'),
(68, 'Éthiopie', 'autorise'),
(69, 'Îles Falkland', 'autorise'),
(70, 'Fidji', 'autorise'),
(71, 'Finlande', 'autorise'),
(72, 'France', 'autorise'),
(73, 'Gabon', 'autorise'),
(74, 'Gambie', 'autorise'),
(75, 'Géorgie', 'autorise'),
(76, 'Ghana', 'autorise'),
(77, 'Gibraltar', 'autorise'),
(78, 'Grèce', 'autorise'),
(79, 'Grenade', 'autorise'),
(80, 'Groenland', 'autorise'),
(81, 'Guadeloupe', 'autorise'),
(82, 'Guam', 'autorise'),
(83, 'Guatemala', 'autorise'),
(84, 'Guernesey', 'autorise'),
(85, 'Guinée', 'autorise'),
(86, 'Guinée Équatoriale', 'autorise'),
(87, 'Guinée-Bissau', 'autorise'),
(88, 'Guyane', 'autorise'),
(89, 'Guyane Française', 'autorise'),
(90, 'Haïti', 'autorise'),
(91, 'Honduras', 'autorise'),
(92, 'Hong Kong', 'autorise'),
(93, 'Hongrie', 'autorise'),
(94, 'Île de Man', 'autorise'),
(95, 'Île Norfolk', 'autorise'),
(96, 'Îles Féroé', 'autorise'),
(97, 'Îles Mariannes du Nord', 'autorise'),
(98, 'Îles Marshall', 'autorise'),
(99, 'Îles Vierges Britanniques', 'autorise'),
(100, 'Îles Åland', 'autorise'),
(101, 'Inde', 'autorise'),
(102, 'Indonésie', 'autorise'),
(103, 'Iran', 'autorise'),
(104, 'Iraq', 'autorise'),
(105, 'Irlande', 'autorise'),
(106, 'Islande', 'autorise'),
(107, 'Israël', 'autorise'),
(108, 'Italie', 'autorise'),
(109, 'Jamaïque', 'autorise'),
(110, 'Japon', 'autorise'),
(111, 'Jersey', 'autorise'),
(112, 'Jordanie', 'autorise'),
(113, 'Kazakhstan', 'autorise'),
(114, 'Kenya', 'autorise'),
(115, 'Kirghizistan', 'autorise'),
(116, 'Kiribati', 'autorise'),
(117, 'Koweït', 'autorise'),
(118, 'Laos', 'autorise'),
(119, 'Le Salvador', 'autorise'),
(120, 'Lesotho', 'autorise'),
(121, 'Lettonie', 'autorise'),
(122, 'Liban', 'autorise'),
(123, 'Liberia', 'autorise'),
(124, 'Libye', 'autorise'),
(125, 'Liechtenstein', 'autorise'),
(126, 'Lituanie', 'autorise'),
(127, 'Luxembourg', 'autorise'),
(128, 'Macao', 'autorise'),
(129, 'Macédoine', 'autorise'),
(130, 'Malawi', 'autorise'),
(131, 'Malaisie', 'autorise'),
(132, 'Maldives', 'autorise'),
(133, 'Mali', 'autorise'),
(134, 'Malte', 'autorise'),
(135, 'Martinique', 'autorise'),
(136, 'Maurice', 'autorise'),
(137, 'Mauritanie', 'autorise'),
(138, 'Mayotte', 'autorise'),
(139, 'Mexique', 'autorise'),
(140, 'Micronésie', 'autorise'),
(141, 'Moldavie', 'autorise'),
(142, 'Monaco', 'autorise'),
(143, 'Mongolie', 'autorise'),
(144, 'Monténégro', 'autorise'),
(145, 'Montserrat', 'autorise'),
(146, 'Maroc', 'autorise'),
(147, 'Mozambique', 'autorise'),
(148, 'Myanmar', 'autorise'),
(149, 'Namibie', 'autorise'),
(150, 'Nauru', 'autorise'),
(151, 'Nicaragua', 'autorise'),
(152, 'Niger', 'autorise'),
(153, 'Nigeria', 'autorise'),
(154, 'Niue', 'autorise'),
(155, 'Norvège', 'autorise'),
(156, 'Nouvelle-Calédonie', 'autorise'),
(157, 'Nouvelle-Zélande', 'autorise'),
(158, 'Népal', 'autorise'),
(159, 'Oman', 'autorise'),
(160, 'Ouganda', 'autorise'),
(161, 'Ouzbékistan', 'autorise'),
(162, 'Pakistan', 'autorise'),
(163, 'Palaos', 'autorise'),
(164, 'Palestine', 'autorise'),
(165, 'Panama', 'autorise'),
(166, 'Papouasie-Nouvelle-Guinée', 'autorise'),
(167, 'Paraguay', 'autorise'),
(168, 'Pays-Bas', 'autorise'),
(169, 'Pays-Bas Caribéens', 'autorise'),
(170, 'Pérou', 'autorise'),
(171, 'Philippines', 'autorise'),
(172, 'Pitcairn', 'autorise'),
(173, 'Pologne', 'autorise'),
(174, 'Polynésie Française', 'autorise'),
(175, 'Porto Rico', 'autorise'),
(176, 'Portugal', 'autorise'),
(177, 'Qatar', 'autorise'),
(178, 'Roumanie', 'autorise'),
(179, 'Royaume-Uni', 'autorise'),
(180, 'Russie', 'autorise'),
(181, 'Rwanda', 'autorise'),
(182, 'République Dominicaine', 'autorise'),
(183, 'République Tchèque', 'autorise'),
(184, 'Réunion', 'autorise'),
(185, 'Sahara Occidental', 'autorise'),
(186, 'Saint-Barthélemy', 'autorise'),
(187, 'Saint-Christophe-et-Niévès', 'autorise'),
(188, 'Sainte-Lucie', 'autorise'),
(189, 'Saint-Marin', 'autorise'),
(190, 'Saint-Martin', 'autorise'),
(191, 'Saint-Pierre-et-Miquelon', 'autorise'),
(192, 'Saint-Siège', 'autorise'),
(193, 'Saint-Vincent-et-les-Grenadines', 'autorise'),
(194, 'Sainte-Hélène', 'autorise'),
(195, 'Samoa', 'autorise'),
(196, 'Samoa Américaines', 'autorise'),
(197, 'Sao Tomé-et-Principe', 'autorise'),
(198, 'Serbie', 'autorise'),
(199, 'Seychelles', 'autorise'),
(200, 'Sierra Leone', 'autorise'),
(201, 'Singapour', 'autorise'),
(202, 'Sint Maarten', 'autorise'),
(203, 'Slovaquie', 'autorise'),
(204, 'Slovénie', 'autorise'),
(205, 'Îles Salomon', 'autorise'),
(206, 'Somalie', 'autorise'),
(207, 'Soudan', 'autorise'),
(208, 'Soudan du Sud', 'autorise'),
(209, 'Sri Lanka', 'autorise'),
(210, 'Suisse', 'autorise'),
(211, 'Suriname', 'autorise'),
(212, 'Suède', 'autorise'),
(213, 'Svalbard et Jan Mayen', 'autorise'),
(214, 'Swaziland', 'autorise'),
(215, 'Syrie', 'autorise'),
(216, 'Sénégal', 'autorise'),
(217, 'Tadjikistan', 'autorise'),
(218, 'Taïwan', 'autorise'),
(219, 'Tanzanie', 'autorise'),
(220, 'Tchad', 'autorise'),
(221, 'Thaïlande', 'autorise'),
(222, 'Timor-Leste', 'autorise'),
(223, 'Togo', 'autorise'),
(224, 'Tokelau', 'autorise'),
(225, 'Tonga', 'autorise'),
(226, 'Trinité-et-Tobago', 'autorise'),
(227, 'Tunisie', 'autorise'),
(228, 'Turkménistan', 'autorise'),
(229, 'Turquie', 'autorise'),
(230, 'Tuvalu', 'autorise'),
(231, 'Ukraine', 'autorise'),
(232, 'Émirats Arabes Unis', 'autorise'),
(233, 'Uruguay', 'autorise'),
(234, 'Vanuatu', 'autorise'),
(235, 'Venezuela', 'autorise'),
(236, 'Viêt Nam', 'autorise'),
(237, 'Wallis-et-Futuna', 'autorise'),
(238, 'Yémen', 'autorise'),
(239, 'Zambie', 'autorise'),
(240, 'Zimbabwe', 'autorise'),
(241, 'États-Unis', 'autorise'),
(242, 'Île Christmas', 'autorise'),
(243, 'Îles Turques-et-Caïques', 'autorise'),
(244, 'Îles Vierges des États-Unis', 'autorise');

-- --------------------------------------------------------

--
-- Structure de la table `taux_de_change`
--

CREATE TABLE `taux_de_change` (
  `id_taux` int(11) NOT NULL,
  `code_taux` int(11) NOT NULL,
  `devise_source` int(11) NOT NULL,
  `devise_destination` int(11) NOT NULL,
  `montant_min` double UNSIGNED NOT NULL,
  `montant_max` int(10) UNSIGNED NOT NULL,
  `taux_application` float NOT NULL,
  `date_debut_validite` timestamp NULL DEFAULT NULL,
  `date_fin_validite` timestamp NULL DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `taux_de_change`
--

INSERT INTO `taux_de_change` (`id_taux`, `code_taux`, `devise_source`, `devise_destination`, `montant_min`, `montant_max`, `taux_application`, `date_debut_validite`, `date_fin_validite`, `active`) VALUES
(1, 1001, 6, 1, 0, 10000, 4907, NULL, NULL, 1),
(2, 36067, 1, 2, 1, 15, 4200, NULL, NULL, 1),
(3, 75684, 1, 6, 0, 9999999, 0.0002, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `transferts`
--

CREATE TABLE `transferts` (
  `id_transfert` int(11) NOT NULL,
  `code_transfert` int(11) NOT NULL,
  `id_compte_source` int(11) NOT NULL,
  `id_compte_destination` int(11) NOT NULL,
  `montant_envoye` double UNSIGNED NOT NULL,
  `montant_recu` double UNSIGNED NOT NULL,
  `frais` double UNSIGNED NOT NULL,
  `taux_applique` int(11) NOT NULL,
  `devise_source` int(11) NOT NULL,
  `devise_destination` int(11) NOT NULL,
  `date_transfert` timestamp NOT NULL DEFAULT current_timestamp(),
  `raison` text NOT NULL,
  `statut_transfert` set('en attente','confirmé','en cours','complété','échoué','annulé') NOT NULL DEFAULT 'en attente',
  `date_confirmation` timestamp NULL DEFAULT NULL,
  `reference_externe` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `transferts`
--

INSERT INTO `transferts` (`id_transfert`, `code_transfert`, `id_compte_source`, `id_compte_destination`, `montant_envoye`, `montant_recu`, `frais`, `taux_applique`, `devise_source`, `devise_destination`, `date_transfert`, `raison`, `statut_transfert`, `date_confirmation`, `reference_externe`) VALUES
(1, 334416839, 2, 3, 10, 50000, 5, 1, 1, 1, '2026-07-19 06:47:28', 'mah', 'confirmé', '2026-07-19 06:47:28', 'EXT-334416839'),
(2, 130656387, 2, 3, 1, 5000, 5, 1, 1, 1, '2026-07-20 13:03:47', 'mah', 'confirmé', '2026-07-20 13:03:47', 'EXT-130656387'),
(3, 677866048, 2, 3, 10, 50000, 5, 1, 1, 1, '2026-07-20 13:04:34', 'Test', 'confirmé', '2026-07-20 13:04:34', 'EXT-677866048'),
(4, 558903514, 2, 3, 10, 50000, 5, 1, 1, 1, '2026-07-20 13:29:03', 'Mahiratra', 'confirmé', '2026-07-20 13:29:03', 'EXT-558903514'),
(5, 844286966, 2, 3, 10, 50000, 5, 1, 1, 1, '2026-07-20 13:30:11', 'Erreur?', 'confirmé', '2026-07-20 13:30:11', 'EXT-844286966'),
(6, 672132567, 2, 3, 1, 5000, 5, 1, 1, 1, '2026-07-20 15:47:18', 'Frais', 'confirmé', '2026-07-20 15:47:18', 'EXT-672132567'),
(7, 489715129, 3, 2, 1000, 5000000, 5, 1, 1, 1, '2026-07-20 16:21:41', 'Frais', 'confirmé', '2026-07-20 16:21:41', 'EXT-489715129'),
(8, 325189941, 4, 3, 10, 50000, 5, 1, 6, 1, '2026-07-21 03:00:46', 'Frais', 'confirmé', '2026-07-21 03:00:46', 'EXT-325189941'),
(9, 928628559, 2, 3, 10, 50000, 5, 1, 1, 1, '2026-07-21 04:08:53', 'Frais', 'confirmé', '2026-07-21 04:08:53', 'EXT-928628559');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id_admin`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Index pour la table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`id_client`),
  ADD UNIQUE KEY `unique_mail` (`mail`),
  ADD UNIQUE KEY `unique_phone_number` (`numero_telephone`),
  ADD KEY `fk_clients_pays` (`pays`),
  ADD KEY `fk_clients_devise` (`devise_preferee`);

--
-- Index pour la table `comptes`
--
ALTER TABLE `comptes`
  ADD PRIMARY KEY (`id_compte`),
  ADD KEY `fk_comptes_client` (`id_client`),
  ADD KEY `fk_comptes_devise` (`devise`);

--
-- Index pour la table `devises`
--
ALTER TABLE `devises`
  ADD PRIMARY KEY (`id_devise`);

--
-- Index pour la table `frais_envoi`
--
ALTER TABLE `frais_envoi`
  ADD PRIMARY KEY (`id_frais`),
  ADD UNIQUE KEY `unique_frais` (`code_frais`),
  ADD KEY `fk_frais_devise` (`devise_frais`);

--
-- Index pour la table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id_notification`),
  ADD KEY `fk_notifications_transfert` (`id_transfert`),
  ADD KEY `fk_notifications_client` (`destinataire`);

--
-- Index pour la table `pays`
--
ALTER TABLE `pays`
  ADD PRIMARY KEY (`id_pays`);

--
-- Index pour la table `taux_de_change`
--
ALTER TABLE `taux_de_change`
  ADD PRIMARY KEY (`id_taux`),
  ADD UNIQUE KEY `unique_taux` (`code_taux`),
  ADD KEY `fk_taux_devise_source` (`devise_source`),
  ADD KEY `fk_taux_devise_dest` (`devise_destination`);

--
-- Index pour la table `transferts`
--
ALTER TABLE `transferts`
  ADD PRIMARY KEY (`id_transfert`),
  ADD UNIQUE KEY `unique_transfert` (`code_transfert`),
  ADD KEY `fk_transferts_compte_source` (`id_compte_source`),
  ADD KEY `fk_transferts_compte_dest` (`id_compte_destination`),
  ADD KEY `fk_transferts_devise_source` (`devise_source`),
  ADD KEY `fk_transferts_devise_dest` (`devise_destination`),
  ADD KEY `fk_transferts_taux` (`taux_applique`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `admins`
--
ALTER TABLE `admins`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `clients`
--
ALTER TABLE `clients`
  MODIFY `id_client` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `comptes`
--
ALTER TABLE `comptes`
  MODIFY `id_compte` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `devises`
--
ALTER TABLE `devises`
  MODIFY `id_devise` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=167;

--
-- AUTO_INCREMENT pour la table `frais_envoi`
--
ALTER TABLE `frais_envoi`
  MODIFY `id_frais` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id_notification` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `pays`
--
ALTER TABLE `pays`
  MODIFY `id_pays` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=245;

--
-- AUTO_INCREMENT pour la table `taux_de_change`
--
ALTER TABLE `taux_de_change`
  MODIFY `id_taux` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `transferts`
--
ALTER TABLE `transferts`
  MODIFY `id_transfert` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

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
  ADD CONSTRAINT `fk_comptes_client` FOREIGN KEY (`id_client`) REFERENCES `clients` (`id_client`) ON DELETE CASCADE,
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
  ADD CONSTRAINT `fk_notifications_client` FOREIGN KEY (`destinataire`) REFERENCES `clients` (`id_client`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_notifications_transfert` FOREIGN KEY (`id_transfert`) REFERENCES `transferts` (`id_transfert`) ON DELETE CASCADE;

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
