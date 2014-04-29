package Entidade;

public class Pagina {
    private String Url;
    private int TempoCarregamento;
    
    public int getTempoCarregamento() {
        return TempoCarregamento;
    }

    public void setTempoCarregamento(int tempoCarregamento) {
        TempoCarregamento = tempoCarregamento;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
