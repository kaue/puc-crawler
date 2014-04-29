package Comum;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
//obterHTML
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//socketGet
import java.net.Socket;
import java.io.PrintWriter;
import java.net.InetAddress;
/**
 * Criado por g0.
 * Data: 15/04/2014.
 */
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
            e.printStackTrace();
        }
        return result;
    }
    
    public static String socketGet(String lUrl){
        String textoResposta = "";
        try {
            //Criar novo Socket utilizando lUrl na porta 80
            Socket socket = new Socket(InetAddress.getByName(lUrl), 80);
            //Criar Request GET
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("GET / HTTP/1.1");
            printWriter.println("Host: " + lUrl);
            printWriter.println("");
            printWriter.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //Retornar Resposta
            while((textoResposta = br.readLine()) != null);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textoResposta;
    }
    
   public static String socketRequest(String lUrl){
       //String data = URLEncoder.encode("key1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
       String result = "";
       try {
           Socket socket = new Socket(InetAddress.getByName(lUrl), 80);


           BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
           wr.write("GET HTTP/1.0\r\n");
           //wr.write("Content-Length: " + data.length() + "\r\n");
           wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
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


       return result;
   }

}
