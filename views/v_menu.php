<div class="left" style="background-color: #333333" id="menu">
    <ul class="off-canvas-list">
        <li><label>Hoverboard</label></li>
        <li class="active"><a href="index.php">Accueil</a></li>
        <li><a href="index.php?control=plugins">Catalogue des plugins</a></li>
        <li><a href="index.php?control=theme">Thèmes</a></li>
        <?php if (isset($_SESSION['idUser']) ) { echo ' <li><a href="index.php?control=upload">Proposer un plugin</a></li> ';} ?>
    </ul>
</div>