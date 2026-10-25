public class FormatacaoJF {
    
    public static String nota(String nome, int oitava, char duracao, int volume) {
        return nome + oitava + duracao + "a" + volume + " ";
    }

    public static String pausa() {
        return "Rq ";
    }
}
