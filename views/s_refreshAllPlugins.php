<?php
    include_once '../ressources/functions.php';
    $idUser = $_POST['idUser'];
    $plugins = getListePlugins($bdd,$idUser);
    if ($plugins){
        echo '
            <h3> Liste des plugins </h3>
            <table role="grid">
                <tr>
                    <th>Date de sortie</th> <th>Nom du plugin</th> <th>Description</th> <th> </th>
                </tr>';

                foreach ($plugins as $plugin) {
                    echo '<tr>
                            <td>'.$plugin['dateRelease'].'</td>
                            <td>'.$plugin['namePlugin'].'</td>
                            <td>'.$plugin['descriptionPlugin'].'</td>
                            <td> <a class="success button round" onclick="installPlugin('.$idUser.','.$plugin['idPlugin'].');">Installer</a> </td>
                         </tr>';
                }
            echo '</table>';
    }