package musica;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Selecionador {

    //Pattern que reconhece conjunto de carateres que vao juntos para uma instrucao
    //Exs: A C# Bb D:3 F#:5 ou qualquer outro caractere
    private static final Pattern CONJUNTO = Pattern.compile("[A-G][#b]?(:[1-7])?|.");

    public static List<String> separar(String texto) {
        //Cria e retorna uma lista de Strings com todos conjuntos de caracteres que vao juntos
        //Ex: A:3BbC#:2a;@ retorna ["A:3", "Bb", "C#:2", "a", ";", "@"]
        List<String> pedacos = new ArrayList<>();
        Matcher m = CONJUNTO.matcher(texto);
        while (m.find()) {
            pedacos.add(m.group());
        }
        return pedacos;
    }
}
