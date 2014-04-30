package Dados;
import java.io.*;

public class Pedido {
    private String dirPedido = "~/workspace/php/xml/p";
    
    public Pedido(){
       
    }
    
    public void ObterListaPedidos(){
        //Teste
        File folder = new File(dirPedido);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }
}
