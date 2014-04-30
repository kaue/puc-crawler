package Entidade;
import java.net.URI;
public class Pagina {
    private String Url;
    private long TempoCarregamento;
    
    public Pagina(String lUrl){
        this.Url = lUrl;
    }
    public Pagina(String lUrl, long lTempoCarregamento){
        this.Url = lUrl;
        this.TempoCarregamento = lTempoCarregamento;
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

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        //Objeto só pode ser Pagina
        Pagina pagina = (Pagina)obj;
        //Se tiver a mesma url é igual!
        URI objUri = null;
        URI thisUri = null;
        try{
            objUri = new URI(pagina.getUrl());
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
