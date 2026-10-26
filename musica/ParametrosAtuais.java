package musica;

public class ParametrosAtuais {

    private boolean AnteriorEhNota = false;
    private int volume = 64;
    private String ultimaNota = null;
    private int NumInstrumento;
    private char UltimaDuracao;
    private int BPM;
    public static final int OITAVA_MINIMA = 0;
    public static final int OITAVA_MAXIMA = 9;

    private final int oitavaBase;
    private int oitava;

    public ParametrosAtuais(int oitavaBase) {
        this.oitavaBase = oitavaBase;
        this.oitava = oitavaBase;
    }

    public void subirOitava() {
        oitava = (oitava + 1 > OITAVA_MAXIMA) ? oitavaBase : oitava + 1;
    }

    public void descerOitava() {
        oitava = (oitava - 1 < OITAVA_MINIMA) ? oitavaBase : oitava - 1;
    }

    public int getOitava() {
        return oitava;
    }

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

    public boolean isAnteriorEhNota() {
        return AnteriorEhNota;
    }

    public void setAnteriorEhNota(boolean valor) {
        this.AnteriorEhNota = valor;
    }

    public void setNumInstrumento(int NumInstrumento) {
        this.NumInstrumento = NumInstrumento;
    }

    public int getNumInstrumento() {
        return NumInstrumento;
    }

    public char getUltimaDuracao() {
        return UltimaDuracao;
    }

    public void setUltimaDuracao(char UltimaDuracao) {
        this.UltimaDuracao = UltimaDuracao;
    }

    public void setBPM(int BPM) {
        this.BPM = BPM;
    }

    public int getBPM() {
        return BPM;
    }
}
