public class Consoante implements analisaChar {
    @Override
    public boolean TesteChar(char c) {

        return Character.isLetter(c)
                && "ABCDEFG"
                .indexOf(Character.toUpperCase(c))
                == -1;
    }

    @Override
    public String TraducaoTexto(
            ContextoChar ctx,
            ParametrosAtuais estado
    ) {

        if (estado.isAnteriorEhNota()) {
            return estado.getUltimaNota() + "q ";
        }

        return "Rq ";
    }

    
}
