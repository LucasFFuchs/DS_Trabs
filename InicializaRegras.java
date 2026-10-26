import java.util.ArrayList;
import java.util.List;

public class InicializaRegras{
    public static List<analisaChar> criarRegras() {
        List<analisaChar> regras = new ArrayList<>();
        regras.add(new Nota());
        regras.add(new Silencio());
        regras.add(new TrocaInstrumento());
        regras.add(new Volume());
        regras.add(new Oitava());
        regras.add(new Andamento());
        regras.add(new RegraDefault());
        return regras;

    }
}