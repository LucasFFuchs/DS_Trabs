public class Volume implements analisaChar{

    @Override
    public boolean TesteChar(char c){
        return c == ' ';
    }

    @Override
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual){
        pAtual.setVolume(Math.min(pAtual.getVolume() * 2, 127));
        pAtual.setAnteriorEhNota(false);
        return "";
    }

    
}
