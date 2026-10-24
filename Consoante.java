public class Consoante implements analisaChar {
    @Override
    public boolean TesteChar(char c) {

        char letra = Character.toUpperCase(c);
        if(!Character.isLetter(letra)){
            return false;
        }
        if("ABCDEFGIOU".indexOf(c) != -1){
            return false;
        }
        return true;

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
