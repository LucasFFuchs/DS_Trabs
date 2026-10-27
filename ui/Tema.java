package ui;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Polygon;
import java.awt.RenderingHints;

//Centraliza as cores, fontes e criacao dos componentes visuais da interface
public class Tema {

    public static final Color CARD = new Color(224, 247, 250, 175);
    public static final Color CARD_CLARO = new Color(242, 252, 254, 215);
    public static final Color EDITOR = new Color(235, 250, 252, 0);
    public static final Color VERDE = new Color(13, 118, 148);
    public static final Color DESTAQUE_SOL = new Color(245, 190, 45);
    public static final Color TEXTO = new Color(11, 52, 78);
    public static final Color TEXTO_SECUNDARIO = new Color(42, 102, 128);
    public static final Color BORDA_VIDRO = new Color(255, 255, 255, 200);

    public static final Font FONTE = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONTE_PEQUENA = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONTE_NEGRITO = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONTE_GRANDE = new Font("Segoe UI", Font.PLAIN, 28);
    public static final Font FONTE_ICONE = new Font(Font.SANS_SERIF, Font.PLAIN, 17);
    public static final Font FONTE_MONO = new Font("Consolas", Font.PLAIN, 15);

    //Define cores padrao para barras de rolagem e dicas de tela (tooltips)
    public static void aplicarPadroes() {
        Color barra = new Color(120, 190, 208);
        Color trilha = new Color(215, 244, 248);
        UIManager.put("ScrollBar.width", 10);
        UIManager.put("ScrollBar.gradient", null);
        UIManager.put("ScrollBar.thumb", barra);
        UIManager.put("ScrollBar.thumbHighlight", barra);
        UIManager.put("ScrollBar.thumbShadow", barra);
        UIManager.put("ScrollBar.thumbDarkShadow", barra);
        UIManager.put("ScrollBar.track", trilha);
        UIManager.put("ScrollBar.trackHighlight", trilha);
        UIManager.put("ScrollBar.background", trilha);
        UIManager.put("ToolTip.background", new Color(240, 252, 254));
        UIManager.put("ToolTip.foreground", TEXTO);
        UIManager.put("ToolTip.border", BorderFactory.createEmptyBorder(4, 8, 4, 8));
    }

    public static JPanel painel(LayoutManager layout) {
        JPanel painel = new JPanel(layout);
        painel.setOpaque(false);
        return painel;
    }

    public static JPanel centralizado(JComponent componente) {
        JPanel painel = painel(new GridBagLayout());
        painel.add(componente);
        return painel;
    }

    public static JLabel rotulo(String texto, Font fonte, Color cor) {
        JLabel rotulo = new JLabel(texto);
        rotulo.setFont(fonte);
        rotulo.setForeground(cor);
        return rotulo;
    }

    //Cria botao arredondado com efeito ao passar o mouse ou clicar
    public static JButton botao(String texto, Color fundo, Color corTexto) {
        JButton botao = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = getBackground();
                Color cor = new Color(base.getRed(), base.getGreen(), base.getBlue(), Math.min(255, base.getAlpha()));
                if (getModel().isPressed()) {
                    cor = cor.darker();
                } else if (getModel().isRollover()) {
                    cor = new Color(
                            Math.min(255, cor.getRed() + 12),
                            Math.min(255, cor.getGreen() + 12),
                            Math.min(255, cor.getBlue() + 12),
                            Math.min(255, base.getAlpha() + 25)
                    );
                }
                g2.setColor(cor);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
                g2.setColor(BORDA_VIDRO);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        prepararBotao(botao, FONTE_NEGRITO);
        botao.setBackground(fundo);
        botao.setForeground(corTexto);
        botao.setBorder(new EmptyBorder(7, 15, 7, 15));
        return botao;
    }

    //Cria o botao redondo central de Play/Pause desenhando o icone diretamente
    public static JButton botaoPlayCircular() {
        JButton botao = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color cor = getBackground();
                if (getModel().isPressed()) {
                    cor = cor.darker();
                } else if (getModel().isRollover()) {
                    cor = cor.brighter();
                }
                g2.setColor(cor);
                g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                g2.setColor(BORDA_VIDRO);
                g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);

