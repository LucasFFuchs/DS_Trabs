package musica;

public class Duracao {

    private static final String LETRAS = "whqistx";
    public static final char PADRAO = 'q';

    public static char letraDeDuracao(String pedaco) {
        int doisPontos = pedaco.indexOf(':');
        if (doisPontos < 0 || doisPontos + 1 >= pedaco.length()) {
            return PADRAO;
        }
        int posicao = pedaco.charAt(doisPontos + 1) - '1';
        if (posicao < 0 || posicao >= LETRAS.length()) {
            return PADRAO;
        }
        return LETRAS.charAt(posicao);
    }

    public static String tirarDuracao(String pedaco) {
        int doisPontos = pedaco.indexOf(':');
        return doisPontos < 0 ? pedaco : pedaco.substring(0, doisPontos);
    }
}
