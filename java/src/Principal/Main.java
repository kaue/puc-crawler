package Principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Controle controle = new Controle("http://www.stackoverflow.com", 3);
        controle.IniciarCaptura();
    }
}
