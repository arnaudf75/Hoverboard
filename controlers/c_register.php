<?php
    if (isset($_REQUEST['action'])) {
        $action = $_REQUEST['action'];
    }
    else {
        $action = NULL;
    }
    
    switch ($action) {
        default : {
            include_once 'views/v_register.php';
            break;
        }
        case 'register' : {
            $userLastName = $_POST['lastNameUser'];
            $userFirstName = $_POST['firstNameUser'];
            $emailUser = $_POST['emailUser'];
            $userLogin = $_POST['loginUser'];
            $userPassword = $_POST['passwordUser'];
            $isValid = addUser($bdd,$userLastName,$userFirstName,$emailUser,$userLogin,$userPassword);
            if ($isValid) {
                echo '<script> alert("Votre compte a bien été créé !");</script>';
            }
            else {
                echo '<script> alert("Un utilisateur existe déjà avec ce login ou cet email !"); </script>';
            }
            include_once 'views/v_register.php';
            break;
        }
    }
?>