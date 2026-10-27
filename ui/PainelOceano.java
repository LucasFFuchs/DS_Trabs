package ui;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Painel de fundo que desenha o oceano em grade e anima os animais marinhos
public class PainelOceano extends JPanel {

    private static final int TAM = 10;
    private static final char[] CHARS_GRADE = {'%', 'S', 't', '8', 'X', ':', ';'};
    private static final char[] CHARS_PEIXE_AMARELO = {'t', 't', '%', ';', ':'};
    private static final char[] CHARS_GOLFINHO = {'S', 'S', 't', '%', 'X'};

    private static final Color AGUA_TOPO_ESQ = new Color(154, 240, 232);
    private static final Color AGUA_MEIO = new Color(46, 186, 214);
    private static final Color AGUA_FUNDO_DIR = new Color(16, 108, 174);
    private static final Color LINHA_GRADE = new Color(255, 255, 255, 22);
    private static final Color SOMBRA_FUNDO = new Color(8, 56, 94, 68);

    private static final Color BALEIA_PROFUNDA = new Color(16, 92, 122, 165);
    private static final Color BALEIA_MEDIA = new Color(26, 112, 142, 145);
    private static final Color BALEIA_BORDA = new Color(54, 146, 172, 140);
    private static final Color BALEIA_NADADEIRA_CLARA = new Color(192, 242, 246, 180);
    private static final Color BALEIA_NADADEIRA_MEDIA = new Color(132, 214, 226, 155);
    private static final Color[] BRILHOS_BALEIA = {
        new Color(255, 82, 134),
        new Color(158, 107, 255),
        new Color(255, 184, 43),
        new Color(60, 240, 230)
    };

    private static final Color CASCO_BORDA = new Color(118, 78, 28);
    private static final Color CASCO_ESCURO = new Color(146, 102, 36);
    private static final Color CASCO_MEDIO = new Color(186, 138, 52);
    private static final Color CASCO_CLARO = new Color(218, 174, 74);
    private static final Color CASCO_OLIVA = new Color(112, 138, 72);
    private static final Color PELE_TARTARUGA = new Color(164, 126, 58);

    private static final Color TUBARAO_ESCURO = new Color(42, 62, 82);
    private static final Color TUBARAO_MEDIO = new Color(82, 108, 132);
    private static final Color TUBARAO_CLARO = new Color(148, 174, 196);
    private static final Color TUBARAO_BRANCO = new Color(236, 246, 252);

    private static final Color PEIXE_AMARELO_CLARO = new Color(242, 198, 42);
    private static final Color PEIXE_AMARELO_MEDIO = new Color(224, 178, 28);
    private static final Color CHAR_PEIXE_ROSA = new Color(232, 62, 148);
    private static final Color CHAR_PEIXE_VERDE = new Color(18, 172, 118);
    private static final Color CHAR_PEIXE_ESCURO = new Color(72, 52, 12);

    private static final Color GOLFINHO_ESCURO = new Color(66, 82, 90);
    private static final Color GOLFINHO_MEDIO = new Color(92, 112, 122);
    private static final Color GOLFINHO_CLARO = new Color(118, 142, 152);
    private static final Color CHAR_GOLFINHO_CLARO = new Color(214, 238, 244);

    //Posicoes relativas de cada peixinho amarelo dentro do cardume
    private static final double[][] CARDUME_AMARELO = {
        {9.5, -5.5, 3.3, 0.0},
        {5.0, -10.5, 3.1, 0.8},
        {4.5, -2.5, 3.5, 1.6},
        {3.0, 4.5, 3.2, 2.4},
        {-0.5, -6.5, 3.4, 3.1},
        {-1.5, 0.5, 3.3, 3.9},
        {-2.5, 7.5, 3.0, 4.7},
        {-6.5, -3.5, 3.2, 5.4},
        {-7.5, 3.0, 3.1, 6.1},
        {-11.5, 0.0, 2.9, 1.2}
    };

    private Font fonteCelula = new Font(Font.MONOSPACED, Font.BOLD, 8);

    private List<AnimalMarinho> tartarugas = new ArrayList<>();
    private List<AnimalMarinho> tubaroes = new ArrayList<>();

