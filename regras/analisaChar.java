package regras;

import musica.ContextoChar;
import musica.ParametrosAtuais;

public interface analisaChar {
    boolean TesteChar(char c);
    String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual);
}
