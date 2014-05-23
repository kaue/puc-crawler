package Dados;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;
public class Pedido {
    private static String dirPedido = "/home/codio/workspace/php/xml/p/";
   
    public Pedido(){
       
    }
    
    public static List<Entidade.Pedido> ObterLista(){
        File folder = new File(dirPedido);
        File[] listOfFiles = folder.listFiles();
        List<Entidade.Pedido> ListaPedidos = new ArrayList<Entidade.Pedido>();
        //=====================================
        //Verificar todos arquivos no dirPedido
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                //===============================
                //Adicionar Pedido a ListaPedidos
                ListaPedidos.add(LerXml(listOfFiles[i].getPath()));
            }
        }
        return ListaPedidos;
    }
    
    private static Entidade.Pedido LerXml(String lDir){
        String arquivoXml = "";
        //===========================
        //Obter linhas do arquivo xml
        try {
            List<String> lines = Files.readAllLines(Paths.get(lDir),
                    Charset.defaultCharset());
            for (String line : lines) {
                arquivoXml += line;
            }
        } catch (IOException e) {
            System.out.println("pedidoId + pedidoDominio + pedidoNivel");
            return null;
        }
        //============================
        //Obter valores do arquivo xml 
        String pedidoId = arquivoXml.split(Pattern.quote("<id>"))[1]; 
        pedidoId = pedidoId.split(Pattern.quote("</id>"))[0];
        String pedidoDominio = arquivoXml.split(Pattern.quote("<dominio>"))[1];
        pedidoDominio = pedidoDominio.split(Pattern.quote("</dominio>"))[0];
        String pedidoNivel = arquivoXml.split(Pattern.quote("<nivel>"))[1];
        pedidoNivel = pedidoNivel.split(Pattern.quote("</nivel>"))[0];
        //=========================
        //Gerar e retornar entidade
        Entidade.Pedido pedido = new Entidade.Pedido(pedidoId, pedidoDominio, Integer.parseInt(pedidoNivel));
        return pedido;
    }
    
    
    public void Apagar(String id){
        //TODO
    }
}
