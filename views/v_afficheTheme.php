<div class="corps">
    <div>
        <div class="large-8 columns">
            <table>
                <th>Nom du thème</th><th>Télécharger le thème</th>
                <?php
                    $listeThemes = array_diff(scandir("content/themes"), array('..', '.'));
                    foreach ($listeThemes as $theme) {
                        echo '<tr><td>'.$theme.'</td><td><a href="content/themes/'.$theme.'">Télécharger le thème</a></td></tr>';
                    }
                ?>
            </table>
        </div>
    </div>
    
    <?php if(isset($_SESSION['idUser'])) {
        echo '
        <h3> Ajouter un Thème </h3>
        <div class="large-8 columns">
            <form action = "index.php?control=theme&action=uploadTheme" method="POST" enctype="multipart/form-data">
                <p> Nom du thème <input type ="text" name="nomTheme" /> </p>
                <p> <input type="file" name="themeFile"> </p>
                <p> <input class="button submit" type="submit" value ="Envoyer"> </p>
            </form>
        </div>';
        }
    ?>
</div>