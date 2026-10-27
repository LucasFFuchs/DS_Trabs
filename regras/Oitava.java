package regras;

import musica.ContextoChar;
import musica.ParametrosAtuais;

public class Oitava implements analisaChar {

    //Polimorfismo da TesteChar, verifica se é algum dos caracteres
    @Override
    public boolean TesteChar(char c) {
        return switch (c) {
            case '.', '?', 'V' -> true;
            default -> false;
        };
    }

    //Polimorfismo da TraducaoTexto, executa o comando de cada regra
    @Override
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        if (contexto.atual == 'V') {
            pAtual.descerOitava();
        } else {
            pAtual.subirOitava();
        }
        pAtual.setAnteriorEhNota(false);
        return ""; //Retorna String vazia, pois apenas muda oitava
    }
}