    private BufferedImage cacheAgua;
    private int larguraCache = -1;
    private int alturaCache = -1;

    private Color[][] gradeCor;
    private boolean[][] gradeSombra;
    private int[][] gradeTipo;
    private int colsGrade = 0;
    private int rowsGrade = 0;

    private int quadro = 0;

    private double baleiaX;
    private double baleiaY;
    private boolean baleiaAtiva = true;
    private boolean baleiaDireitaParaEsquerda = true;
    private boolean baleiaInicializada = false;
    private int esperaBaleia = 0;

    private double golfinhosX;
    private double golfinhosY;
    private boolean golfinhosAtivos = true;
    private boolean golfinhosEsquerdaParaDireita = true;
    private boolean golfinhosInicializados = false;
    private int esperaGolfinhos = 0;

    private double cardumeCx;
    private double cardumeCy;
    private double cardumeRaio;
    private double cardumeAngulo;
    private double cardumeAnguloFinal;
    private double cardumeVelAngular;
    private int cardumeEtapa = 0;
    private boolean cardumeInicializado = false;

    public PainelOceano() {
        setOpaque(true);
        setBackground(AGUA_MEIO);
        setPreferredSize(new Dimension(720, 460));

        //Inicializa as 3 tartarugas e os 2 tubaroes
        tartarugas.add(new AnimalMarinho(22, 34, -0.18, -0.14, 1.05, 0.0));
        tartarugas.add(new AnimalMarinho(66, 16, -0.15, 0.16, 0.94, 2.1));
        tartarugas.add(new AnimalMarinho(52, 46, -0.20, -0.11, 0.85, 4.2));

        tubaroes.add(new AnimalMarinho(78, 38, -0.34, -0.22, 1.05, 0.5));
        tubaroes.add(new AnimalMarinho(28, 12, 0.36, 0.10, 0.95, 3.4));

        //Timer que atualiza os quadros da animacao a cada 40ms
        new Timer(40, e -> atualizarCena()).start();
    }

    private void atualizarCena() {
        quadro++;
        int maxC = Math.max(70, colsGrade);
        int maxR = Math.max(46, rowsGrade);

        atualizarBaleia(maxC, maxR);
        atualizarCardumeAmarelo(maxC, maxR);
        atualizarGolfinhos(maxC, maxR);

        for (int i = 0; i < tartarugas.size(); i++) {
            tartarugas.get(i).mover(maxC, maxR, 16);
        }
        for (int i = 0; i < tubaroes.size(); i++) {
            tubaroes.get(i).mover(maxC, maxR, 20);
        }
        repaint();
    }

    //Controla o movimento da baleia no centro da tela com intervalo de 20s (500 quadros)
    private void atualizarBaleia(int maxC, int maxR) {
        if (!baleiaInicializada && colsGrade > 0) {
            baleiaInicializada = true;
            baleiaX = maxC + 26;
            baleiaY = maxR / 2.0;
        }
        if (baleiaAtiva) {
            baleiaY = maxR / 2.0 + Math.sin(quadro * 0.03) * 1.2;
            if (baleiaDireitaParaEsquerda) {
                baleiaX -= 0.28;
                if (baleiaX < -30) {
                    baleiaAtiva = false;
                    baleiaDireitaParaEsquerda = false;
                    esperaBaleia = 0;
                }
            } else {
                baleiaX += 0.28;
                if (baleiaX > maxC + 30) {
                    baleiaAtiva = false;
                    baleiaDireitaParaEsquerda = true;
                    esperaBaleia = 0;
                }
            }
        } else {
            esperaBaleia++;
            if (esperaBaleia >= 500) {
                baleiaAtiva = true;
                baleiaY = maxR / 2.0;
                baleiaX = baleiaDireitaParaEsquerda ? (maxC + 30) : -30;
            }
        }
    }

