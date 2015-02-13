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
            addUser($bdd,$userLastName,$userFirstName,$emailUser,$userLogin,$userPassword);
            //Header("Location:index.php");
            break;
        }
    }