                g2.setColor(Color.WHITE);
                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                if ("PAUSAR".equals(getActionCommand())) {
                    g2.fillRoundRect(cx - 5, cy - 6, 3, 12, 2, 2);
                    g2.fillRoundRect(cx + 2, cy - 6, 3, 12, 2, 2);
                } else {
                    Polygon p = new Polygon(
                            new int[]{cx - 4, cx - 4, cx + 6},
                            new int[]{cy - 6, cy + 6, cy},
                            3
                    );
                    g2.fillPolygon(p);
                }
                g2.dispose();
            }
        };
        prepararBotao(botao, FONTE_NEGRITO);
        botao.setBackground(VERDE);
        botao.setActionCommand("TOCAR");
        botao.setPreferredSize(new Dimension(38, 38));
        return botao;
    }

    public static JButton botaoIcone(String texto) {
        JButton botao = new JButton(texto);
        prepararBotao(botao, FONTE_ICONE);
        botao.setForeground(TEXTO_SECUNDARIO);
        botao.setBorder(new EmptyBorder(4, 6, 4, 6));
        corPorEstado(botao);
        return botao;
    }

    public static JToggleButton alternador(String texto) {
        JToggleButton botao = new JToggleButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (isSelected()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(VERDE);
                    g2.fillOval(getWidth() / 2 - 2, getHeight() - 5, 5, 5);
                    g2.dispose();
                }
            }
        };
        prepararBotao(botao, FONTE_ICONE);
        botao.setForeground(TEXTO_SECUNDARIO);
        botao.setBorder(new EmptyBorder(4, 6, 8, 6));
        corPorEstado(botao);
        return botao;
    }

    private static void prepararBotao(AbstractButton botao, Font fonte) {
        botao.setFont(fonte);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);
        botao.setRolloverEnabled(true);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private static void corPorEstado(AbstractButton botao) {
        botao.getModel().addChangeListener(e -> {
            if (botao.isSelected()) {
                botao.setForeground(VERDE);
            } else if (botao.getModel().isRollover()) {
                botao.setForeground(TEXTO);
            } else {
                botao.setForeground(TEXTO_SECUNDARIO);
            }
        });
    }

    //Cria barra deslizante (slider) estilizada para Oitava, BPM e Volume
    public static JSlider slider(int minimo, int maximo, int valor) {
        JSlider slider = new JSlider(minimo, maximo, valor);
        slider.setOpaque(false);
        slider.setFocusable(false);
        slider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        slider.setUI(new BasicSliderUI(slider) {
            @Override
            protected Dimension getThumbSize() {
                return new Dimension(14, 14);
            }

            @Override
            public void paintFocus(Graphics g) {
            }

            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int y = trackRect.y + trackRect.height / 2 - 2;
                int preenchido = thumbRect.x + thumbRect.width / 2 - trackRect.x;
                g2.setColor(new Color(155, 212, 226));
                g2.fillRoundRect(trackRect.x, y, trackRect.width, 4, 4, 4);
                g2.setColor(VERDE);
                g2.fillRoundRect(trackRect.x, y, preenchido, 4, 4, 4);
                g2.dispose();
            }

            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(VERDE);
                g2.fillOval(thumbRect.x, thumbRect.y + thumbRect.height / 2 - 7, 14, 14);
                g2.setColor(Color.WHITE);
                g2.fillOval(thumbRect.x + 3, thumbRect.y + thumbRect.height / 2 - 4, 8, 8);
                g2.dispose();
            }
        });
        return slider;
    }

    public static void estilizar(JComboBox<?> combo) {
        combo.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton seta = new BasicArrowButton(
                        BasicArrowButton.SOUTH,
                        new Color(235, 250, 252),
                        new Color(235, 250, 252),
                        TEXTO_SECUNDARIO,
                        new Color(235, 250, 252)
                );
                seta.setBorder(new EmptyBorder(0, 0, 0, 0));
                return seta;
            }
        });
        combo.setBackground(new Color(235, 250, 252));
        combo.setForeground(TEXTO);
        combo.setFont(FONTE);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 210), 1, true),
                new EmptyBorder(4, 8, 4, 4)
        ));
        combo.setFocusable(false);
    }

    public static void estilizarLista(JList<String> lista) {
        lista.setOpaque(false);
        lista.setBackground(new Color(0, 0, 0, 0));
        lista.setForeground(TEXTO);
        lista.setSelectionBackground(new Color(255, 255, 255, 190));
        lista.setSelectionForeground(VERDE);
        lista.setFixedCellHeight(34);
        lista.setFont(FONTE);
        lista.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object valor, int indice, boolean selecionado, boolean foco) {
                String nome = String.valueOf(valor).replace(".txt", "").replace(".TXT", "");
                JLabel rotulo = (JLabel) super.getListCellRendererComponent(l, (indice + 1) + "   " + nome, indice, selecionado, false);
                rotulo.setOpaque(selecionado);
                if (selecionado) {
                    rotulo.setBackground(new Color(255, 255, 255, 200));
                    rotulo.setForeground(VERDE);
                } else {
                    rotulo.setForeground(TEXTO);
                }
                rotulo.setBorder(new EmptyBorder(0, 10, 0, 10));
                rotulo.setFont(selecionado ? FONTE_NEGRITO : FONTE);
                return rotulo;
            }
        });
    }

    public static void estilizarEditor(JTextArea area) {
        area.setOpaque(false);
        area.setBackground(EDITOR);
        area.setForeground(TEXTO);
        area.setCaretColor(VERDE);
        area.setSelectionColor(new Color(145, 225, 238));
        area.setSelectedTextColor(TEXTO);
        area.setFont(FONTE_MONO);
        area.setMargin(new Insets(6, 8, 6, 8));
        area.setTabSize(4);
    }

    public static JScrollPane rolagem(JComponent componente, Color fundo) {
        JScrollPane rolagem = new JScrollPane(componente);
        rolagem.setBorder(BorderFactory.createEmptyBorder());
        rolagem.setOpaque(false);
        rolagem.getViewport().setOpaque(false);
        rolagem.getViewport().setBackground(fundo);
        rolagem.setBackground(fundo);
        return rolagem;
    }
}