    //Controla os 2 golfinhos indo na parte superior e voltando pela parte inferior
    private void atualizarGolfinhos(int maxC, int maxR) {
        if (!golfinhosInicializados && colsGrade > 0) {
            golfinhosInicializados = true;
            golfinhosX = -26;
            golfinhosY = maxR * 0.16;
        }
        if (!golfinhosInicializados) {
            return;
        }
        if (golfinhosAtivos) {
            double yBase = golfinhosEsquerdaParaDireita ? (maxR * 0.16) : (maxR * 0.78);
            golfinhosY = yBase + Math.sin(quadro * 0.05) * 0.8;
            if (golfinhosEsquerdaParaDireita) {
                golfinhosX += 0.42;
                if (golfinhosX > maxC + 28) {
                    golfinhosAtivos = false;
                    golfinhosEsquerdaParaDireita = false;
                    esperaGolfinhos = 0;
                }
            } else {
                golfinhosX -= 0.42;
                if (golfinhosX < -28) {
                    golfinhosAtivos = false;
                    golfinhosEsquerdaParaDireita = true;
                    esperaGolfinhos = 0;
                }
            }
        } else {
            esperaGolfinhos++;
            if (esperaGolfinhos >= 200) {
                golfinhosAtivos = true;
                golfinhosY = golfinhosEsquerdaParaDireita ? (maxR * 0.16) : (maxR * 0.78);
                golfinhosX = golfinhosEsquerdaParaDireita ? -28 : (maxC + 28);
            }
        }
    }

    //Atualiza o angulo do cardume amarelo nos 4 cantos da tela e na diagonal
    private void atualizarCardumeAmarelo(int maxC, int maxR) {
        if (!cardumeInicializado && colsGrade > 0) {
            cardumeInicializado = true;
            configurarEtapaCardume(0, maxC, maxR);
        }
        if (!cardumeInicializado) {
            return;
        }
        cardumeAngulo += cardumeVelAngular;
        if ((cardumeVelAngular < 0 && cardumeAngulo <= cardumeAnguloFinal)
                || (cardumeVelAngular > 0 && cardumeAngulo >= cardumeAnguloFinal)) {
            configurarEtapaCardume((cardumeEtapa + 1) % 5, maxC, maxR);
        }
    }

    private void configurarEtapaCardume(int etapa, int maxC, int maxR) {
        cardumeEtapa = etapa;
        double anguloInicial;

        if (etapa == 0) {
            cardumeCx = maxC + 5.0;
            cardumeCy = maxR + 5.0;
            cardumeRaio = 32.0;
            anguloInicial = -0.22 * Math.PI;
            cardumeAnguloFinal = -1.28 * Math.PI;
        } else if (etapa == 1) {
            cardumeCx = -5.0;
            cardumeCy = maxR + 5.0;
            cardumeRaio = 32.0;
            anguloInicial = 0.28 * Math.PI;
            cardumeAnguloFinal = -0.78 * Math.PI;
        } else if (etapa == 2) {
            cardumeCx = -5.0;
            cardumeCy = -5.0;
            cardumeRaio = 32.0;
            anguloInicial = 0.78 * Math.PI;
            cardumeAnguloFinal = -0.28 * Math.PI;
        } else if (etapa == 3) {
            cardumeCx = maxC + 5.0;
            cardumeCy = -5.0;
            cardumeRaio = 32.0;
            anguloInicial = 1.28 * Math.PI;
            cardumeAnguloFinal = 0.22 * Math.PI;
        } else {
            cardumeCx = -maxC * 0.22;
            cardumeCy = maxR * 1.32;
            cardumeRaio = Math.sqrt(maxC * maxC + maxR * maxR) * 0.96;
            anguloInicial = -0.02 * Math.PI;
            cardumeAnguloFinal = -0.48 * Math.PI;
        }

        cardumeAngulo = anguloInicial;
        double sentido = (cardumeAnguloFinal >= anguloInicial) ? 1.0 : -1.0;
        cardumeVelAngular = sentido * (0.48 / cardumeRaio);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) {
            return;
        }

