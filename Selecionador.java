import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Selecionador {

    private static final Pattern CONJUNTO = Pattern.compile("[A-G][#b]?(:[1-7])?|.", Pattern.DOTALL);
    
    public static List<String> separar(String texto){
        List<String> pedacos = new ArrayList<>();
        Matcher m = CONJUNTO.matcher(texto);
        while(m.find()){
            pedacos.add(m.group());
        }
        return pedacos;
    }
}
