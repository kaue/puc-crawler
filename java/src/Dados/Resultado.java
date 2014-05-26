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
        resultadoXml += "\t" + "<info>\n";
        resultadoXml += "\t\t" + GerarLinhaXml("id", Pedido.getId());
        resultadoXml += "\t\t" + GerarLinhaXml("dominio", Pedido.getDominio());
        resultadoXml += "\t\t" + GerarLinhaXml("nivel", String.valueOf(Pedido.getNivel()));
        resultadoXml += "\t" + "</info>\n";
        //================================================
        //Preencher informações do Resultado '<resultado>'
        resultadoXml += "<resultado>\n";
        //==========================
        //Adicionar lista de Paginas
        resultadoXml += "<paginas>\n";  
        for (Pagina pagina : ListaPaginas) {
            resultadoXml += "\t" + "<pagina>\n";
            resultadoXml += "\t\t" + GerarLinhaXml("url", pagina.getUrl());
            resultadoXml += "\t\t" + GerarLinhaXml("tempo", String.valueOf(pagina.getTempoCarregamento()));
            resultadoXml += "\t" + "</pagina>\n";
        }
        resultadoXml += "</paginas>";
        //===========================
        //Adicionar lista de Recursos
        resultadoXml += "<recursos>\n";
        for (Recurso recurso : ListaRecursos) {
            resultadoXml += "\t" + "<recurso>\n";
            resultadoXml += "\t\t" + GerarLinhaXml("url", recurso.getUrl());
            resultadoXml += "\t\t" + GerarLinhaXml("tipo", recurso.getTipo());
            resultadoXml += "\t\t" + GerarLinhaXml("tempo", String.valueOf(recurso.getTempoCarregamento()));
            resultadoXml += "\t\t" + GerarLinhaXml("caracteres", String.valueOf(recurso.getQuantidadeCaracteres()));
            resultadoXml += "\t\t" + GerarLinhaXml("tamanho", String.valueOf(recurso.getDiferencaTamanho()));
            resultadoXml += "\t" + "</recurso>\n";
            totalCaracteres += recurso.getQuantidadeCaracteres();
            totalTamanho += recurso.getDiferencaTamanho();
        }
        resultadoXml += "</recursos>\n";
        //Adicionar informações resultado
        resultadoXml += "\t" + "<info>\n";
        resultadoXml += "\t\t" + GerarLinhaXml("qtdPaginas", String.valueOf(ListaPaginas.size()));
        resultadoXml += "\t\t" + GerarLinhaXml("qtdRecursos", String.valueOf(ListaRecursos.size()));
        resultadoXml += "\t\t" + GerarLinhaXml("totalCaracteres", String.valueOf(totalCaracteres));
        resultadoXml += "\t\t" + GerarLinhaXml("totalTamanho", String.valueOf(totalTamanho));
        resultadoXml += "\t" + "</info>\n";
        resultadoXml += "</resultado>\n";
        resultadoXml += "</data>\n";
            
        return resultadoXml;
    }
    
    private String GerarLinhaXml(String lNode, String lValor){
        return "<" + lNode + ">" + lValor + "</" + lNode + ">\n";
    }
}
