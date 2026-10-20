public class nota implements analisaChar {
    @Override 
    public boolean TesteChar(char c){
        return switch (c) {
            case 'A', 'B', 'C', 'D', 'E', 'F', 'G' -> true;
            default -> false;
        };
    }
    public String TraducaoTexto(char c){
        

    }
}
