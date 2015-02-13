<div class="corps">
    <h2> Créez votre compte Hoverboard </h2> 
    <form action="index.php?control=register&action=register" data-abide method="POST">
        <div class="row">
            <div class="large-4 columns">
                <legend> Nom </legend>
                <input type = "text" name = "lastNameUser" class="error" required="required" />
                <small class="error">You must fill this field to register !</small>
            </div>
            <div class="large-4 columns end">
                <legend> Prénom </legend>
                <input type = "text" name = "firstNameUser" class="error" required="required" />
                <small class="error">You must fill this field to register !</small>
            </div>
        </div>
        <div class="row">
            <div class="large-4 columns">
                <legend> Adresse email </legend>
                <input type = "text" name = "emailUser" id = "emailUser" class="error" required= "required" />
                <small class="error">You must fill this field to register !</small>
            </div>
            <div class="large-4 columns end">
                <legend> Confirmez votre adresse email </legend>
                <input type = "text" name = "confirmEmailUser" data-equalto= "emailUser" class="error" required= "required" />
                <small class="error">Emails don't match</small>
            </div>
        </div>
        <div class="row">
            <div class="large-4 columns">
                <legend> Login </legend>
                <input type = "text" name = "loginUser" class="error" required="required" />
                <small class="error">You must fill this field to register !</small>
            </div>
            <div class="large-4 columns end">
                <legend> Mot de passe </legend>
                <input type = "password" name = "passwordUser" class="error" required="required" />
                <small class="error">You must fill this field to register !</small>
            </div>
        </div>
        <div class="row">
            <input class="button round small success" type="submit" value="Créer mon compte" />
            <input class="button round small" type="reset" />
        </div>
    </form>
</div>