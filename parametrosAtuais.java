public class ParametrosAtuais {
    private boolean AnteriorEhNota = false;
    private int volume = 64;
    private int oitava = 4;
    private String ultimaNota = null;
    private int NumInstrumento;
    private char UltimaDuracao;
    private int BPM;

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
    public int getOitava(){
        return oitava;
    }
    public void setOitava(int oitava){
        this.oitava = oitava;
    }
    public void setNumInstrumento(int NumInstrumento){
        this.NumInstrumento = NumInstrumento;
    }
    public int getNumInstrumento(){
        return NumInstrumento;
    }
    public char getUltimaDuracao(){
        return UltimaDuracao;
    }
    public void setUltimaDuracao(char UltimaDuracao){
        this.UltimaDuracao = UltimaDuracao;
    }
    public void setBPM(int BPM){
        this.BPM = BPM;
    }
    public int getBPM(){
        return BPM;
    }
}
    

