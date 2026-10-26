public class Duracao {

    // posição 0 = duração 1 (semibreve) ... posição 6 = duração 7 (semifusa)
    private static final String LETRAS = "whqistx";
    public static final char PADRAO = 'q';   // semínima

    /** "B#:2" vira 'h'. Sem duração escrita ("B#"), devolve PADRAO. */
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

    /** "B#:2" vira "B#". Sem duração escrita, devolve o pedaço como está. */
    public static String tirarDuracao(String pedaco) {
        int doisPontos = pedaco.indexOf(':');
        return doisPontos < 0 ? pedaco : pedaco.substring(0, doisPontos);
    }
}
