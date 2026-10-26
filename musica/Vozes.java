package musica;

public class Vozes {

    public static final int MAXIMO_DE_VOZES = 4;
    private static final int[] OITAVAS = {6, 5, 4, 3};
    private static final int[] VOLUMES = {100, 80, 60, 40};
    private static final int[] INSTRUMENTOS = {7, 20, 1, 71};

    public static ParametrosAtuais criar(int indiceVoz) {
        return criar(indiceVoz, INSTRUMENTOS[indiceVoz], VOLUMES[indiceVoz], OITAVAS[indiceVoz]);
    }

    public static ParametrosAtuais criar(int indiceVoz, int instrumentoInicial, int volumeInicial, int oitavaInicial) {
        int oitava = (indiceVoz == 0) ? oitavaInicial : Math.max(0, oitavaInicial - indiceVoz);
        int volume = (indiceVoz == 0) ? volumeInicial : Math.max(0, volumeInicial - (indiceVoz * 20));
        int instrumento = (indiceVoz == 0) ? instrumentoInicial : INSTRUMENTOS[indiceVoz];

        ParametrosAtuais parametros = new ParametrosAtuais(oitava);
        parametros.setVolume(volume);
        parametros.setNumInstrumento(instrumento);
        return parametros;
    }
}
