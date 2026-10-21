public class ParametrosAtuais {
    private boolean AnteriorEhNota = false;
    private int volume = 64;
    private int oitava = 4;
    private String ultimaNota = null;

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getUltimaNota() {
        return ultimaNota;
    }

    public void setUltimaNota(String ultimaNota) {
        this.ultimaNota = ultimaNota;
    }
    public boolean isAnteriorEhNota(){
        return AnteriorEhNota;
    }
    public void setAnteriorEhNota(boolean valor){
        this.AnteriorEhNota = valor;
    }
}
    

