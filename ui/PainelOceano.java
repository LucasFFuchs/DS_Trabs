package ui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

//Painel de fundo que carrega e exibe o GIF animado do oceano
public class PainelOceano extends JPanel {

    private final Image imagemOceano;

    public PainelOceano() {
        setOpaque(true);
        setPreferredSize(new Dimension(720, 460));

        //Busca o arquivo oceano.gif na pasta ui (ou pelo caminho relativo)
        URL recurso = PainelOceano.class.getResource("oceano.gif");
        if (recurso != null) {
            imagemOceano = new ImageIcon(recurso).getImage();
        } else {
            imagemOceano = new ImageIcon("ui/oceano.gif").getImage();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemOceano != null) {
            int pw = getWidth();
            int ph = getHeight();
            int iw = imagemOceano.getWidth(this);
            int ih = imagemOceano.getHeight(this);

            if (iw > 0 && ih > 0) {
                // Preenche toda a area mantendo a proporcao correta (sem achatar ou esticar)
                double escala = Math.max((double) pw / iw, (double) ph / ih);
                int nw = (int) Math.round(iw * escala);
                int nh = (int) Math.round(ih * escala);
                int x = (pw - nw) / 2;
                int y = (ph - nh) / 2;
                g.drawImage(imagemOceano, x, y, nw, nh, this);
            } else {
                g.drawImage(imagemOceano, 0, 0, pw, ph, this);
            }
        }
    }
}
