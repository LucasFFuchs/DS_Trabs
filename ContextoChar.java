public class ContextoChar {

    public final char anterior;
    public final char atual;
    public final char proximo;

    public ContextoChar(
            char anterior,
            char atual,
            char proximo) {

        this.anterior = anterior;
        this.atual = atual;
        this.proximo = proximo;
    }
}
