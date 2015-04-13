<?php
    if (isset($_REQUEST['action'])) {
        $action = $_REQUEST['action'];
    }
    else {
        $action = NULL;
    }
    
    switch ($action) {
        default : {
            include_once 'views/v_plugins.php';
            break;
        }
        case 'addPlugin' : {
            addPlugin($bdd,$_GET['idPlugin'],$_SESSION['idUser']);
            Header("Location:index.php?control=plugins");
            break;
        }
        case 'removePlugin' : {
            removePlugin($bdd,$_GET['idPlugin'],$_SESSION['idUser']);
            Header("Location:index.php?control=plugins");
            break;
        }
    }
?>