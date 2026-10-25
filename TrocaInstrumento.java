public class TrocaInstrumento implements analisaChar {


    @Override
    public boolean TesteChar(char c){
        return "!,;0123456789".indexOf(c) >=0;
        }
    
    @Override
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual){
        
        switch (contexto.atual) {
            case '!':
                pAtual.setNumInstrumento(24);
                pAtual.setAnteriorEhNota(false);
                return "I24 ";
            case ',':
                pAtual.setNumInstrumento(114);
                pAtual.setAnteriorEhNota(false);
                return "I114 ";
            case ';':
                pAtual.setNumInstrumento(15);
                pAtual.setAnteriorEhNota(false);
                return "I15 ";
            case '0','2','4','6','8':
                int digito = contexto.atual - '0';
                int InstrumentoAtual = pAtual.getNumInstrumento();
                pAtual.setNumInstrumento(digito + InstrumentoAtual);
                pAtual.setAnteriorEhNota(false);
                return "I" + pAtual.getNumInstrumento() + " ";
            case '1', '3', '5', '7', '9' :
                pAtual.setNumInstrumento(15);
                pAtual.setAnteriorEhNota(false);
                return "I15 ";
            default:
                return "";

        }
    }

}
    