        if (cacheAgua == null || larguraCache != w || alturaCache != h) {
            construirCacheAgua(w, h);
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.drawImage(cacheAgua, 0, 0, null);
        desenharLinhasGrade(g2, w, h);

        //Preenche a grade na ordem de profundidade (baleia no fundo, depois peixes, tartarugas e tubaroes)
        limparGrade();
        if (baleiaAtiva && baleiaInicializada) {
            rasterizarBaleia();
        }
        if (golfinhosAtivos && golfinhosInicializados) {
            rasterizarGolfinhos();
        }
        if (cardumeInicializado) {
            rasterizarCardumeAmarelo();
        }
        for (int i = 0; i < tartarugas.size(); i++) {
            rasterizarTartaruga(tartarugas.get(i));
        }
        for (int i = 0; i < tubaroes.size(); i++) {
            rasterizarTubarao(tubaroes.get(i));
        }
        renderizarGradeMosaico(g2);
        desenharOndinhasPontilhadas(g2);

        g2.dispose();
    }

    private void construirCacheAgua(int w, int h) {
        larguraCache = w;
        alturaCache = h;
        colsGrade = Math.max(1, w / TAM + 2);
        rowsGrade = Math.max(1, h / TAM + 2);
        gradeCor = new Color[colsGrade][rowsGrade];
        gradeSombra = new boolean[colsGrade][rowsGrade];
        gradeTipo = new int[colsGrade][rowsGrade];

        cacheAgua = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = cacheAgua.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(new GradientPaint(0, 0, AGUA_TOPO_ESQ, w, h, AGUA_FUNDO_DIR));
        g2.fillRect(0, 0, w, h);

        pintarLuzAgua(g2, w * 0.16f, h * 0.14f, Math.max(w, h) * 0.46f, new Color(188, 254, 244, 140));
        pintarLuzAgua(g2, w * 0.36f, h * 0.48f, Math.max(w, h) * 0.38f, new Color(118, 240, 244, 115));
        pintarLuzAgua(g2, w * 0.72f, h * 0.24f, Math.max(w, h) * 0.34f, new Color(92, 218, 238, 90));
        g2.dispose();
    }

    private void pintarLuzAgua(Graphics2D g2, float cx, float cy, float raio, Color corCentro) {
        if (raio <= 1) {
            return;
        }
        Color transparente = new Color(corCentro.getRed(), corCentro.getGreen(), corCentro.getBlue(), 0);
        RadialGradientPaint luz = new RadialGradientPaint(
                new Point2D.Float(cx, cy),
                raio,
                new float[]{0f, 1f},
                new Color[]{corCentro, transparente}
        );
        g2.setPaint(luz);
        g2.fillRect(0, 0, larguraCache, alturaCache);
    }

    private void desenharLinhasGrade(Graphics2D g2, int w, int h) {
        g2.setColor(LINHA_GRADE);
        for (int x = 0; x < w; x += TAM) {
            g2.drawLine(x, 0, x, h);
        }
        for (int y = 0; y < h; y += TAM) {
            g2.drawLine(0, y, w, y);
        }
    }

    private void limparGrade() {
        for (int c = 0; c < colsGrade; c++) {
            Arrays.fill(gradeCor[c], null);
            Arrays.fill(gradeSombra[c], false);
            Arrays.fill(gradeTipo[c], 0);
        }
    }

    private void definirCelula(int c, int r, Color cor, int tipo, boolean comSombra, int dxSombra, int dySombra) {
        if (c >= 0 && c < colsGrade && r >= 0 && r < rowsGrade) {
            gradeCor[c][r] = cor;
            gradeTipo[c][r] = tipo;
        }
        if (comSombra) {
            int cs = c + dxSombra;
            int rs = r + dySombra;
            if (cs >= 0 && cs < colsGrade && rs >= 0 && rs < rowsGrade) {
                gradeSombra[cs][rs] = true;
            }
        }
    }

    private void rasterizarGolfinhos() {
        double dir = golfinhosEsquerdaParaDireita ? 1.0 : -1.0;
        rasterizarUmGolfinho(golfinhosX - dir * 4.5, golfinhosY - 2.0, dir, 0.0);
        rasterizarUmGolfinho(golfinhosX + dir * 4.0, golfinhosY + 2.2, dir, 1.4);
    }

