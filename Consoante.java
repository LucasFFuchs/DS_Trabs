public class Consoante implements analisaChar {
    @Override
    public boolean testeChar(char c) {

        return Character.isLetter(c)
                && "ABCDEFG"
                .indexOf(Character.toUpperCase(c))
                == -1;
    }

    @Override
    public String traduzir(
            ContextoChar ctx,
            EstadoMusical estado
    ) {

        if (estado.isUltimaCoisaFoiNota()) {
            return estado.getUltimaNota() + "q ";
        }

        return "Rq ";
    }

    
}
