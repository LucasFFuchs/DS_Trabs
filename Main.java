import javax.swing.SwingUtilities;
import musica.Tradutor;
import regras.InicializaRegras;
import ui.Janela;

public class Main {

    public static void main(String[] args) {
        Tradutor tradutor = new Tradutor(InicializaRegras.criarRegras());
        SwingUtilities.invokeLater(() -> new Janela(tradutor).setVisible(true));
    }
}
