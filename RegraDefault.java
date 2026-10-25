public class RegraDefault implements analisaChar {

    @Override 
    public boolean TesteChar(char c){
        return true;
    }

    @Override 
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual){
        boolean repete = pAtual.isAnteriorEhNota();
        pAtual.setAnteriorEhNota(false);
        if(repete){
            return pAtual.getUltimaNota() + pAtual.getOitava() + "q" + "a" + pAtual.getVolume() + " ";
        }
        return "Rq ";

    }
    
}
