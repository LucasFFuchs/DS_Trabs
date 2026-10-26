public class Vozes {

    public static final int MAXIMO_DE_VOZES = 4;
    private static final int[] OITAVAS = {6, 5, 4, 3};
    private static final int[] VOLUMES = {100, 80, 60, 40};
    private static final int[] INSTRUMENTOS = {7, 20, 1, 71};   // cravo, órgão, piano, fagote (numeração de 1 a 128)

    /** Parâmetros iniciais da voz de índice 'indiceDaLinha' (0 = primeira linha). */
    public static ParametrosAtuais criar(int indiceVoz) {
        ParametrosAtuais parametros = new ParametrosAtuais(OITAVAS[indiceVoz]);
        parametros.setVolume(VOLUMES[indiceVoz]);
        parametros.setNumInstrumento(INSTRUMENTOS[indiceVoz]);
        return parametros;
    }
    
}
