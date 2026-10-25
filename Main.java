import org.jfugue.player.Player;
import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String []args){

        String texto = "";
        Scanner Leitor = new Scanner(System.in);
        System.out.println("Insira o texto: ");
        texto = Leitor.nextLine();
        Leitor.close();
        ParametrosAtuais parametros = new ParametrosAtuais();
        List <analisaChar> regras = InicializaRegras.criarRegras();

        StringBuilder TextoMusica = new StringBuilder();

        for(String pedaco: Selecionador.separar(texto)){
            ContextoChar contexto = new ContextoChar(pedaco);
            for(analisaChar regra : regras){
                if(regra.TesteChar(contexto.atual)){
                    TextoMusica.append(regra.TraducaoTexto(contexto, parametros));
                    break;
                }
            }

        }
        



    }
    
}
