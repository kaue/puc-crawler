package Dados;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Entidade.*;

public class Resultado {
    private String dirResultado = "/home/codio/workspace/php/xml/r/";
    private List<Recurso> ListaRecursos = new ArrayList<Recurso>();
    private List<Pagina> ListaPaginas = new ArrayList<Pagina>();
    private Entidade.Pedido Pedido;
    
    public Resultado(List<Recurso> lListaRecursos, List<Pagina> lListaPaginas, Entidade.Pedido lPedido){
        this.ListaRecursos = lListaRecursos;
        this.ListaPaginas = lListaPaginas;
        this.Pedido = lPedido;
    }
    
    public void Salvar(){
        //=================
        //Gerar Arquivo Xml
        String arquivoXml = GerarXml();
        try{
            File file = new File(dirResultado + Pedido.getId() + ".xml");
            //==============================
            //Verificar se arquivo ja existe
            if (!file.exists()) {
                file.createNewFile();
            }
            //=======================
            //Escrever xml no arquivo
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(arquivoXml);
            bw.close();    
        } catch (IOException e){
            Tela.Console.mostrarMensagem("Erro: Ocorreu um problema ao salvar o arquivo xml.");
        }
        
    }
    
    private String GerarXml() {
        String resultadoXml = "";
        int totalTamanho = 0;
        int totalCaracteres = 0;
        resultadoXml += "<?xml version=\"1.0\"?>\n";
        resultadoXml += "<data>\n";
        //========================================
        //Preencher informações do Pedido '<info>'
        resultadoXml += "<info>\n";
        resultadoXml += GerarLinhaXml("id", Pedido.getId());
        resultadoXml += GerarLinhaXml("dominio", Pedido.getDominio());
        resultadoXml += GerarLinhaXml("nivel", String.valueOf(Pedido.getNivel()));
        resultadoXml += "</info>\n";
        //================================================
        //Preencher informações do Resultado '<resultado>'
        resultadoXml += "<resultado>\n";
        //==========================
        //Adicionar lista de Paginas
        resultadoXml += "<paginas>\n";  
        for (Pagina pagina : ListaPaginas) {
            resultadoXml += "<pagina>\n";
            resultadoXml += GerarLinhaXml("url", pagina.getUrl());
            resultadoXml += GerarLinhaXml("tempo", String.valueOf(pagina.getTempoCarregamento()));
            resultadoXml += "</pagina>\n";
        }
        resultadoXml += "</paginas>";
        //===========================
        //Adicionar lista de Recursos
        resultadoXml += "<recursos>\n";
        for (Recurso recurso : ListaRecursos) {
            resultadoXml += "<recurso>\n";
            resultadoXml += GerarLinhaXml("url", recurso.getUrl());
            resultadoXml += GerarLinhaXml("tipo", recurso.getTipo());
            resultadoXml += GerarLinhaXml("tempo", String.valueOf(recurso.getTempoCarregamento()));
            resultadoXml += GerarLinhaXml("caracteres", String.valueOf(recurso.getQuantidadeCaracteres()));
            resultadoXml += GerarLinhaXml("tamanho", String.valueOf(recurso.getDiferencaTamanho()));
            resultadoXml += "</recurso>\n";
            totalCaracteres += recurso.getQuantidadeCaracteres();
            totalTamanho += recurso.getDiferencaTamanho();
        }
        resultadoXml += "</recursos>\n";
        //Adicionar informações resultado
        resultadoXml += "<info>\n";
        resultadoXml += GerarLinhaXml("qtdPaginas", String.valueOf(ListaPaginas.size()));
        resultadoXml += GerarLinhaXml("qtdRecursos", String.valueOf(ListaRecursos.size()));
        resultadoXml += GerarLinhaXml("totalCaracteres", String.valueOf(totalCaracteres));
        resultadoXml += GerarLinhaXml("totalTamanho", String.valueOf(totalTamanho));
        resultadoXml += "</info>\n";
        resultadoXml += "</resultado>\n";
        resultadoXml += "</data>\n";
            
        return resultadoXml;
    }
    
    private String GerarLinhaXml(String lNode, String lValor){
        return "<" + lNode + ">" + lValor + "</" + lNode + ">\n";
    }
}
