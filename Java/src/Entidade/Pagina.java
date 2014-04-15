package Entidade;

/**
 * Criado por Kauê.
 * Data: 15/04/2014.
 */
public class Pagina {
    private String Url;
    private int TempoCarregamento;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getTempoCarregamento() {
        return TempoCarregamento;
    }

    public void setTempoCarregamento(int tempoCarregamento) {
        TempoCarregamento = tempoCarregamento;
    }
}
