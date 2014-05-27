package Comum;

import Entidade.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;
//obterHTML
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//socketGet
import java.net.Socket;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;

public class Funcao {
    public static String obterHTML(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return result;
    }
    
    
    public static Solicitacao socketRequest(String lUrl){
        //==================================================================================
        //Socket esta apresentando problema em alguns dominios, utilizando HttpURLConnection
        long startTime2 = System.nanoTime();
        String result2 = obterHTML(lUrl);
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        
        duration2 = TimeUnit.SECONDS.convert(duration2, TimeUnit.NANOSECONDS);
        if(true)
            return new Solicitacao(result2, duration2);
        
        //=============
        //METODO ANTIGO
        String requestUrl = "";
        String requestDomain = "";
        String requestPath = "";
        //Obter tempo de inicio para calcular o tempo de carregamento
        long startTime = System.nanoTime();
        String result = "";
        try {

            URL myUrl = new URL(lUrl);
            //Obter dominio
            requestDomain = myUrl.getHost();
            //Obter caminho
            requestPath = myUrl.getPath();

            Socket socket = new Socket(InetAddress.getByName(requestDomain).getHostAddress(), 80);
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
            //Method
             
            if(requestPath == "")
                requestPath = "/";
            
            wr.write("GET " + requestPath + " HTTP/1.1\r\n");
            wr.write("Host: " + requestDomain + "\r\n");
            wr.write("Connection: keep-alive\r\n");
           // wr.write("Cache-Control: no-cache\r\n");
            //wr.write("Pragma: no-cache\r\n");
            //wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
            wr.write("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n");
           // wr.write("User-Agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36\r\n");
            wr.write("Accept-Encoding: gzip,deflate,sdch\r\n");
            wr.write("Accept-Language: pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4\r\n");
            wr.write("\r\n");
            //wr.write(data);
            wr.flush();
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            wr.close();
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        duration = TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS);
        return new Solicitacao(result, duration);
    }

}
