import org.jfugue.player.Player;
import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String []args){

        String texto = "";
        Scanner Leitor = new Scanner(System.in);
        System.out.println("Insira o texto: ");
        texto = Leitor.nextLine();
        ParametrosAtuais parametros = new ParametrosAtuais();
        List <analisaChar> regras = InicializaRegras.criarRegras();

        StringBuilder TextoMusica = new StringBuilder();

        for (int i=0; i<texto.length(); i++){
            char Atual = texto.charAt(i);
            char Proximo = (i == texto.length() - 1 ? '\0' : texto.charAt(i+1));
            char Anterior = (i == 0 ? '\0' : texto.charAt(i-1));
            ContextoChar contexto = new ContextoChar(Anterior, Atual, Proximo);
            for(analisaChar regra: regras){
                if(regra.TesteChar(Atual)){
                    TextoMusica.append(regra.TraducaoTexto(contexto, parametros));
                    break;
                }
            }
            
            
        }
        



    }
    
}
