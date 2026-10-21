import org.jfugue.player.Player;
import java.util.Scanner;

public class Main {

    public static void main(String []args){

        String texto = "";
        Scanner Leitor = new Scanner(System.in);
        System.out.println("Insira o texto: ");
        texto = Leitor.nextLine();
        Nota nota = new Nota();

        StringBuilder TextoMusica = new StringBuilder();

        for (int i=0; i<texto.length(); i++){
            char Atual = texto.charAt(i);
            char Proximo = texto.charAt(i+1);
            if(i == 0){
                char Anterior = '\0';
            }else{
                char Anterior = texto.charAt(i-1);}
            
            



        }
        



    }
    
}
