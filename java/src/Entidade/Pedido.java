package Entidade;

public class Pedido {
    private String Dominio;
    private int Nivel;
    
    public Solicitacao(String lDominio, int lNivel){
        this.Dominio = lDominio;
        this.Nivel = lNivel;
    }
    public int getNivel() {
        return Nivel;
    }

    public void setNivel(int nivel) {
        Nivel = nivel;
    }

    public String getDominio() {
        return Html;
    }

    public void setDominio(String dominio) {
        Dominio = dominio;
    }
}
