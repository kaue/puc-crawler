package Entidade;

public class Solicitacao {
    private String Html;
    private long TempoCarregamento;
    
    public Solicitacao(String lHtml, long lTempoCarregamento){
        this.Html = lHtml;
        this.TempoCarregamento = lTempoCarregamento;
    }
    public long getTempoCarregamento() {
        return TempoCarregamento;
    }

    public void setTempoCarregamento(long tempoCarregamento) {
        TempoCarregamento = tempoCarregamento;
    }

    public String getHtml() {
        return this.Html;
    }

    public void setHtml(String html) {
        this.Html = html;
    }
}
