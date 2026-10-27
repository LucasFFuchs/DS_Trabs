package ui;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

//Componente visual que desenha a barra de progresso da musica em execucao
public class BarraProgresso extends JComponent {

    private double progresso = 0;

    public BarraProgresso() {
        setPreferredSize(new Dimension(360, 12));
    }

    //Recebe um valor entre 0.0 e 1.0 e redesenha a barra
    public void setProgresso(double progresso) {
        this.progresso = Math.max(0, Math.min(1, progresso));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int y = getHeight() / 2 - 3;
        int preenchido = (int) (getWidth() * progresso);

        //Desenha o fundo da barra e a parte preenchida
        g2.setColor(new Color(145, 208, 224));
        g2.fillRoundRect(0, y, getWidth(), 6, 6, 6);
        g2.setColor(Tema.VERDE);
        g2.fillRoundRect(0, y, preenchido, 6, 6, 6);

        //Desenha a bolinha amarela na ponta do progresso quando estiver tocando
        if (progresso > 0) {
            g2.setColor(Tema.DESTAQUE_SOL);
            g2.fillOval(Math.max(0, preenchido - 6), y - 3, 12, 12);
            g2.setColor(Color.WHITE);
            g2.drawOval(Math.max(0, preenchido - 6), y - 3, 12, 12);
        }
        g2.dispose();
    }
}
