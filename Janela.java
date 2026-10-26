import org.jfugue.player.Player;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import java.awt.BorderLayout;

public class Janela extends JFrame {

    private final JTextArea areaTexto = new JTextArea(10, 40);
    private final JSpinner campoBpm = new JSpinner(new SpinnerNumberModel(120, 20, 300, 10));
    private final Tradutor tradutor;

    public Janela(Tradutor tradutor) {
        super("Gerador de música");
        this.tradutor = tradutor;

        JButton botaoTocar = new JButton("Tocar");
        botaoTocar.addActionListener(e -> tocar());

        JPanel baixo = new JPanel();
        baixo.add(new JLabel("BPM inicial:"));
        baixo.add(campoBpm);
        baixo.add(botaoTocar);

        add(new JScrollPane(areaTexto), BorderLayout.CENTER);
        add(baixo, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    private void tocar() {
        String texto = areaTexto.getText();
        if (texto.isBlank()) {
            JOptionPane.showMessageDialog(this, "Digite um texto antes.");
            return;
        }
        int bpm = (int) campoBpm.getValue();
        try {
            String musica = tradutor.traduzir(texto, bpm);
            new Thread(() -> new Player().play(musica)).start();   // thread própria: a janela não trava
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());   // ex.: mais de 4 linhas
        }
    }
}
