<?php   
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    //Obter resultado POST
    $id = uniqid(rand(), true);
    $id = str_replace(".", "", $id);
    $dominio = $_POST["dominio"];
    $nivel = $_POST["nivel"];
    //Gerar Arquivo XML
    $arquivoXml = "";
    $arquivoXml .=           '<?xml version="1.0"?>';
    $arquivoXml .= PHP_EOL . '<data>';
    $arquivoXml .= PHP_EOL . '  <info>';
    $arquivoXml .= PHP_EOL . '    <id>' . $id . '</id>';
    $arquivoXml .= PHP_EOL . '    <dominio>' . $dominio . '</dominio>';
    $arquivoXml .= PHP_EOL . '    <nivel>' . $nivel . '</nivel>';
    $arquivoXml .= PHP_EOL . '  </info>';
    $arquivoXml .= PHP_EOL . '</data>';
    //Salvar Arquivo XML pasta 'xml/p'
    $diretorioArquivo = 'xml/p/' . $id . '.xml';
    file_put_contents($diretorioArquivo, $arquivoXml);
    //Redirecionar para pagina de resultado
    header("Location: resultado.php?res=" . $id);
    die();
}
?>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title> Iniciar Pesquisa </title>
    <!-- Main CSS -->
    <link rel="stylesheet" type="text/css" href="/include/style.css">
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans:300">
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
</head>
<body>
    <form class="main-form" method="POST">
        <h1 class="main-form-title">Verificar um domínio!</h1>
        <p>Dominio (http://www.dominio.com)</p>
        <input type="text" name="dominio" class="main-form-input" placeholder="Dominio" pattern="https?://.+" required autofocus>
        <p>Nível da pesquisa</p>
        <input type="number" name="nivel" class="main-form-input" value="3" min="0" max="10" required>
        <input type="submit" value="Acordar o Robo!" class="main-form-button">
      </form>
</body>
</html>




