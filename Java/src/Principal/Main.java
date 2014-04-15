package Principal;

public class Main {

    public static void main(String[] args) {
        //Obter HMTL Google
        String html = Comum.Funcao.obterHTML("http://www.google.com");
        //Mostrar HTML Tela
        Tela.Console.mostrarMensagem(html);
    }
}
