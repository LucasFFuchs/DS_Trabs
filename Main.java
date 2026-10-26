import org.jfugue.player.Player;
import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String []args){

        String texto = "";
        Scanner Leitor = new Scanner(System.in);
        System.out.println("Insira o texto: ");
        texto = Leitor.nextLine();
        System.out.println("Insira o BPM inicial desejado: ");
        int bpm = Leitor.nextInt();
        Leitor.close();
        List <analisaChar> regras = InicializaRegras.criarRegras();


        Tradutor tradutor = new Tradutor(regras);
        String musica = tradutor.traduzir(texto, bpm);
        System.out.println("MusicString: " + musica);   // mostra o que vai para o JFugue
        new Player().play(musica);
        }

    }
        



    
    

