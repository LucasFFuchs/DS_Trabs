package musica;

import regras.analisaChar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tradutor {

    private final List<analisaChar> regras;
    private static final Pattern ATRASO = Pattern.compile("^\\s*\\[(\\d{1,3})]\\s*");

    public Tradutor(List<analisaChar> regras) {
        this.regras = regras;
    }

    public String traduzir(String texto, int bpmInicial) {
        return traduzir(texto, bpmInicial, 7, 100, 6);
    }

    public String traduzir(String texto, int bpmInicial, int instrumentoInicial, int volumeInicial, int oitavaInicial) {
        String[] linhas = texto.split("\\R");
        if (linhas.length > Vozes.MAXIMO_DE_VOZES) {
            throw new IllegalArgumentException("O texto tem " + linhas.length + " linhas, mas o máximo é " + Vozes.MAXIMO_DE_VOZES + " vozes.");
        }

        StringBuilder musica = new StringBuilder("T" + bpmInicial + " ");
        int bpmAtual = bpmInicial;
        for (int i = 0; i < linhas.length; i++) {
            ParametrosAtuais parametros = Vozes.criar(i, instrumentoInicial, volumeInicial, oitavaInicial);
            parametros.setBPM(bpmAtual);
            musica.append("V").append(i).append(" ");
            musica.append(traduzirLinha(linhas[i], parametros));
            bpmAtual = parametros.getBPM();
        }
        return musica.toString().trim();
    }

    private String traduzirLinha(String linha, ParametrosAtuais parametros) {
        StringBuilder voz = new StringBuilder();
        voz.append("I").append(parametros.getNumInstrumento() - 1).append(" ");
        Matcher atraso = ATRASO.matcher(linha);
        if (atraso.find()) {
            int beats = Integer.parseInt(atraso.group(1));
            voz.append("Rq ".repeat(beats));
            linha = linha.substring(atraso.end());
        }
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
