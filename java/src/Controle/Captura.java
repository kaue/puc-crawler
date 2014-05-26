package Controle;

import Entidade.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.net.URI;
import java.util.regex.Matcher;
import java.io.IOException;


public class Captura {
    private Entidade.Pedido Pedido; 
    private List<Entidade.Recurso> ListaRecursos = new ArrayList<Entidade.Recurso>();
    private List<Entidade.Pagina> ListaPaginas = new ArrayList<Entidade.Pagina>();
    
    public Captura(Entidade.Pedido lPedido) {
        this.Pedido = lPedido;
    }
   
    public void Iniciar() throws IOException {
        //===========
        //Url Inicial
        URI mainUrl = null;
        try{mainUrl = new URI(Pedido.getDominio());}catch(Exception ex){
            return;
        }

        List<Entidade.Pagina> listaSubPaginas = new ArrayList<Entidade.Pagina>();
        List<Entidade.Pagina> listaSubPaginasAux = new ArrayList<Entidade.Pagina>();
        //=========================
        //Verificar todos os niveis
        for(int i=0; i<= Pedido.getNivel(); i++){
            listaSubPaginasAux = listaSubPaginas;
            listaSubPaginas = new ArrayList<Pagina>();
            //==============
            //Primeiro Nivel
            if(i == 0)
                listaSubPaginasAux.add(new Pagina(Pedido.getDominio()));
            //========================================
            //Obter os links/subPaginas das SubPaginas
            for (Pagina pagina : listaSubPaginasAux) {
                Tela.Console.mostrarMensagem("--Pagina Atual = " + pagina.getUrl());
                Solicitacao solicitacao = Comum.Funcao.socketRequest(pagina.getUrl());
                Tela.Console.mostrarMensagem("Tempo Carregamneto = " + solicitacao.getTempoCarregamento());
                //=========================================
                //Obter lista de recursos para pagina atual
                ObterRecursos(solicitacao,pagina);
                //=========================
                //Obter 'href' da tag '<a>'
                for (String strLink : solicitacao.getHtml().split(Pattern.quote("<a")))
                {
                    try{
                        strLink = strLink.split(Pattern.quote("</a>"))[0];
                        strLink = strLink.split(Pattern.quote("href=\""))[1];
                        strLink = strLink.split(Pattern.quote("\""))[0];
                        strLink = strLink.trim();
                        //===========
                        //Validar Url
                        if(!Url.Validar(strLink))
                            continue;
                        //==============
                        //Estruturar Url
                        strLink = Url.Estruturar(strLink, mainUrl);
                        //==================================
                        //Verificar se está no mesmo dominio
                        URI curUrl = new URI(strLink);
                        if(curUrl.getHost().equals(mainUrl.getHost())){
                            Pagina pag = new Pagina(strLink, solicitacao.getTempoCarregamento());
                            if(!listaSubPaginas.contains(pag)){
                                //========================
                                //Adicionar pagina a lista
                                listaSubPaginas.add(pag);
                                //Debug
                                //Tela.Console.mostrarMensagem("Link Adicionado = " + strLink);
                            }
                        }
                    }catch (Exception ex){
                        Tela.Console.mostrarMensagem("Erro <a:" + strLink);
                        ex.printStackTrace();
                    }
                };
            }
            Tela.Console.mostrarMensagem("--Numero de SubPaginas = " + listaSubPaginas.size());
            //Adicionar a lista principal
            for (Pagina pagina : listaSubPaginas) {
                if(!ListaPaginas.contains(pagina)){
                    ListaPaginas.add(pagina);
                }
            };
        }
        //======================
        //Finalizou pedido atual
        Tela.Console.mostrarMensagem("--Finalizou Url = " + Pedido.getDominio());
        //==================================
        //Gerar/Salvar arquivo xml Resultado
        Tela.Console.mostrarMensagem("Salvando arquivo resultado...");
        Dados.Resultado dadosResultado = new Dados.Resultado(ListaRecursos, ListaPaginas, Pedido);
        dadosResultado.Salvar();
        //=========================
        //Apagar arquivo xml Pedido
        Tela.Console.mostrarMensagem("Deletando arquivo pedido...");
        //Dados.Pedido.Apagar(Pedido.getId());
    }
    
    
    private Recurso ObterInformacao(Recurso lRecurso){
        Recurso recursoRetorno = lRecurso;
        Tela.Console.mostrarMensagem("Fazendo request...");
        Solicitacao solicitacao = Comum.Funcao.socketRequest(lRecurso.getUrl());
        
        //============================
        //Atualizar tempo carregamento
        recursoRetorno.setTempoCarregamento(solicitacao.getTempoCarregamento());
        
        int tamanhoArquivo = 0;
        int qtdCaracteres = 0;                                   //(?:\/\*(?:[\s\S]*?)\*\/)|(?:([\s;])+\/\/(?:.*)$)
        //====================================
        //Remover todos comentarios do arquivo
        Tela.Console.mostrarMensagem("Removendo comentarios...");
        String semComentarios = solicitacao.getHtml().replaceAll("/(?:\\/\\*(?:[\\s\\S]*?)\\*\\/)|(?:([\\s;])+\\/\\/(?:.*)$)", ""); //.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)","");        
        //=================================================
        //Contar tamanho do arquivo e quantidade caracteres
        tamanhoArquivo = solicitacao.getHtml().getBytes().length - semComentarios.getBytes().length;
        qtdCaracteres = solicitacao.getHtml().length() - semComentarios.length();
        recursoRetorno.setDiferencaTamanho(tamanhoArquivo);
        recursoRetorno.setQuantidadeCaracteres(qtdCaracteres);
        //Debug        
        Tela.Console.mostrarMensagem("qtdCaracteres = " + qtdCaracteres + " Num kbs = " + tamanhoArquivo/1024);
        return recursoRetorno;
    }
    
    
    private void ObterRecursos(Solicitacao lSolicitacao, Pagina lPagina){
        Solicitacao solicitacao = lSolicitacao;
        URI paginaUrl = null;
        try{paginaUrl = new URI(lPagina.getUrl());}catch(Exception ex){
            return;
        }
        //==================================
        //Obter <script src="URL"> </script>
        for (String strScript : solicitacao.getHtml().split(Pattern.quote("<script")))
        {
            try{
                strScript = strScript.split(Pattern.quote("</script>"))[0];
                if(strScript.contains("src=")){
                    strScript = strScript.split(Pattern.quote("src=\""))[1];
                    strScript = strScript.split(Pattern.quote("\""))[0];
                    strScript = Url.Estruturar(strScript, paginaUrl);
                    Recurso recurso = new Recurso(strScript, "Script", lPagina);
                    if(!ListaRecursos.contains(recurso)){
                        //==========================================
                        //Adicionar a lista de recursos e obter info
                        ObterInformacao(recurso);
                        ListaRecursos.add(recurso);
                        Tela.Console.mostrarMensagem("Script= " + strScript);
                    }
                }else{
                    //Script Direto (sem url/src)
                }
            }catch (Exception ex){
                Tela.Console.mostrarMensagem("Erro <script:" + strScript);
            }
        };
        //========================================================================
        //Obter 'href' da tag '<link rel="stylesheet" type="text/css" href="URL">'
        for (String strLink : solicitacao.getHtml().split(Pattern.quote("<link")))
        {
            try{
                strLink = strLink.split(Pattern.quote(">"))[0];
                if(strLink.contains("href=")){
                    strLink = strLink.split(Pattern.quote("href=\""))[1];
                    strLink = strLink.split(Pattern.quote("\""))[0];
                    strLink = Url.Estruturar(strLink, paginaUrl);
                    //===========================
                    //Filtrar apenas arquivos CSS
                    if(!strLink.contains(".css"))
                        continue;
                    Recurso recurso = new Recurso(strLink, "Style", lPagina);
                    if(!ListaRecursos.contains(recurso)){
                        //==========================================
                        //Adicionar a lista de recursos e obter info
                        ObterInformacao(recurso);
                        ListaRecursos.add(recurso);
                        Tela.Console.mostrarMensagem("Style= " + strLink);
                    }
                }
            }catch (Exception ex){
                Tela.Console.mostrarMensagem("Erro <link:" + strLink);
            }
        };
        //==============================================
        //Obter 'src' da tag '<style src="URL"></style>'
        for (String strStyle : solicitacao.getHtml().split(Pattern.quote("<style")))
        {
            try{
                strStyle = strStyle.split(Pattern.quote("</style>"))[0];
                if(strStyle.contains("src=")){
                    strStyle = strStyle.split(Pattern.quote("src=\""))[1];
                    strStyle = strStyle.split(Pattern.quote("\""))[0];
                    strStyle = Url.Estruturar(strStyle, paginaUrl);
                    //===========================
                    //Filtrar apenas arquivos CSS
                    if(!strStyle.contains(".css"))
                        continue;
                    Recurso recurso = new Recurso(strStyle, "Style", lPagina);
                    if(!ListaRecursos.contains(recurso)){
                        //==========================================
                        //Adicionar a lista de recursos e obter info
                        ObterInformacao(recurso);
                        ListaRecursos.add(recurso);
                        Tela.Console.mostrarMensagem("Style= " +strStyle);
                    }
                }else{
                    //Style Direto (sem url/src)
                }
            }catch (Exception ex){
                Tela.Console.mostrarMensagem("Erro <style:" + strStyle);
            }
        };
        
    }
}
