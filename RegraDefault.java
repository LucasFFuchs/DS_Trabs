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
            return FormatacaoJF.nota(pAtual.getUltimaNota(), pAtual.getOitava(), pAtual.getUltimaDuracao(), pAtual.getVolume());
        }
        return FormatacaoJF.pausa();

    }
    
}
