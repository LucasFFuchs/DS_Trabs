package musica;

public class Vozes {

    //Define numero maximo de vozes e parametros individuais para cada voz baseado no seu indice (0 a 3)
    public static final int MAXIMO_DE_VOZES = 4;
    private static final int[] OITAVAS = {6, 5, 4, 3};
    private static final int[] VOLUMES = {100, 80, 60, 40};
    private static final int[] INSTRUMENTOS = {7, 20, 1, 71};

    /*Quando recebe apenas o indice da voz, chama a funcao criar com os parametros
    padroes de cada voz*/
    public static ParametrosAtuais criar(int indiceVoz) {
        return criar(indiceVoz, INSTRUMENTOS[indiceVoz], VOLUMES[indiceVoz], OITAVAS[indiceVoz]);
    }


    public static ParametrosAtuais criar(int indiceVoz, int instrumentoInicial, int volumeInicial, int oitavaInicial) {
        /*Se estivermos tratando da primeira voz, ela vai receber os parametros
          selecionados pelo usuario, da segundo voz em diante, é feito uma conta*/

        //Desce uma oitava a cada voz, se a oitava ficar negativa, fica definida como 0
        int oitava = (indiceVoz == 0) ? oitavaInicial : Math.max(0, oitavaInicial - indiceVoz);

        //Desce 20 no volume a cada voz, se o volume ficar negativo, fica definida como 0
        int volume = (indiceVoz == 0) ? volumeInicial : Math.max(0, volumeInicial - (indiceVoz * 20));

        //Se nao for a voz 1, ele pega os instrumentos setados como padrao para cada voz
        int instrumento = (indiceVoz == 0) ? instrumentoInicial : INSTRUMENTOS[indiceVoz];


        ParametrosAtuais parametros = new ParametrosAtuais(oitava);
        parametros.setVolume(volume);
        parametros.setNumInstrumento(instrumento);
        return parametros;
    }
}
