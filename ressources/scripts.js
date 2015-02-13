function installPlugin(idUser,idPlugin){
    if (idUser == -1) {
        alert ("Vous devez être connecté pour installer un plugin !");
    }
    else {
        $.post('views/s_addPlugin.php',
            {idUser: idUser,
             idPlugin : idPlugin},
            function(data){
                $('#myPlugins').html(data);
        });
        $.post('views/s_refreshAllPlugins.php',
            {idUser: idUser},
            function(data){
                $('#allPlugins').html(data);
        });
        alert ("Le plugin sera installé la prochaine fois que vous démarerrez l'application.");
    }
}

function removePlugin(idUser,idPlugin){
    $.post('views/s_removePlugin.php',
        {idUser: idUser,
         idPlugin : idPlugin},
        function(data){
            $('#myPlugins').html(data);
    });
    $.post('views/s_refreshAllPlugins.php',
        {idUser: idUser},
        function(data){
            $('#allPlugins').html(data);
    });
    alert("Le plugin sera désinstallé la prochaine fois que vous démarerrez l'application.");
}