package regras;

import musica.ContextoChar;
import musica.FormatacaoJF;
import musica.ParametrosAtuais;

public class TrocaInstrumento implements analisaChar {

    private static final int BANDONEON = 24;
    private static final int AGOGO = 114;
    private static final int TUBULAR_BELLS = 15;
    private static final int TOTAL_INSTRUMENTOS = 128;

    @Override
    public boolean TesteChar(char c) {
        return "!,;0123456789".indexOf(c) >= 0;
    }

    @Override
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        int novo = switch (contexto.atual) {
            case '!' -> BANDONEON;
            case ',' -> AGOGO;
            case ';', '1', '3', '5', '7', '9' -> TUBULAR_BELLS;
            case '0', '2', '4', '6', '8' -> pAtual.getNumInstrumento() + (contexto.atual - '0');
            default -> throw new IllegalStateException("Caractere inesperado: " + contexto.atual);
        };

        novo = (novo - 1) % TOTAL_INSTRUMENTOS + 1;
        pAtual.setNumInstrumento(novo);
        pAtual.setAnteriorEhNota(false);
        return FormatacaoJF.MudaInstrumento(novo);
    }
}
