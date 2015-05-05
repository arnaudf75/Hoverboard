<div class="corps">
    <div>
        <div class="large-8 columns">
            
            
        </div>
    </div>
    
    <h3> Ajouter un Thème </h3>
    <div class="large-8 columns">
        <form action = "index.php?control=theme&action=uploadTheme" method="POST" enctype="multipart/form-data">
            <p> Nom du thème <input type ="text" name="nomTheme" /> </p>
            <p> <input type="file" name="themeFile"> </p>
            <p> <input class="button submit" type="submit" value ="Envoyer"> </p>
        </form>
    </div>
</div>