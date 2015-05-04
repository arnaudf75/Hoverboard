<?php
    $bdd = new PDO('mysql:host=localhost;dbname=hoverboard_esgi','103876','esgi_hoverboard');
    
    function addPlugin($bdd, $idPlugin, $idUser) {
        $bdd->exec('INSERT INTO installe VALUES('.$idPlugin.','.$idUser.')');
    }
    
    function addUser($bdd,$userLastName,$userFirstName,$emailUser,$userLogin,$userPassword) {
        $requete = 'SELECT email, login FROM users WHERE email = "'.$emailUser.'" OR login = "'.$userLogin.'"';
        $resultat = $bdd->query($requete);
        if ($resultat->rowCount()==0) {
            $requete = "INSERT INTO users VALUES ('','$userLastName','$userFirstName','$emailUser','$userLogin','$userPassword',0)";
            $bdd->exec($requete);
            return (1);
        }
        else {
            return (0);
        }
    }
    
    function connect($lastName,$firstName,$idUser,$login,$isAdmin) {
        $_SESSION['idUser'] = $idUser;
        $_SESSION['lastName'] = $lastName;
        $_SESSION['firstName'] = $firstName;
        $_SESSION['login'] = $login;
        $_SESSION['isAdmin'] = $isAdmin;
    }
    
    function disconnect() {
        session_destroy();
    }
    
    function getPluginsByAuthor($bdd, $idAuthor) {
        $requete = 'SELECT P.*
                    FROM plugins P
                    WHERE P.idEditeur = '.$idAuthor;
        $resultat = $bdd->query($requete)->fetchAll();
        return ($resultat);
    }
    
    function getVersionsByPlugins($bdd, $idPlugin) {
        $requete = 'SELECT V.*
                    FROM version V
                    WHERE V.idPlugin = '.$idPlugin;
        $resultat = $bdd->query($requete)->fetchAll();
        return ($resultat);
    }
    
    function getMyDashboards($bdd,$idUser) {
        $requete = 'SELECT D.*
                    FROM utilise U
                    LEFT JOIN dashboard D
                    ON D.idDashboard = U.idDashboard
                    WHERE U.idUser = '.$idUser;
        $resultat = $bdd->query($requete)->fetchAll();
        return ($resultat);
    }
	
    function getDashboardLogs($bdd,$idDashboard) {
        $requete = 'SELECT L.*
                    FROM logs L
                    WHERE L.idDashboard = '.$idDashboard.'
					ORDER BY L.modifDate DESC';
        $resultat = $bdd->query($requete)->fetchAll();
        return ($resultat);
    }
	
    function getWidgetById($bdd,$idWidget) {
        $requete = 'SELECT W.*
                    FROM widget W
                    WHERE L.idWidget = '.$idWidget;
        $resultat = $bdd->query($requete)->fetchAll();
        return ($resultat);
    }
        
    function getUserById($bdd,$idUser) {
        $requete = 'SELECT U.*
                    FROM users U
                    WHERE U.iduser = '.$idUser;
        $resultat = $bdd->query($requete)->fetchAll();
        return ($resultat);
    }
    
    function getNews($bdd) {
        $requete = ("SELECT * FROM news ORDER BY dateNews DESC");
        $resultat = $bdd->query($requete)->fetchAll();
        return ($resultat);
    }
    
    function getListePlugins($bdd,$idUser) {
        if ($idUser) {
            $isConnected = 'AND idPlugin NOT IN (SELECT idPlugin FROM installe I WHERE I.idUser = '.$idUser.')';
        }
        else { $isConnected = ''; }
        $requete = 'SELECT * FROM plugins WHERE isValid = 1 '.$isConnected;
        $resultat = $bdd->query($requete)->fetchAll();
        return($resultat);
    }
    
    function getMyPlugins($bdd,$idUser) {
        $requete = 'SELECT P.*
                    FROM installe I, plugins P
                    WHERE I.idPlugin = P.idPlugin
                    AND I.idUser = '.$idUser.'
                    ORDER BY P.dateRelease DESC';
        $resultat = $bdd->query($requete)->fetchAll();
        return ($resultat);
    }
    
    function getUser($bdd,$login,$password) {
        $requete = 'SELECT * FROM users WHERE login = "'.$login.'" AND password ="'.$password.'"';
        $resultat = $bdd->query($requete)->fetch();
        return ($resultat);
    }
    
    function isConnected() {
        if (isset($_SESSION['idUser'])) {
            return ($_SESSION['idUser']);
        }
        else {
            return (false);
        }
    }
    
    function removePlugin($bdd, $idPlugin, $idUser) {
        $bdd->exec('DELETE FROM installe WHERE idPlugin = '.$idPlugin.' AND idUser = '.$idUser.' ');
    }
    
    function sendMailNewUser($emailUser) {
        $destinataire = $emailUser;
        $expediteur = 'arnaud.flaesch@livehost.fr';
        $objet = 'Welcome to Hoverboard !';
        $header  = 'MIME-Version: 1.0' . "\n";
        $header .= 'From: "The Hoverboard Team"<'.$expediteur.'>'."\n";
        $header .= 'Delivered-to: '.$destinataire."\n";
        $message = 'Welcome to Hoverboard. \n';
        $message .= 'Click on the following link to activate your account: \n';
        $message .= 'http://www.lien_activation.hoverboard.com';
        $message .= 'If you have any questions, feel free to contact us at hoverboard.esgi@gmail.com.';
        $message .= 'Cordially,The Hoverboard Team';
        if (mail($destinataire, $objet, $message, $header)) {
            // Le mail a été envoyé
        }
        else {
            // Le mail n'a pas été envoyé
        }
    }
?>