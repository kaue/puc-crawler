package Controle;

import Entidade.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.net.URI;
import java.io.IOException;
public class Captura {
    private String CapturaUrl;
    private int CapturaNivel;
    private List<Recurso> ListaRecursos = new ArrayList<Recurso>();
    private List<Pagina> ListaPaginas = new ArrayList<Pagina>();
    
    public Captura(String lUrl, int lNivel) {
        this.CapturaUrl = lUrl;
        this.CapturaNivel = lNivel;
    }
   
    public void Iniciar() throws IOException {
        URI mainUrl = null;
        try{mainUrl = new URI(CapturaUrl);}catch(Exception ex){
            return;
        }

        List<Pagina> listaSubPaginas = new ArrayList<Pagina>();
        List<Pagina> listaSubPaginasAux = new ArrayList<Pagina>();
        
        //Verificar todos os niveis
        for(int i=0; i<= CapturaNivel; i++){
            //Obter os links/subPaginas das SubPaginas
            listaSubPaginasAux = listaSubPaginas;
            listaSubPaginas = new ArrayList<Pagina>();
            //Primeiro Nivel
            if(i == 0)
                listaSubPaginasAux.add(new Pagina(CapturaUrl));
            for (Pagina pagina : listaSubPaginasAux) {
                Tela.Console.mostrarMensagem("--Pagina Atual = " + pagina.getUrl());
                Solicitacao solicitacao = Comum.Funcao.socketRequest(pagina.getUrl());
                Tela.Console.mostrarMensagem("Tempo Carregamneto = " + solicitacao.getTempoCarregamento());
                //Obter lista de recursos para pagina atual
                ObterRecursos(solicitacao,pagina);
                //Obter 'href' da tag '<a>'
                for (String strLink : solicitacao.getHtml().split(Pattern.quote("<a")))
                {
                    try{
                        strLink = strLink.split(Pattern.quote("</a>"))[0];
                        strLink = strLink.split(Pattern.quote("href=\""))[1];
                        strLink = strLink.split(Pattern.quote("\""))[0];
                        strLink = strLink.trim();
                        //Validar link                   
                        if(strLink.startsWith("#")){
                            continue;
                        }
                        if(strLink.endsWith("favicon.ico")){
                            continue;
                        }
                        if(strLink.endsWith("/")){
                            strLink = strLink.substring(0,strLink.length()-1);
                        }
                        if(strLink.isEmpty()){
                            continue;
                        }
                        if(!strLink.startsWith("http") && !strLink.startsWith("https")){
                            Tela.Console.mostrarMensagem("Link antes = " + strLink);
                            if(!strLink.startsWith("/"))
                                strLink = "/" + strLink;
                            strLink = "http://" + mainUrl.getHost() + strLink;
                            Tela.Console.mostrarMensagem("Link depois = " + strLink);
                        }
                        
                        //Verificar se está no mesmo dominio
                        URI curUrl = new URI(strLink);
                        if(curUrl.getHost().equals(mainUrl.getHost())){
                            Pagina pag = new Pagina(strLink, solicitacao.getTempoCarregamento());
                            if(!listaSubPaginas.contains(pag)){
                                //Adicionar pagina a lista
                                listaSubPaginas.add(pag);
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
        //Terminou
        for (Pagina recurso : ListaRecursos) {
            //Obter Tempo Carregamento
            //Possiveis solucoes
        }
    }
    
    private void ObterRecursos(Solicitacao lSolicitacao, Pagina lPagina){
        Solicitacao solicitacao = lSolicitacao;
        //Obter <script src="URL"> </script>
        for (String strScript : solicitacao.getHtml().split(Pattern.quote("<script")))
        {
            try{
                strScript = strScript.split(Pattern.quote("</script>"))[0];
                if(strScript.contains("src=")){
                    strScript = strScript.split(Pattern.quote("src=\""))[1];
                    strScript = strScript.split(Pattern.quote("\""))[0];
                    Recurso recurso = new Recurso(strScript, "Script", lPagina);
                    if(!ListaRecursos.contains(recurso)){
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
        //Obter 'href' da tag '<link rel="stylesheet" type="text/css" href="URL">'
        for (String strLink : solicitacao.getHtml().split(Pattern.quote("<link")))
        {
            try{
                strLink = strLink.split(Pattern.quote(">"))[0];
                if(strLink.contains("href=")){
                    strLink = strLink.split(Pattern.quote("href=\""))[1];
                    strLink = strLink.split(Pattern.quote("\""))[0];
                    //Filtrar apenas arquivos CSS
                    if(!strLink.contains(".css"))
                        continue;
                    Recurso recurso = new Recurso(strLink, "Style", lPagina);
                    if(!ListaRecursos.contains(recurso)){
                        ListaRecursos.add(recurso);
                        Tela.Console.mostrarMensagem("Style= " + strLink);
                    }
                }
            }catch (Exception ex){
                Tela.Console.mostrarMensagem("Erro <link:" + strLink);
            }
        };
        //Obter 'src' da tag '<style src="URL"></style>'
        for (String strStyle : solicitacao.getHtml().split(Pattern.quote("<style")))
        {
            try{
                strStyle = strStyle.split(Pattern.quote("</style>"))[0];
                if(strStyle.contains("src=")){
                    strStyle = strStyle.split(Pattern.quote("src=\""))[1];
                    strStyle = strStyle.split(Pattern.quote("\""))[0];
                    //Filtrar apenas arquivos CSS
                    if(!strStyle.contains(".css"))
                        continue;
                    Recurso recurso = new Recurso(strStyle, "Style", lPagina);
                    if(!ListaRecursos.contains(recurso)){
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
