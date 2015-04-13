<?php
    if (isset($_REQUEST['action'])) {
        $action = $_REQUEST['action'];
    }
    else {
        $action = NULL;
    }

    switch ($action) {
        default : {
            include_once 'views/v_upload.php';
            break;
        }
        case 'application' : {
            mkdir('content/application/'.$_FILES['application']['numVersion'].'/ ', 0777, true);
            $directory = 'contenu/application/'.$_FILES['application']['numVersion'].'/';
            move_uploaded_file ($_FILES['application']['tmp_name'],$directory);
            //ajouteEpisode($bdd, $_POST['serie'], $_POST['nom'], $_POST['saison'], $_POST['episode'], $directory, $_POST['audio'], $_POST['subs']);
            break;
        }
        
    }