    private void rasterizarUmGolfinho(double gx, double gy, double dir, double fase) {
        int cx = (int) Math.round(gx);
        int cy = (int) Math.round(gy);
        int dxSombra = (dir > 0) ? -2 : 2;

        for (int r = cy - 8; r <= cy + 8; r++) {
            for (int c = cx - 16; c <= cx + 16; c++) {
                double fx = (c - gx) * dir;
                double fy = r - gy;
                double onda = Math.sin(quadro * 0.20 + fase - fx * 0.22) * 0.42;
                double yCurvo = fy - onda;

                if (fx >= -11.5 && fx <= 12.0) {
                    double meiaAlt = 1.65;
                    if (fx >= 4.5) {
                        meiaAlt = 1.65 * (1.0 - (fx - 4.5) / 8.2);
                    } else if (fx <= -4.0) {
                        meiaAlt = 1.65 * (1.0 - (-4.0 - fx) / 8.2);
                    }
                    if (Math.abs(yCurvo) <= meiaAlt) {
                        Color cor;
                        if (fx > 8.5 || yCurvo > meiaAlt * 0.45) {
                            cor = GOLFINHO_CLARO;
                        } else if (yCurvo < -0.2 || (c + r) % 2 == 0) {
                            cor = GOLFINHO_ESCURO;
                        } else {
                            cor = GOLFINHO_MEDIO;
                        }
                        definirCelula(c, r, cor, 3, true, dxSombra, 2);
                        continue;
                    }
                }

                if (yCurvo >= 0.0 && yCurvo <= 4.2 && Math.abs(fx - (-10.5 - yCurvo * 0.52)) <= 1.05) {
                    definirCelula(c, r, GOLFINHO_ESCURO, 3, true, dxSombra, 2);
                    continue;
                }

                if (yCurvo >= 1.2 && yCurvo <= 3.8 && Math.abs(fx - (3.0 - (yCurvo - 1.2) * 0.42)) <= 1.05) {
                    definirCelula(c, r, GOLFINHO_MEDIO, 3, true, dxSombra, 2);
                }
            }
        }
    }

    private void rasterizarCardumeAmarelo() {
        double sentido = (cardumeVelAngular >= 0) ? 1.0 : -1.0;
        for (int i = 0; i < CARDUME_AMARELO.length; i++) {
            double offsetFrente = CARDUME_AMARELO[i][0];
            double offsetLado = CARDUME_AMARELO[i][1];
            double meioComp = CARDUME_AMARELO[i][2];
            double fase = CARDUME_AMARELO[i][3];

            double raioPeixe = Math.max(8.0, cardumeRaio + offsetLado);
            double angPeixe = cardumeAngulo + sentido * (offsetFrente / raioPeixe);
            double px = cardumeCx + raioPeixe * Math.cos(angPeixe);
            double py = cardumeCy + raioPeixe * Math.sin(angPeixe);

            double angTangente = angPeixe + sentido * (Math.PI / 2.0);
            double cos = Math.cos(angTangente);
            double sin = Math.sin(angTangente);

            int cx = (int) Math.round(px);
            int cy = (int) Math.round(py);

            for (int r = cy - 6; r <= cy + 6; r++) {
                for (int c = cx - 6; c <= cx + 6; c++) {
                    double dx = c - px;
                    double dy = r - py;
                    double fx = dx * cos + dy * sin;
                    double fy = -dx * sin + dy * cos;
                    double onda = Math.sin(quadro * 0.26 + fase - fx * 0.55) * 0.28;
                    double yCurvo = fy - onda;

                    if (Math.abs(fx) <= meioComp) {
                        double largura = 1.08 * Math.cos((fx / meioComp) * 0.95);
                        if (Math.abs(yCurvo) <= largura) {
                            Color cor = ((c + r) % 2 == 0) ? PEIXE_AMARELO_CLARO : PEIXE_AMARELO_MEDIO;
                            definirCelula(c, r, cor, 2, true, 1, 2);
                        }
                    }
                }
            }
        }
    }

