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
        case 'uploadPlugin' : {
            
            if (isset($_POST['nomPlugin'])) {
                $nomPlugin = $_POST['nomPlugin'];
                mkdir('content/plugins/'.$nomPlugin.'/ ', 0777, true);
                $directory = 'content/plugins/'.$nomPlugin.'/'.$nomPlugin.'.jar';
                move_uploaded_file ($_FILES['pluginFile']['tmp_name'],$directory);
            }
            else {
                if (isset($_POST['nouveauPlugin'])) {
                    $nomPlugin = $_POST['nouveauPlugin'];
                    mkdir('content/plugins/'.$nomPlugin.'/ ', 0777, true);
                    $directory = 'content/plugins/'.$nomPlugin.'/'.$nomPlugin.'.jar';
                    move_uploaded_file ($_FILES['pluginFile']['tmp_name'],$directory);
                }
                else {
                    $nomPlugin = 1240;
                }
            }
            Header('Location:index.php?control=upload&plugin='.$nomPlugin);
            //ajouteEpisode($bdd, $_POST['serie'], $_POST['nom'], $_POST['saison'], $_POST['episode'], $directory, $_POST['audio'], $_POST['subs']);
            break;
        }
        
    }
