<?php
    if (isset($_REQUEST['action'])) {
        $action = $_REQUEST['action'];
    }
    else {
        $action = NULL;
    }

    switch ($action) {
        default : {
            include_once 'views/v_afficheTheme.php';
            break;
        }
        case 'uploadTheme' : {
            $nomTheme = $_POST['nomTheme'];
            mkdir('content/themes/', 0777, true);
            $directory = 'content/themes/'.$nomTheme.'.zip';
            move_uploaded_file ($_FILES['themeFile']['tmp_name'],$directory);
            Header('Location:index.php?control=theme');
            break;
        }
    }