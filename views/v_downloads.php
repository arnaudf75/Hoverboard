<div class="corps">
    <?php
       $downloads = getDownloads($bdd);
       foreach ($downloads as $download) {
       echo '   
            <div class="large-6 columns">
                <table role="grid">
                    <tr>
                        <th> Version '.$download['numVersion'].'</th> <th> '.$download['dateUpdate'].'</th>
                    </tr>
                    <tr> <td> '.$download['changelog'].' </td> <td> <a href="'.$download['pathToVersion'].'" class="button round tiny"> Télécharger </a> </td> </tr>
                </table>
            </div>
        ';
       }
    ?>
</div>