<?php
    if (isset($_REQUEST['action'])) {
        $action = $_REQUEST['action'];
    }
    else {
        $action = NULL;
    }
    
    switch ($action) {
        default : {
            break;
        }
        case 'login' : {
            $userInfo = getUsers($bdd, $_POST['login'], $_POST['password']);
            connect($userInfo['lastName'],$userInfo['firstName'],$userInfo['idUser'],$userInfo['login'],$userInfo['isAdmin']);
            Header("Location:index.php");
            break;
        }
        case 'logout' : {
            session_destroy();
            Header("Location:index.php");
            break;
        }
    }