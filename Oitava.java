public class Oitava implements analisaChar {

    @Override 
    public boolean TesteChar(char c){
        return switch (c){
            case '.', '?', 'V' -> true;
            default -> false;
        };
    }


    @Override 
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual){
        pAtual.setAnteriorEhNota(false);
        int OitavaAtual = pAtual.getOitava();
        pAtual.setOitava(OitavaAtual + 1);
        if(OitavaAtual + 1 >=)
    }
    
}
