package Principal;

import Controle.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Tela.Console.mostrarMensagem("--------------------INICIANDO ROBO--------------------"); 
        try{
            Captura captura = new Captura("http://www.pucsp.br/universidade/contato", 1);
            captura.Iniciar();    
        }catch (Exception ex){
            
        }
        
    }
}