    private void rasterizarBaleia() {
        int cx = (int) Math.round(baleiaX);
        int cy = (int) Math.round(baleiaY);
        double dir = baleiaDireitaParaEsquerda ? -1.0 : 1.0;

        for (int r = cy - 20; r <= cy + 20; r++) {
            for (int c = cx - 28; c <= cx + 28; c++) {
                double fx = (c - baleiaX) * dir;
                double fy = r - baleiaY;

                double ondaCauda = 0;
                if (fx < -4) {
                    ondaCauda = Math.sin(quadro * 0.10 - fx * 0.18) * Math.max(0, (-fx - 4) * 0.14);
                }
                double ay = Math.abs(fy - ondaCauda);

                if (fx >= -18.0 && fx <= 14.0) {
                    double largura;
                    if (fx >= 0) {
                        largura = 6.2 * Math.sqrt(Math.max(0, 1.0 - (fx / 14.2) * (fx / 14.2)));
                    } else {
                        largura = 6.2 * Math.pow((fx + 19.0) / 19.0, 1.45);
                    }
                    if (ay <= largura) {
                        Color cor;
                        if (ay > largura - 0.9) {
                            cor = BALEIA_BORDA;
                        } else if ((c + r) % 2 == 0) {
                            cor = BALEIA_PROFUNDA;
                        } else {
                            cor = BALEIA_MEDIA;
                        }
                        definirCelula(c, r, cor, 1, false, 0, 0);
                        continue;
                    }
                }

                double batidaNadadeira = Math.sin(quadro * 0.08) * 0.8;
                if (ay >= 4.6 && ay <= 16.5) {
                    double prog = ay - 4.6;
                    double centroX = 2.8 - prog * 0.65 + batidaNadadeira * (prog * 0.08);
                    double meiaLargura = Math.max(0.9, 2.5 - prog * 0.13);
                    double distX = fx - centroX;
                    if (Math.abs(distX) <= meiaLargura) {
                        Color cor;
                        if (distX >= -0.3 || prog > 8.5) {
                            cor = ((c + r) % 2 == 0) ? BALEIA_NADADEIRA_CLARA : BALEIA_NADADEIRA_MEDIA;
                        } else {
                            cor = BALEIA_MEDIA;
                        }
                        definirCelula(c, r, cor, 1, false, 0, 0);
                        continue;
                    }
                }

                if (fx >= -23.5 && fx <= -16.5 && ay <= 7.8) {
                    double centroCaudaX = -18.0 - ay * 0.52;
                    double meiaLargCauda = Math.max(0.8, 2.2 - ay * 0.16);
                    if (Math.abs(fx - centroCaudaX) <= meiaLargCauda) {
                        Color cor = (fx < centroCaudaX) ? BALEIA_NADADEIRA_MEDIA : BALEIA_PROFUNDA;
                        definirCelula(c, r, cor, 1, false, 0, 0);
                    }
                }
            }
        }
    }

    private void rasterizarTartaruga(AnimalMarinho t) {
        double angulo = Math.atan2(t.vy, t.vx);
        double cos = Math.cos(angulo);
        double sin = Math.sin(angulo);
        double batida = Math.sin(quadro * 0.14 + t.fase) * 0.42;

        int alcance = (int) Math.ceil(13 * t.escala);
        int cx = (int) Math.round(t.x);
        int cy = (int) Math.round(t.y);

        for (int r = cy - alcance; r <= cy + alcance; r++) {
            for (int c = cx - alcance; c <= cx + alcance; c++) {
                double dx = (c - t.x) / t.escala;
                double dy = (r - t.y) / t.escala;
                double fx = dx * cos + dy * sin;
                double fy = -dx * sin + dy * cos;

                double casco = (fx * fx) / (6.8 * 6.8) + (fy * fy) / (5.4 * 5.4);
                if (casco <= 1.0) {
                    Color cor;
                    if (casco > 0.78) {
                        cor = ((c + r) % 2 == 0) ? CASCO_BORDA : CASCO_CLARO;
                    } else {
                        int padrao = Math.abs((int) Math.round(fx * 1.4) * 3 + (int) Math.round(fy * 1.4) * 5) % 4;
                        if (padrao == 0) {
                            cor = CASCO_ESCURO;
                        } else if (padrao == 1) {
                            cor = CASCO_MEDIO;
                        } else if (padrao == 2) {
                            cor = CASCO_CLARO;
                        } else {
                            cor = CASCO_OLIVA;
                        }
                    }
                    definirCelula(c, r, cor, 0, true, 2, 2);
                    continue;
                }

                double cabeca = ((fx - 7.8) * (fx - 7.8)) / (2.3 * 2.3) + (fy * fy) / (1.7 * 1.7);
                if (cabeca <= 1.0) {
                    definirCelula(c, r, PELE_TARTARUGA, 0, true, 2, 2);
                    continue;
                }

                double afy = Math.abs(fy);
                double rx = fx - 2.4;
                double ry = afy - 4.8;
                double cosF = Math.cos(batida);
                double sinF = Math.sin(batida);
                double nfx = rx * cosF + ry * sinF;
                double nfy = -rx * sinF + ry * cosF;
                if ((nfx * nfx) / (2.0 * 2.0) + (nfy * nfy) / (5.2 * 5.2) <= 1.0 && afy > 3.8) {
                    Color cor = (nfy > 2.5) ? CASCO_ESCURO : PELE_TARTARUGA;
                    definirCelula(c, r, cor, 0, true, 2, 2);
                    continue;
                }

                double hfx = fx + 5.8;
                double hfy = afy - 3.6;
                if ((hfx * hfx) / (2.2 * 2.2) + (hfy * hfy) / (1.5 * 1.5) <= 1.0) {
                    definirCelula(c, r, CASCO_ESCURO, 0, true, 2, 2);
                }
            }
        }
    }

