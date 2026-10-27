package musica;

public class ContextoChar {

    public final String pedaco;
    public final char atual;

    //Recebe um conjunto de caracteres que vamos chamar de pedaco e armazena apenas o
    //char da String na variavel atual Ex: "A:3" atual = "A"
    public ContextoChar(String pedaco) {
        this.pedaco = pedaco;
        this.atual = pedaco.charAt(0);
    }
}
