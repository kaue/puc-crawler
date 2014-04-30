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
            e.printStackTrace();
        }
        return result;
    }
    
    
   public static String socketRequest(String lUrl){
       String requestUrl = "";
       String requestDomain = "";
       String requestPath = "";
     
      
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
           wr.write("GET " + requestPath + " HTTP/1.1\r\n");
           //Post Data
           //wr.write("Content-Length: " + data.length() + "\r\n");
           //Host
           wr.write("Host: " + requestDomain + "\r\n");
           wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
           wr.write("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n");
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
       //String data = URLEncoder.encode("key1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");

       return result;
   }

}
