<div class="corps">
    <div>
        <h3> Les plugins que vous avez créés : </h3>
    <?php
        $listePlugins = getPluginsByAuthor($bdd, $_SESSION['idUser']);  
            if ($listePlugins) {
                echo '
                    <table role="grid" class="large-12 columns">
                    <tr>
                        <th>Date de sortie</th> <th>Nom du plugin</th> <th>Description</th> <th>A été validé</th>
                    </tr>';
                    foreach ($listePlugins as $plugin) {
                        $plugin['isValid'] = ($plugin['isValid'] == 1) ? "Oui" : "Non";
                        echo '
                            <tr>
                                <td>'.$plugin['dateRelease'].'</td>
                                <td>'.$plugin['namePlugin'].'</td>
                                <td>'.$plugin['descriptionPlugin'].'</td>
                                <td>'.$plugin['isValid'].'</td>
                            </tr>';
                    }
                echo '</table>';
        }
        else {
            echo "Vous n'avez crée aucun plugin !";
        }
    ?>
    
    
    </div>
    
    <div>
    <h3> Ajouter un plugin </h3>
        <div class="large-8 columns">
            <form action = "index.php?control=upload&action=uploadPlugin" method="POST" enctype="multipart/form-data">
                
                <?php
                    if ($listePlugins) {
                        echo '<p> Choisissez un plugin déjà existant <select name = "nomPlugin"> <option value = "VOID"> </option>';
                        foreach ($listePlugins as $plugin) {
                            echo '<option value = '.$plugin['idPlugin'].'>'.$plugin['namePlugin'].'</option>';
                        }
                        echo '</select> </p>';
                    }
                    echo 'Ou créez un nouveau plugin <p> Nom du plugin <input type ="text" name="nouveauPlugin" /> </p>';
                ?>
                    
                <p> Numéro de la version <input type ="text" name="numVersion" > </textarea></p>
                <p> Changelog <textarea name ="changelog" rows="5"> </textarea> </p>
                <p> <input type="file" name="pluginFile"> </p>
                <p> <input class="button submit" type="submit" value ="Envoyer"> </p>
            </form>
        </div>
    </div>