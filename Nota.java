public class Nota implements analisaChar {
    
    
    @Override 
    public boolean TesteChar(char c){
        return switch (c) {
            case 'A', 'B', 'C', 'D', 'E', 'F', 'G' -> true;
            default -> false;
        };
    }
    @Override
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        String nota = Duracao.tirarDuracao(contexto.pedaco);
        char duracao = Duracao.letraDeDuracao(contexto.pedaco);

        pAtual.setUltimaNota(nota);
        pAtual.setUltimaDuracao(duracao);
        pAtual.setAnteriorEhNota(true);
        return FormatacaoJF.nota(nota, pAtual.getOitava(), duracao, pAtual.getVolume());
    }
        


}

