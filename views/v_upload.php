<div class="corps">
    <?php
        if ($_SESSION['isAdmin'] == 1) {
            echo '
                <h2> Ajouter une nouvelle version de l\'application </h2>
                <div class="large-6 columns">
                     <form action = "index.php?control=upload&action=application" method="POST" enctype="multipart/form-data">
                         <p> Numéro de la version <input type ="text" name="numVersion" > </textarea></p>
                         <p> Changelog <textarea name ="changelog" rows="5"> </textarea> </p>
                         <p> <input type="file" name="application"> </p>
                         <p> <input class="button submit" type="submit" value ="Envoyer"> </p>
                     </form>
                 </div>
            ';
        }
    ?>
</div>