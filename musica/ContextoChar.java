package musica;

public class ContextoChar {

    public final String pedaco;
    public final char atual;

    public ContextoChar(String pedaco) {
        this.pedaco = pedaco;
        this.atual = pedaco.charAt(0);
    }
}
