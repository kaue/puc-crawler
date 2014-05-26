<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Resultado</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" type="text/css" href="/include/style.css">
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans:300">
    <link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.min.css" type="text/css" rel="stylesheet">
    <link href="/include/css/bootstrap.min.css" rel="stylesheet">
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	<link href="/include/css/styles.css" rel="stylesheet">
</head>
<body>

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
        echo '<div class="header alt vert">';
        
        echo '<div class="container">';
        echo '<div class="loadingcogs"></div>';
    
        
        echo '<h1>Ops! </h1>';
        
        echo '<p class="lead">O robo está dormindo! Aguarde.</br>';
        echo 'Esse processo pode demorar alguns minutos!</br>';
        echo 'Prepare uma pipoca!';
        echo '<div>&nbsp;</div>';
        echo '</div>';
        echo '</div>';
        echo '<meta http-equiv="refresh" content="5">';

    }
    //Mostrar resultado
    function mostrarResultado($lCaminhoArquivo){
        $xmlResultado = simplexml_load_file($lCaminhoArquivo);
        $urlPrincipal = $xmlResultado->info->dominio;
        if($xmlResultado->resultado->info->qtdPaginas == 0){
            echo '<div class="header alt vert">';
            echo '<div class="container text-center">';
            echo '<h2 style="color:white;">' . $xmlResultado->info->dominio . '</h3></br>';
            echo '<i class="icon-remove-sign icon-5x" style="color: rgb(185, 0, 0);"></i>';
            echo '<p class="lead">';
            echo 'Ohh! Não foi possivel mapear o dominio!</br>';
            echo 'Triste isso... =(';
            echo '</p>';
            echo '</div>';
            echo '</div>';
             return;
            
        }
           
        //Info
        echo '<div class="header alt vert">';
        echo '<div class="container text-center">';
        echo '<h2 style="color:white;">' . $xmlResultado->info->dominio . '</h3></br>';
        echo '<i class="icon-ok-sign icon-5x" style="color: rgb(45, 172, 33);"></i>';
        echo '<p class="lead">';
        echo 'Oba! Foi possivel mapear o dominio com sucesso!</br>';
        echo 'Desculpe a demora!';
        echo '</p>';
        echo '</div>';
        echo '</div>';
        
        echo '<div class="featurette">
              <div class="container">';
        echo '<div class="row">
                  <div class="col-md-12 text-center">
                    <h1>O que o robo encontrou ?</h1>
                  </div>
                </div>';
        echo '<div class="row">';
        echo '<div class="col-md-2 col-md-offset-1 text-center">
	            <div class="featurette-item">
                  <i class="icon-sitemap"></i>
                  <h4>Nivel</h4>
                  <p>Foi verificado ate o nivel ' . $xmlResultado->info->nivel . '.</p>
                  </div>
              </div>';
        echo '<div class="col-md-2 text-center">
                <div class="featurette-item">
                  <i class="icon-file"></i>
                  <h4>Paginas</h4>
                  <p>Foram mepeadas ' . $xmlResultado->resultado->info->qtdPaginas . ' paginas.</p>
                </div>
              </div>';
        echo '<div class="col-md-2 text-center">
                <div class="featurette-item">
                  <i class="icon-globe"></i>
                  <h4>Recursos</h4>
                  <p>Foram mapeados ' . $xmlResultado->resultado->info->qtdRecursos . ' recursos.</p>
                </div>
              </div>';
        echo '<div class="col-md-2 text-center">
                <div class="featurette-item">
                  <i class="icon-font"></i>
                  <h4>Caracteres</h4>
                  <p>Foram removidos ' . $xmlResultado->resultado->info->totalCaracteres . ' caracteres</p>
                </div>
              </div>';
        echo '<div class="col-md-2 text-center">
                <div class="featurette-item">
                  <i class="icon-hdd"></i>
                  <h4>Tamanho</h4>
                  <p>Foi possivel diminuir ' . $xmlResultado->resultado->info->totalTamanho . 'kbs dos recursos.</p>
                </div>
              </div>';
        echo '</div>';
        echo '</div>';
        echo '</div>';
        //Tabelas
        echo '<div class="blurb bright">';
        echo '<div class="row">
              <div class="col-md-6 col-md-offset-3 text-center">
                <h1>Paginas / Recursos</h1>
                <p class="lead">Lista de paginas e recursos mapeados pelo robo.
                <br>
              </div>
              </div>';
        //echo '<div class="tbsresult">';
        //Paginas
        echo '<div class="row">
              <div class="col-sm-4 col-sm-offset-2">';
        echo '<table class="rwd-table table-resultado">';
        //Header - URL, Tempo
        echo '<tr><th>Url</th>       <th>Tempo</th>      </tr>';
        foreach ($xmlResultado->resultado->paginas->pagina as $pagina) {
           echo '<tr>';
           echo '<td data-th="Url">' . str_replace($urlPrincipal, "",$pagina->url) . '</td>';
           echo '<td data-th="Tempo">' . $pagina->tempo . 's</td>';
           echo '</tr>';
        }
        echo '</table>';
        echo '</div>';
        //Recursos
        echo '<div class="col-sm-4">';
        echo '<table class="rwd-table table-resultado">';
        //Header - URL, Tipo, Tempo, Caracteres, Tamanho
        echo '<tr><th>Url</th>       <th>Tipo</th>      <th>Tempo</th>         <th>Caracteres</th>          <th>Tamanho</th></tr>';
        foreach ($xmlResultado->resultado->recursos->recurso as $recurso) {
           echo '<tr>';
           echo '<td data-th="Url">' . str_replace($urlPrincipal, "",$recurso->url) . '</td>';
           echo '<td data-th="Tipo">' . $recurso->tipo . '</td>';
           echo '<td data-th="Tempo">' . $recurso->tempo . 's</td>';
           echo '<td data-th="Caracteres">' . $recurso->caracteres . '</td>';
           echo '<td data-th="Tamanho">' . $recurso->tamanho . 'kb</td>';
           echo '</tr>';
        }
        echo '</table>';
        echo '</div>';
        echo '</div>';
        echo '</div>';
    }
    ?> 

    

<div class="callout">
  <div class="container">
    <div class="row">
      <div class="col-md-5">
        <h1><i class="icon-share-alt icon-3x"></i></h1>
      </div>
      <div class="col-md-7">
        <h1 class="pull-right">Projeto PUC-SP</h1>
        <p class="lead pull-right">Desenvolvido por: Kauê Gimenes, Flávio Tada, Alan Pereira</p>
      </div>
    </div>
  </div>
</div>

	<!-- script references -->
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
		<script src="/include/js/bootstrap.min.js"></script>
		<script src="/include/js/scripts.js"></script>
        <script src="/include/script.js"></script>
</body>
</html>