<div class="left" style="background-color: #333333" id="menu">
    <ul class="off-canvas-list">
        <li><label>Hoverboard</label></li>
        <li class="active"><a href="index.php">Accueil</a></li>
        <li><a href="index.php?control=downloads">Téléchargements</a></li>
        <li><a href="index.php?control=plugins">Catalogue des plugins</a></li>
        <?php if (isset($_SESSION['isAdmin']) && $_SESSION['isAdmin']==1) { echo ' <li><a href="index.php?control=upload">Upload</a></li> ';} ?>
        <li><a href="index.php?control=support">Support</a></li>
    </ul>
</div>