    private void rasterizarTubarao(AnimalMarinho tb) {
        double angulo = Math.atan2(tb.vy, tb.vx);
        double cos = Math.cos(angulo);
        double sin = Math.sin(angulo);

        int alcance = (int) Math.ceil(16 * tb.escala);
        int cx = (int) Math.round(tb.x);
        int cy = (int) Math.round(tb.y);

        for (int r = cy - alcance; r <= cy + alcance; r++) {
            for (int c = cx - alcance; c <= cx + alcance; c++) {
                double dx = (c - tb.x) / tb.escala;
                double dy = (r - tb.y) / tb.escala;
                double fx = dx * cos + dy * sin;
                double fy = -dx * sin + dy * cos;

                double onda = 0;
                if (fx < 2) {
                    onda = Math.sin(quadro * 0.22 + tb.fase - fx * 0.35) * Math.max(0, (3 - fx) * 0.18);
                }
                double yCurvo = fy - onda;
                double ay = Math.abs(yCurvo);

                if (fx >= -10.5 && fx <= 9.5) {
                    double largura = 2.8 * Math.cos((fx - 1.5) * 0.13);
                    if (fx < -6.5) {
                        largura = Math.max(0.6, (fx + 10.5) * 0.35);
                    }
                    if (ay <= largura) {
                        Color cor;
                        if (yCurvo > largura * 0.45) {
                            cor = TUBARAO_BRANCO;
                        } else if (ay < largura * 0.4) {
                            cor = TUBARAO_ESCURO;
                        } else if ((c + r) % 2 == 0) {
                            cor = TUBARAO_MEDIO;
                        } else {
                            cor = TUBARAO_CLARO;
                        }
                        definirCelula(c, r, cor, 0, true, 3, 3);
                        continue;
                    }
                }

                double pfx = fx - 1.2 + (ay - 2.2) * 0.55;
                if (ay >= 2.0 && ay <= 7.2 && Math.abs(pfx) <= (7.5 - ay) * 0.38) {
                    Color cor = (pfx > 0.4) ? TUBARAO_BRANCO : TUBARAO_ESCURO;
                    definirCelula(c, r, cor, 0, true, 3, 3);
                    continue;
                }

                double tfx = fx + 10.5 + (ay - 0.5) * 0.45;
                if (ay >= 0.4 && ay <= 4.6 && Math.abs(tfx) <= 1.2) {
                    definirCelula(c, r, TUBARAO_ESCURO, 0, true, 3, 3);
                }
            }
        }
    }

