<div class="corps">
    <h2>Catalogue des plugins</h2>
    <div id="myPlugins">
        <?php
            if (isset($_SESSION['idUser'])) {
                $idUser = $_SESSION['idUser'];
                $plugins = getMyPlugins($bdd,$idUser);
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
            }
            else {
                $idUser = -1;
            }
        ?>
    </div>

    <div id="allPlugins">
        <?php
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
        ?>
    </div>
</div>