public class Andamento implements analisaChar {


    private static final int VARIACAO = 10;
    private static final int BPM_MINIMO = 20;
    private static final int BPM_MAXIMO = 300;

    @Override
    public boolean TesteChar(char c) {
        return c == '>' || c == '<';
    }

    @Override
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        int variacao = (contexto.atual == '>') ? VARIACAO : -VARIACAO;
        int novo = Math.max(BPM_MINIMO, Math.min(BPM_MAXIMO, pAtual.getBPM() + variacao));
        pAtual.setBPM(novo);
        pAtual.setAnteriorEhNota(false);
        return "T" + novo + " ";
    }
}
    

