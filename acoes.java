public class acoes {

    public class Nota implements ProcessaTexto.AnalisaChar {
        @Override 
        public boolean TesteChar(char c){
            return switch (c) {
                case 'A', 'B', 'C', 'D', 'E', 'F', 'G' -> true;
                default -> false;
            };
        }

    }
    
}
