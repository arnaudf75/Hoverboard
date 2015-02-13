<?php
    session_start();

    include_once 'ressources/functions.php';
    
    include_once 'views/v_header.php';
    include_once 'views/v_navbar.php';
    include_once 'views/v_menu.php';
    
    if (isset($_REQUEST['control'])) {
        $control = $_REQUEST['control'];
    }
    else {
        $control = NULL;
    }

    switch ($control) {
        default : {
            include_once 'views/v_home.php';
            break;
        }
        case 'downloads' : {
            include_once 'views/v_downloads.php';
            break;
        }
        case 'login' : {
            include_once 'controlers/c_login.php';
            break;
        }
        case 'plugins' : {
            include_once 'views/v_plugins.php';
            break;
        }
        case 'register' : {
            include_once 'controlers/c_register.php';
            break;
        }
        case 'upload' : {
            include_once 'controlers/c_upload.php';
            break;
        }
        case 'support' : {
            include_once 'views/v_support.php';
            break;
        }
    }

    include_once 'views/v_footer.php';