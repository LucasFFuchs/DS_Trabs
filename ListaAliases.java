import java.lang.reflect.Field;
import java.util.Map;

public class ListaAliases {
    public static void main(String[] args) {
        try {
            Class<?> cls = Class.forName(
                "org.staccato.Maps"
            );

            for (Field campo : cls.getDeclaredFields()) {
                campo.setAccessible(true);

                Object valor = campo.get(null);

                if (valor instanceof Map<?, ?> map) {
                    System.out.println(
                        "\n=== " + campo.getName() + " ==="
                    );

                    for (Object chave : map.keySet()) {
                        System.out.println(chave);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}