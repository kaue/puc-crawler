package Entidade;

public class Pedido {
    private String Id;
    private String Dominio;
    private int Nivel;
    
    public Pedido(String lId, String lDominio, int lNivel){
        this.Id = lId;
        this.Dominio = lDominio;
        this.Nivel = lNivel;
    }
    //Nivel
    public int getNivel() {
        return Nivel;
    }
    public void setNivel(int nivel) {
        Nivel = nivel;
    }
    //Dominio
    public String getDominio() {
        return Dominio;
    }
    public void setDominio(String dominio) {
        Dominio = dominio;
    }
    //Id
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
}
