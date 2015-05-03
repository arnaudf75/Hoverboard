<div class="corps">
    <h2>Liste des logs</h2>
    <div id="myLogs">
        <?php
            if (isset($_SESSION['idUser'])) {
                $idUser = $_SESSION['idUser'];
                $dashboards = getMyDashboards($bdd,$idUser);
                if ($dasboards) {
					foreach($dashboards as $dashboard) {
						echo '<h3>'.$dashboard['titleDashboard'].'</h3>
						<table role="grid">
							<tr>
								<th>utilisateur</th> <th>Nom du widget/th> <th>modification</th>
							</tr>';
							$logs = getDashboardLogs($bdd,$dashboard['idDashboard']);
							foreach ($logs as $log) {
								$widget=getWidgetById($bdd,$log['idWidget']);
								$user=getUserById($bdd,$log['idUser']);
								echo '
									<tr>
										<td>'.$widget['nameWidget'].'</td>
										<td>'.$user['login'].'</td>
										<td>'.$log['contentWidget'].'</td>
									</tr>';
							}
						echo '</table>';
					}
                }
            }
            else {
                $idUser = -1;
            }
        ?>
    </div>

    <div id="allPlugins">
        <?php
            $plugins = getListePlugins($bdd,$idUser);
            if ($plugins){
                echo ' 
                    <h3> Liste des plugins </h3>
                    <table role="grid">
                        <tr>
                            <th>Date de sortie</th> <th>Nom du plugin</th> <th>Description</th> <th> </th>
                        </tr>';

                        foreach ($plugins as $plugin) {
                            echo '<tr>
                                    <td>'.$plugin['dateRelease'].'</td>
                                    <td>'.$plugin['namePlugin'].'</td>
                                    <td>'.$plugin['descriptionPlugin'].'</td>
                                    <td> <a href="index.php?control=plugins&action=addPlugin&idPlugin='.$plugin['idPlugin'].'" class="success button round">Installer</a> </td>
                                 </tr>';
                        }
                    echo '</table>';
            }
        ?>
    </div>
</div>