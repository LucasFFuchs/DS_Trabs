package regras;

import musica.ContextoChar;
import musica.ParametrosAtuais;

//Interface que sera como um esqueleto para cada regra
public interface analisaChar {
    boolean TesteChar(char c);
    String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual);
}
