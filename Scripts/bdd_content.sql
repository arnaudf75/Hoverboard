-- --------------------------------------------------------
-- Hôte :                        mysql-hoverboard.alwaysdata.net
-- Version du serveur:           5.1.66-0+squeeze1 - (Debian)
-- Serveur OS:                   debian-linux-gnu
-- HeidiSQL Version:             9.1.0.4908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- Export de données de la table hoverboard_esgi.apparente : ~0 rows (environ)
/*!40000 ALTER TABLE `apparente` DISABLE KEYS */;
/*!40000 ALTER TABLE `apparente` ENABLE KEYS */;

-- Export de données de la table hoverboard_esgi.application : ~1 rows (environ)
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` (`idApplication`, `nameApplication`, `dateReleaseApp`) VALUES
	(1, 'Hoverboard', '2015-01-20');
/*!40000 ALTER TABLE `application` ENABLE KEYS */;

-- Export de données de la table hoverboard_esgi.dashboard : ~3 rows (environ)
/*!40000 ALTER TABLE `dashboard` DISABLE KEYS */;
INSERT INTO `dashboard` (`idDashboard`, `titleDashboard`, `isShared`) VALUES
	(1, 'Your first dashboard', 0),
	(2, 'Your first dashboard', 0),
	(3, 'Your first shared dashboard', 1);
/*!40000 ALTER TABLE `dashboard` ENABLE KEYS */;

-- Export de données de la table hoverboard_esgi.plugins : ~3 rows (environ)
/*!40000 ALTER TABLE `plugins` DISABLE KEYS */;
INSERT INTO `plugins` (`idPlugin`, `namePlugin`, `descriptionPlugin`, `dateRelease`, `isValid`) VALUES
	(1, 'Birthday Calendar', 'Displays a birthday window when it is the birthday of a dashboard\'s user.', '2015-01-10', 1),
	(2, 'Google Maps', 'Asks the user to enter an adress, then display the location on Google Maps.', '2014-12-16', 1),
	(3, 'Theme pack for Hoverboard', 'Add some optionnal themes for the Hoverboard application.', '2015-01-02', 0);
/*!40000 ALTER TABLE `plugins` ENABLE KEYS */;

-- Export de données de la table hoverboard_esgi.statut_plugin : ~3 rows (environ)
/*!40000 ALTER TABLE `statut_plugin` DISABLE KEYS */;
INSERT INTO `statut_plugin` (`idStatutPlugin`, `labelStatutPlugin`) VALUES
	(1, 'Deactivated'),
	(2, 'Pending'),
	(3, 'Activated');
/*!40000 ALTER TABLE `statut_plugin` ENABLE KEYS */;

-- Export de données de la table hoverboard_esgi.telecharge : ~4 rows (environ)
/*!40000 ALTER TABLE `telecharge` DISABLE KEYS */;
INSERT INTO `telecharge` (`idPlugin`, `idUser`, `idStatutPlugin`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(2, 2, 3),
	(3, 1, 3);
/*!40000 ALTER TABLE `telecharge` ENABLE KEYS */;

-- Export de données de la table hoverboard_esgi.type_widget : ~0 rows (environ)
/*!40000 ALTER TABLE `type_widget` DISABLE KEYS */;
INSERT INTO `type_widget` (`idTypeWidget`, `nomTypeWidget`, `descriptionTypeWidget`) VALUES
	(1, 'Tasklist', 'Affiche une liste de tâches que l\'utilisateur peut rayer au fur et à mesure qu\'il les complète.'),
	(2, 'Post-it', 'Affiche un post-it sur lequel l\'utilisateur peut écrire du texte ou/et insérer une image.'),
	(3, 'Poll', 'Crée un sondage auquel les utilisateurs du dashboard peuvent répondre.');
/*!40000 ALTER TABLE `type_widget` ENABLE KEYS */;

-- Export de données de la table hoverboard_esgi.users : ~0 rows (environ)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`idUser`, `firstName`, `lastName`, `email`, `login`, `password`, `isAdmin`, `isActive`) VALUES
	(1, 'Arnaud', 'Flaesch', 'arnaudflaesch@hotmail.com', 'aflaesch', 'root', 1, 1),
	(2, 'Gérard', 'Dupont', '', 'gdupont', 'root', 0, 1),
	(14, 'Arnaud', 'Flaesch', '', '2flaesch', 'root', 0, 0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Export de données de la table hoverboard_esgi.utilise : ~0 rows (environ)
/*!40000 ALTER TABLE `utilise` DISABLE KEYS */;
INSERT INTO `utilise` (`idUser`, `idDashboard`, `idDashboardAdmin`) VALUES
	(1, 1, 1),
	(1, 3, 1),
	(2, 2, 1),
	(2, 3, 0);
/*!40000 ALTER TABLE `utilise` ENABLE KEYS */;

-- Export de données de la table hoverboard_esgi.version : ~0 rows (environ)
/*!40000 ALTER TABLE `version` DISABLE KEYS */;
INSERT INTO `version` (`idVersion`, `dateUpdate`, `numVersion`, `changelog`, `pathToVersion`) VALUES
	(1, '2015-01-21', '1.0.5.48', '- Ajout d\'une fenêtre de connexion', 'ressources/url/application_version'),
	(2, '2015-01-17', '3.0.2.13', '- Ajout d\'un gâteau d\'anniversaire sur la fenêtre', 'http://www.siteduplugin.com/versions/30213.jar'),
	(3, '2015-01-13', '1.0.3.30', '- Connexion à la base de données', 'ressources/url/application_version'),
	(4, '2015-01-27', '1.0.2.34', '- Ajout du plugin Google Maps', 'http://g00gle.com/plugin/g00gle_maps/10234'),
	(5, '2015-01-25', '4.1.0.5', '- Ajout d\'un son "Happy Birthday" lorsque la fenêtre se lance', 'http://www.siteduplugin.com/versions/4105.jar');
/*!40000 ALTER TABLE `version` ENABLE KEYS */;

-- Export de données de la table hoverboard_esgi.version_application : ~0 rows (environ)
/*!40000 ALTER TABLE `version_application` DISABLE KEYS */;
INSERT INTO `version_application` (`idApplication`, `idVersion`) VALUES
	(1, 1),
	(1, 3);
/*!40000 ALTER TABLE `version_application` ENABLE KEYS */;

-- Export de données de la table hoverboard_esgi.version_plugin : ~0 rows (environ)
/*!40000 ALTER TABLE `version_plugin` DISABLE KEYS */;
INSERT INTO `version_plugin` (`idPlugin`, `idVersion`) VALUES
	(1, 2),
	(2, 4);
/*!40000 ALTER TABLE `version_plugin` ENABLE KEYS */;

-- Export de données de la table hoverboard_esgi.widgets : ~0 rows (environ)
/*!40000 ALTER TABLE `widgets` DISABLE KEYS */;
INSERT INTO `widgets` (`idWidget`, `contentWidget`, `positionX`, `positionY`, `longueur`, `largeur`, `idDashboard`, `idTypeWidget`) VALUES
	(1, 'Dash 1 : Vide', 150, 150, 0, 0, 1, 2),
	(2, 'Dash 2 : Vide', 10, 300, 80, 80, 1, 1),
	(40, 'Vide 1', 50, 50, 40, 90, 3, 2),
	(41, 'Vide 2', 10, 300, 80, 80, 3, 2),
	(42, '', 250, 200, 0, 0, 3, 1);
/*!40000 ALTER TABLE `widgets` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
