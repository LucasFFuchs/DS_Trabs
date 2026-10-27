package regras;

import musica.ContextoChar;
import musica.FormatacaoJF;
import musica.ParametrosAtuais;

public class Silencio implements analisaChar {

    //Polimorfismo da TesteChar, verifica se é algum dos caracteres em abcdefg
    @Override
    public boolean TesteChar(char c) {
        return ("abcdefg".indexOf(c) >= 0);
    }

    //Polimorfismo da TraducaoTexto, executa o comando de cada regra
    @Override
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        pAtual.setAnteriorEhNota(false);
        return FormatacaoJF.pausa();
    }
}
