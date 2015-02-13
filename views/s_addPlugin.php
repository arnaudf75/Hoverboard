<?php
    include_once '../ressources/functions.php';
    $idUser = $_POST['idUser'];
    $idPlugin = $_POST['idPlugin'];
    $bdd->exec('INSERT INTO telecharge VALUES('.$idPlugin.','.$idUser.',2)');
    $plugins = getMyPlugins($bdd, $idUser);
    if ($plugins) {
        echo '<h3>Vos plugins</h3>
        <table role="grid">
            <tr>
                <th>Date de sortie</th> <th>Nom du plugin</th> <th>Description</th>
            </tr>';
            foreach ($plugins as $plugin) {
                echo '
                    <tr>
                        <td>'.$plugin['dateRelease'].'</td>
                        <td>'.$plugin['namePlugin'].'</td>
                        <td>'.$plugin['descriptionPlugin'].'</td>
                        <td> <a class="button small alert round" onclick="removePlugin('.$idUser.','.$plugin['idPlugin'].');">Désinstaller</a> </td>
                    </tr>';
            }
        echo '</table>';
    }