public class Consoante implements analisaChar {
    @Override
    public boolean TesteChar(char c) {

        char letra = Character.toUpperCase(c);
        return Character.isLetter(letra) && "ABCDEFGIOU".indexOf(c) != -1;

    }

    @Override
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual){

        if (pAtual.isAnteriorEhNota()) {
            return pAtual.getUltimaNota() + pAtual.getOitava() + "q" + "a" + pAtual.getVolume() + " ";
        }

        return "Rq ";
    }

    
}
