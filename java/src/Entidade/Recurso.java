package Entidade;

public class Recurso {
    private String Url;
    private String Tipo;
    private Pagina PaginaMae;
    private long TempoCarregamento;
    
    public Recurso(String lUrl, String lTipo, long lTempoCarregamento, Pagina lPaginaMae){
        this.Url = lUrl;
        this.Tipo = lTipo;
        this.TempoCarregamento = lTempoCarregamento;
        this.PaginaMae = lPaginaMae;
    }
    
    public long getTempoCarregamento() {
        return TempoCarregamento;
    }

    public void setTempoCarregamento(long tempoCarregamento) {
        TempoCarregamento = tempoCarregamento;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
    
    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
    
     public Pagina getPaginaMae() {
        return PaginaMae;
    }

    public void setPaginaMae(Pagina paginaMae) {
        PaginaMae = paginaMae;
    }
}
