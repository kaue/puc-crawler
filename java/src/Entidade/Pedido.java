package Entidade;

public class Pedido {
    private String Dominio;
    private int Nivel;
    
    public Pedido(String lDominio, int lNivel){
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
        return Dominio;
    }

    public void setDominio(String dominio) {
        Dominio = dominio;
    }
}
