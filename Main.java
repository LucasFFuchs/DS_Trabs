import org.jfugue.player.Player;
import java.util.Scanner;

public class Main {

    public static void main(String []args){

        String texto = "";
        Scanner Leitor = new Scanner(System.in);
        System.out.println("Insira o texto: ");
        texto = Leitor.nextLine();

        StringBuilder TextoMusica = new StringBuilder();

        for (int i=0; i<texto.length(); i++){
            char chAtual = texto.charAt(i);
            char chProximo = texto.charAt(i+1);
            if(i == 0){
                char chAnterior = '\0';
            }else{
                char chAnterior = texto.charAt(i-1);}
            



        }
        



    }
    
}
