<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="/include/style.css">
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans:300">
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
</head>
<body>
<center>
   <?php
    //Obter valor get.
    $nomeArquivo = $_GET["res"] . ".xml";
    //Montar caminho para o arquivo.
    $caminhoArquivo = './xml/r/' . $nomeArquivo;
    //Verificar se o existe.
    if (!file_exists($caminhoArquivo)){
        //Mostrar tela load.
        mostrarLoading();
    } else{
        //Mostrar tela resultado.
        mostrarResultado($caminhoArquivo);
    }  
    //Mostrar tela de carregamento
    function mostrarLoading(){
        echo '<div class="loadingcogs"></div></br>';
        echo '<p class="load-legenda">Ops! O robo está dormindo! Aguarde.</br>';
        echo 'Esse processo pode demorar alguns minutos!</br>';
        echo 'Prepare uma pipoca!';
        echo '<meta http-equiv="refresh" content="5">';
        echo '</p>';
    }
    //Mostrar resultado
    function mostrarResultado($lCaminhoArquivo){
        echo 'resultado!';
        echo $lCaminhoArquivo;
    }
    ?> 
</center>
</body>
</html>