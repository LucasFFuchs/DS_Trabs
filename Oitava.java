public class Oitava implements analisaChar {

    @Override 
    public boolean TesteChar(char c){
        return switch (c){
            case '.', '?', 'V' -> true;
            default -> false;
        };
    }


    @Override
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        if (contexto.atual == 'V') {
            pAtual.descerOitava();
        } else {
            pAtual.subirOitava();
        }
        pAtual.setAnteriorEhNota(false);
        return "";
    }
    
}
