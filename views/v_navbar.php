<div>
    <nav class="top-bar" data-topbar role="navigation">
        <section class="top-bar-section">
        <?php
            if (isset($_SESSION['login'])) {
                echo '<ul class="left"> <li style="color : white;margin-left : 1em; margin-top : 8px;"> Bonjour, '.$_SESSION['firstName'].' '.$_SESSION['lastName'].'</li> </ul>';
                echo '<ul class="right"> <li> <a href="index.php?control=login&action=logout" class="button alert round">Se déconnecter</a> </li> </ul>';
            }
            else {
                echo ' 
                    <ul class="right">
                        <form method="post" action="index.php?control=login&action=login">
                            <li> <a> Login </a> </li>
                            <li> <input type="text" name="login" required="required" /> </li>
                            <li> <a> Mot de passe </a></li>
                            <li> <input type="password" name="password" required="required" /> </li>
                            <li> <input class="button round" type="submit" value="Valider" /> </li>
                            <li> <a href="index.php?control=register" class="button success round"> Créer un compte </a> </li>
                        </form>
                    </ul>
                ';
            }
        ?>
        </section>
    </nav>
</div>