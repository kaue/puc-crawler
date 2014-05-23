package Principal;

import Controle.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        //TODO Implementar multi-thread.
        Tela.Console.mostrarMensagem("--------------------INICIANDO ROBO--------------------"); 
        List<Entidade.Pedido> listaPedidos = Dados.Pedido.ObterLista();
        try{
            //==================================================
            //Iniciar captura para todos os pedidos do diretorio
            for (Entidade.Pedido pedido : listaPedidos) {
                Captura captura = new Captura(pedido);
                captura.Iniciar();    
            }
        }catch (Exception ex){
            
        }    
    }
}
