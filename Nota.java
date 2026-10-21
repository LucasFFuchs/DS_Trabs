public class Nota implements analisaChar {
    
    
    @Override 
    public boolean TesteChar(char c){
        return switch (c) {
            case 'A', 'B', 'C', 'D', 'E', 'F', 'G' -> true;
            default -> false;
        };
    }
    @Override
    public String TraducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual){
        String nota = String.valueOf(contexto.atual);
        if(contexto.proximo == '#') {
            nota+= "#";
        }
        if(contexto.proximo == 'b'){
            nota+= "b";
        }
        pAtual.setUltimaNota(nota);
        pAtual.setAnteriorEhNota(true);
        return nota + "q ";
        


    }
}
