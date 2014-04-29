package Principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        //Obter HMTL Google
        String html = Comum.Funcao.obterHTML("https://github.com/");
        List<String> listaScripts = new ArrayList<String>();
        for (String s : html.split(Pattern.quote("<script")))
        {
            try{
                
                s = s.split(Pattern.quote("</script>"))[0];
                s = s.split(Pattern.quote("src=\""))[1];
                s = s.split(Pattern.quote("\""))[0];
                listaScripts.add(s);
            }catch (Exception ex){

            }
        };
        for (String script : listaScripts) {
            Tela.Console.mostrarMensagem(script);
        };

        //Mostrar HTML Tela
        //Tela.Console.mostrarMensagem(html);
    }
}
