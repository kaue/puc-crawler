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
    private Pedido Pedido = new Pedido();
    
    public Resultado(List<Recurso> lListaRecursos, List<Pagina> lListaPaginas, Pedido lPedido){
        this.ListaRecursos = lListaRecursos;
        this.ListaPaginas = lListaPaginas;
        this.Pedido = lPedido;
    }
    
    public void Salvar(){
        //TODO
        String resultadoXml = "";
        for (Pagina pagina : ListaPaginas) {
             
        }
        
        for (Recurso pagina : ListaRecursos) {
            
        }
    }
    
}
