public class Silencio implements analisaChar {

    @Override 
    public boolean TesteChar(char c){
        return ("abcdefgh".indexOf(c) >= 0);
    }

    @Override
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual){
        pAtual.setAnteriorEhNota(false);

        return FormatacaoJF.pausa();

    }
    
}
