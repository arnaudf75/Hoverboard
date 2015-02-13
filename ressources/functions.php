<?php
    $bdd = new PDO('mysql:host=localhost;dbname=hoverboard_jx90','hoverboard_jx90','esgi_hoverboard');
    
    function addUser($bdd,$userLastName,$userFirstName,$emailUser,$userLogin,$userPassword) {
        $requete = 'SELECT email, login FROM users WHERE email = "'.$emailUser.'" OR login = "'.$userLogin;
        $resultat = $bdd->query($requete);
        if ($resultat->fetchColumn()==0) {
            echo $resultat;
            $requete = "INSERT INTO users VALUES ('','$userLastName','$userFirstName','$emailUser','$userLogin','$userPassword',0,0)";
            $bdd->exec($requete);
            sendMailNewUser($emailUser);
        }
        else {
            echo '<script> alert("User existant"); </script>';
        }
    }
    
    function isConnected() {
        if (isset($_SESSION['idUser'])) {
            return ($_SESSION['idUser']);
        }
        else {
            return (false);
        }
    }
    
    function connect($lastName,$firstName,$idUser,$login,$isAdmin) {
        $_SESSION['lastName'] = $lastName;
        $_SESSION['firstName'] = $firstName;
        $_SESSION['idUser'] = $idUser;
        $_SESSION['login'] = $login;
        $_SESSION['isAdmin'] = $isAdmin;
    }
    
    function disconnect() {
        session_destroy();
    }
    
    function getDownloads($bdd) {
        $requete = 'SELECT V.*
                    FROM version_application VA
                    LEFT JOIN version V
                    ON VA.idVersion = V.idVersion
                    ORDER BY dateUpdate DESC';
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
            $isConnected = 'AND idPlugin NOT IN (SELECT idPlugin FROM telecharge T WHERE T.idUser = '.$idUser.')';
        }
        else { $isConnected = ''; }
        $requete = 'SELECT * FROM plugins WHERE isValid = 1 '.$isConnected;
        $resultat = $bdd->query($requete)->fetchAll();
        return($resultat);
    }
    
    function getMyPlugins($bdd,$idUser) {
        $requete = 'SELECT P.*
                    FROM telecharge T
                    LEFT JOIN plugins P
                    ON P.idPlugin = T.idPlugin
                    WHERE T.idUser = '.$idUser.'
                    ORDER BY P.dateRelease DESC';
        $resultat = $bdd->query($requete)->fetchAll();
        return ($resultat);
    }
    
    function getUsers($bdd,$login,$password) {
        $requete = 'SELECT * FROM users WHERE login = "'.$login.'" AND password ="'.$password.'" AND isActive = 1';
        $resultat = $bdd->query($requete)->fetch();
        return ($resultat);
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