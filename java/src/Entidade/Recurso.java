package Entidade;
import java.net.URI;
public class Recurso {
    private String Url;
    private String Tipo;
    private Pagina PaginaMae;
    private long TempoCarregamento;
    private int QuantidadeCaracteres;
    private int DiferencaTamanho;
    
    public Recurso(String lUrl, String lTipo, Pagina lPaginaMae){
        this.Url = lUrl;
        this.Tipo = lTipo;
        this.PaginaMae = lPaginaMae;
    }
    //TempoCarregamento
    public long getTempoCarregamento() {
        return TempoCarregamento;
    }
    public void setTempoCarregamento(long tempoCarregamento) {
        TempoCarregamento = tempoCarregamento;
    }
    //Url
    public String getUrl() {
        return Url;
    }
    public void setUrl(String url) {
        Url = url;
    }
    //QuantidadeCaracteres
    public int getQuantidadeCaracteres() {
        return QuantidadeCaracteres;
    }
    public void setQuantidadeCaracteres(int quantidadeCaracteres) {
        QuantidadeCaracteres = quantidadeCaracteres;
    }
    //DiferencaTamanho
    public int getDiferencaTamanho() {
        return DiferencaTamanho;
    }
    public void setDiferencaTamanho(int diferencaTamanho) {
        DiferencaTamanho = diferencaTamanho;
    }
    //Tipo
    public String getTipo() {
        return Tipo;
    }
    public void setTipo(String tipo) {
        Tipo = tipo;
    }
    //PaginaMae
    public Pagina getPaginaMae() {
        return PaginaMae;
    }
    public void setPaginaMae(Pagina paginaMae) {
        PaginaMae = paginaMae;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        //Objeto só pode ser Pagina
        Recurso recurso = (Recurso)obj;
        //Se tiver a mesma url é igual!
        URI objUri = null;
        URI thisUri = null;
        try{
            objUri = new URI(recurso.getUrl());
            thisUri = new URI(this.Url);
        }
        catch(Exception ex){            
            return false;
        }

        if(thisUri.equals(objUri))
            return true;
        return false;
    }
}
