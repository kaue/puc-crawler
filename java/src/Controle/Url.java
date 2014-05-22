package Controle;

import Entidade.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.net.URI;
import java.io.IOException;
public class Url {

    public static boolean Validar(String lUrl){
        //Não pode ser ancora.
        if(lUrl.startsWith("#")){
            return false;
        }
        //Não considerar 'mailto'
        if(lUrl.startsWith("mailto:"))
            return false;
        //Não considerar 'favicon'
        if(lUrl.endsWith("favicon.ico")){
            return false;
        }
        //Url não pode estar em branco.
        if(lUrl.isEmpty()){
            return false;
        }
        return true;
    }
   
    public static String Estruturar(String lUrl, URI lMainUrl){
        String urlRetorno = lUrl;
        //============================
        //Remover '/' no final do link
        if(urlRetorno.endsWith("/")){
            urlRetorno = urlRetorno.substring(0,urlRetorno.length()-1);
        }
        //==========================
        //Adicionar caminho completo
        if(!urlRetorno.startsWith("http") && !urlRetorno.startsWith("https")){
            if(!urlRetorno.startsWith("/"))
                urlRetorno = "/" + urlRetorno;
            urlRetorno = "http://" + lMainUrl.getHost() + urlRetorno;
        }
        return urlRetorno;
    }

}
