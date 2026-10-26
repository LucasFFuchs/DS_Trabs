import java.util.List;

public class Tradutor {

    private final List<analisaChar> regras;

    public Tradutor(List<analisaChar> regras) {
        this.regras = regras;
    }

    public String traduzir(String texto, int bpmInicial) {
        String[] linhas = texto.split("\\R");
        if (linhas.length > Vozes.MAXIMO_DE_VOZES) {
            throw new IllegalArgumentException("O texto tem " + linhas.length + " linhas, mas o máximo é " + Vozes.MAXIMO_DE_VOZES + " vozes.");
        }

        StringBuilder musica = new StringBuilder("T" + bpmInicial + " ");
        int bpmAtual = bpmInicial;
        for (int i = 0; i < linhas.length; i++) {
            ParametrosAtuais parametros = Vozes.criar(i);
            parametros.setBPM(bpmAtual);                 // a voz começa no BPM em que a anterior terminou
            musica.append("V").append(i).append(" ");
            musica.append(traduzirLinha(linhas[i], parametros));
            bpmAtual = parametros.getBPM();              // guarda para a próxima voz
        }
        return musica.toString().trim();
    }

    private String traduzirLinha(String linha, ParametrosAtuais parametros) {
        StringBuilder voz = new StringBuilder();
        voz.append("I").append(parametros.getNumInstrumento() - 1).append(" ");
        for (String pedaco : Selecionador.separar(linha)) {
            ContextoChar contexto = new ContextoChar(pedaco);
            for (analisaChar regra : regras) {
                if (regra.TesteChar(contexto.atual)) {
                    voz.append(regra.TraducaoTexto(contexto, parametros));
                    break;
                }
            }
        }
        return voz.toString();
    }
}