    //Desenha na tela as sombras, os quadrados coloridos e os caracteres ASCII de cada celula
    private void renderizarGradeMosaico(Graphics2D g2) {
        g2.setFont(fonteCelula);
        for (int r = 0; r < rowsGrade; r++) {
            int y = r * TAM;
            for (int c = 0; c < colsGrade; c++) {
                if (gradeSombra[c][r] && gradeCor[c][r] == null) {
                    g2.setColor(SOMBRA_FUNDO);
                    g2.fillRect(c * TAM, y, TAM - 1, TAM - 1);
                }
            }
        }

        for (int r = 0; r < rowsGrade; r++) {
            int y = r * TAM;
            for (int c = 0; c < colsGrade; c++) {
                Color cor = gradeCor[c][r];
                if (cor == null) {
                    continue;
                }
                int x = c * TAM;
                g2.setColor(cor);
                g2.fillRect(x, y, TAM - 1, TAM - 1);

                int tipo = gradeTipo[c][r];
                char ch;
                if (tipo == 3) {
                    ch = CHARS_GOLFINHO[Math.abs(c * 17 + r * 23 + quadro / 6) % CHARS_GOLFINHO.length];
                    int hashBrilho = Math.abs(c * 37 + r * 53 + quadro / 9) % 24;
                    if (hashBrilho < BRILHOS_BALEIA.length) {
                        g2.setColor(BRILHOS_BALEIA[hashBrilho]);
                    } else {
                        g2.setColor(CHAR_GOLFINHO_CLARO);
                    }
                } else if (tipo == 2) {
                    ch = CHARS_PEIXE_AMARELO[Math.abs(c * 13 + r * 19 + quadro / 7) % CHARS_PEIXE_AMARELO.length];
                    int hashCor = Math.abs(c * 29 + r * 43 + quadro / 9) % 14;
                    if (hashCor == 0) {
                        g2.setColor(CHAR_PEIXE_ROSA);
                    } else if (hashCor == 1) {
                        g2.setColor(CHAR_PEIXE_VERDE);
                    } else {
                        g2.setColor(CHAR_PEIXE_ESCURO);
                    }
                } else if (tipo == 1) {
                    ch = CHARS_GRADE[Math.abs(c * 11 + r * 17 + quadro / 6) % CHARS_GRADE.length];
                    int hashBrilho = Math.abs(c * 31 + r * 47 + quadro / 10) % 29;
                    if (hashBrilho < BRILHOS_BALEIA.length) {
                        g2.setColor(BRILHOS_BALEIA[hashBrilho]);
                    } else {
                        g2.setColor(corCaractereCelula(cor));
                    }
                } else {
                    ch = CHARS_GRADE[Math.abs(c * 11 + r * 17 + quadro / 6) % CHARS_GRADE.length];
                    g2.setColor(corCaractereCelula(cor));
                }
                g2.drawString(String.valueOf(ch), x + 2, y + TAM - 2);
            }
        }
    }

    private Color corCaractereCelula(Color fundo) {
        int r = Math.max(0, (int) (fundo.getRed() * 0.42));
        int g = Math.max(0, (int) (fundo.getGreen() * 0.42));
        int b = Math.max(0, (int) (fundo.getBlue() * 0.42));
        return new Color(r, g, b, Math.min(220, fundo.getAlpha()));
    }

    private void desenharOndinhasPontilhadas(Graphics2D g2) {
        g2.setFont(fonteCelula);
        int[][] grupos = {
            {colsGrade / 5, 3, 9, 4},
            {colsGrade / 2 + 5, 3, 10, 4},
            {colsGrade - 14, rowsGrade / 2, 9, 5},
            {colsGrade / 2 - 4, rowsGrade - 11, 11, 4},
            {colsGrade / 3, rowsGrade / 3, 8, 3}
        };

        for (int g = 0; g < grupos.length; g++) {
            int cx = grupos[g][0] + (int) Math.round(Math.sin(quadro * 0.03 + g) * 2);
            int cy = grupos[g][1];
            int larg = grupos[g][2];
            int alt = grupos[g][3];
            for (int r = 0; r < alt; r++) {
                for (int c = 0; c < larg; c++) {
                    if ((c + r + quadro / 7) % 2 == 0) {
                        g2.setColor(new Color(255, 255, 255, 165));
                        g2.drawString("·", (cx + c) * TAM + 2, (cy + r) * TAM + TAM - 2);
                    }
                }
            }
        }
    }
}
