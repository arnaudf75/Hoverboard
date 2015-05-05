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
            $nomPlugin = $_POST['nomPlugin'];
            mkdir('content/plugins/'.$nomPlugin.'/ ', 0777, true);
            $directory = 'content/plugins/'.$nomPlugin.'/'.$nomPlugin.'.jar';
            move_uploaded_file ($_FILES['pluginFile']['tmp_name'],$directory);
            Header('Location:index.php?control=upload');
            break;
        }
    }