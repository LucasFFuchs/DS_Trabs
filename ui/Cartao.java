package ui;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

//Painel com bordas arredondadas e fundo semitransparente usado nos blocos da interface
public class Cartao extends JPanel {

    private static final int RAIO = 20;

    public Cartao(LayoutManager layout) {
        super(layout);
        setOpaque(false);
        setBackground(Tema.CARD);
        setBorder(new EmptyBorder(14, 16, 14, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Desenha sombra leve atras do cartao
        g2.setColor(new Color(8, 56, 88, 28));
        g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4, RAIO, RAIO);

        //Desenha o fundo translucido e a borda clara
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 4, RAIO, RAIO);
        g2.setColor(Tema.BORDA_VIDRO);
        g2.drawRoundRect(0, 0, getWidth() - 3, getHeight() - 4, RAIO, RAIO);
        g2.dispose();
    }
}
