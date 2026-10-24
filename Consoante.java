public class Consoante implements analisaChar {
    @Override
    public boolean TesteChar(char c) {

        char letra = Character.toUpperCase(c);
        return Character.isLetter(letra) && "ABCDEFGIOU".indexOf(c) != -1;

    }

    @Override
    public String TraducaoTexto(ContextoChar ctx, ParametrosAtuais estado){

        if (estado.isAnteriorEhNota()) {
            return estado.getUltimaNota() + "q ";
        }

        return "Rq ";
    }

    
}
