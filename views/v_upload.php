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
                <p> Nom du plugin <input type ="text" name="nomPlugin" /> </p>
                <p> Description <textarea name ="descriptionPlugin" rows="5" > </textarea>  </p>
                <p> <input type="file" name="pluginFile"> </p>
                <p> <input class="button submit" type="submit" value ="Envoyer"> </p>
            </form>
        </div>
    </div>