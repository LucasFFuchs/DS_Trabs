package musica;

public class Duracao {
    /*Define uma String que diferentes indices, representam diferentes duracoes musicais
      w - 0 - semibreve
      h - 1 - mínima
      q - 2 - semínima
      i - 3 - colcheia
      s - 4 - semicolcheia
      t - 5 - fusa
      x - 6 - semifusa*/
    private static final String LETRAS = "whqistx";
    public static final char PADRAO = 'q';

    public static char letraDeDuracao(String pedaco) {
        int doisPontos = pedaco.indexOf(':'); //Retorna a posicao do ':' na String
        //Se nao ha ':' ou se nao houver numero apos ':', retorna o tempo padrao
        if (doisPontos < 0 || doisPontos + 1 >= pedaco.length()) {
            return PADRAO;
        }
        /*Pega o numero apos ':' e to transforma e seu indice da String LETRAS
          Ex: 'C:3' posicao = '3' - '1' = 2 (posicao 2 da String LETRAS)*/
        int posicao = pedaco.charAt(doisPontos + 1) - '1';
        if (posicao < 0 || posicao >= LETRAS.length()) { //Verifica se a posicao eh valida
            return PADRAO;
        }
        return LETRAS.charAt(posicao); //Retorna o char que esta na posicao obtida
    }

    //Remove ':' e tudo que vem depois dele
    public static String tirarDuracao(String pedaco) {
        int doisPontos = pedaco.indexOf(':');
        return doisPontos < 0 ? pedaco : pedaco.substring(0, doisPontos);
    }
}
