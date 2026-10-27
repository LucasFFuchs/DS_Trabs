package ui;

import io.LeitorTxt;
import musica.Instrumento;
import musica.Tradutor;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.jfugue.player.SequencerManager;
import playlist.Album;
import playlist.ListaPlaylist;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;

//Janela principal do programa que integra o editor de texto, playlists e controles do player
public class Janela extends JFrame {

    private static final String MODO_FECHADO = "FECHADO";
    private static final String MODO_EXPANDIDO = "EXPANDIDO";

    private Tradutor tradutor;
    private Player player = new Player();

    private JTextArea areaTexto = new JTextArea(8, 42);
    private PainelOceano painelOceano = new PainelOceano();
    private CardLayout layoutCaixa = new CardLayout();
    private Cartao caixaMusica = new Cartao(layoutCaixa);
    private boolean caixaExpandida = false;

    private JComboBox<Instrumento> comboInstrumentos = new JComboBox<>(Instrumento.getCatalogo());
    private JSlider sliderOitava = Tema.slider(0, 9, 6);
    private JSlider sliderVolume = Tema.slider(0, 100, 100);
    private JSlider sliderBpm = Tema.slider(20, 300, 120);

    private JButton botaoPlayPause = Tema.botaoPlayCircular();
    private JButton botaoParar = Tema.botaoIcone("⏹");
    private BarraProgresso barraProgresso = new BarraProgresso();

    private Album album = new Album("Meu Álbum");
    private ListaPlaylist playlistAtual;
    private JComboBox<String> comboPlaylists = new JComboBox<>();

    private DefaultListModel<String> modeloLista = new DefaultListModel<>();
    private JList<String> jListPlaylist = new JList<>(modeloLista);

    private JToggleButton botaoAleatorio = Tema.alternador("🔀");
    private JToggleButton botaoLoop = Tema.alternador("🔁");

    private boolean tocandoPlaylist = false;
    private File arquivoAtual = null;
    private int bpmBaseExecucao = 120;

    public Janela(Tradutor tradutor) {
        super("Trabalho DS");
        this.tradutor = tradutor;
        Tema.aplicarPadroes();

        //Cria a primeira playlist padrao dentro do album
        ListaPlaylist inicial = new ListaPlaylist("Playlist 1");
        album.adicionar(inicial);
        playlistAtual = inicial;
        comboPlaylists.addItem(inicial.getNome());
        comboPlaylists.addActionListener(e -> alternarPlaylistSelecionada());

        Tema.estilizar(comboPlaylists);
        Tema.estilizar(comboInstrumentos);
        Tema.estilizarLista(jListPlaylist);
        Tema.estilizarEditor(areaTexto);

        sliderBpm.addChangeListener(e -> atualizarBpmEmTempoReal());

        painelOceano.setLayout(new BorderLayout(12, 12));
        painelOceano.setBorder(new EmptyBorder(12, 12, 12, 12));
        painelOceano.add(criarPainelLateral(), BorderLayout.WEST);
        painelOceano.add(criarPainelCentral(), BorderLayout.CENTER);
        painelOceano.add(criarBarraPlayer(), BorderLayout.SOUTH);

        setContentPane(painelOceano);
        iniciarMonitorProgresso();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setMinimumSize(new Dimension(1060, 680));
        setPreferredSize(new Dimension(1160, 730));
        pack();
        setLocationRelativeTo(null);
    }

    //Monta o painel esquerdo com o seletor de album, lista de musicas e botoes da playlist
    private JPanel criarPainelLateral() {
        Cartao lateral = new Cartao(new BorderLayout(0, 12));
        lateral.setPreferredSize(new Dimension(272, 0));

        JPanel cabecalho = Tema.painel(new BorderLayout());
        cabecalho.add(Tema.rotulo("Sua Biblioteca", Tema.FONTE_TITULO, Tema.TEXTO), BorderLayout.WEST);
        JButton botaoNovaPlaylist = Tema.botao("+ Playlist", Tema.CARD_CLARO, Tema.TEXTO);
        botaoNovaPlaylist.addActionListener(e -> adicionarPlaylistAoAlbum());
        cabecalho.add(botaoNovaPlaylist, BorderLayout.EAST);

        JPanel seletorAlbum = Tema.painel(new BorderLayout(8, 0));
        seletorAlbum.add(Tema.rotulo("Álbum:", Tema.FONTE_PEQUENA, Tema.TEXTO_SECUNDARIO), BorderLayout.WEST);
        seletorAlbum.add(comboPlaylists, BorderLayout.CENTER);

        JPanel topo = Tema.painel(new BorderLayout(0, 10));
        topo.add(cabecalho, BorderLayout.NORTH);
        topo.add(seletorAlbum, BorderLayout.CENTER);
        topo.add(Tema.rotulo("Músicas da playlist (máx. 10)", Tema.FONTE_PEQUENA, Tema.TEXTO_SECUNDARIO), BorderLayout.SOUTH);

        jListPlaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListPlaylist.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !tocandoPlaylist) {
                selecionarMusicaPlaylist();
            }
        });

        JPanel linhaArquivos = Tema.painel(new GridLayout(1, 2, 8, 0));
        JButton botaoAdd = Tema.botao("+ Adicionar", Tema.CARD_CLARO, Tema.TEXTO);
        JButton botaoRemover = Tema.botao("- Remover", Tema.CARD_CLARO, Tema.TEXTO);
        botaoAdd.addActionListener(e -> adicionarNaPlaylist());
        botaoRemover.addActionListener(e -> removerDaPlaylist());
        linhaArquivos.add(botaoAdd);
        linhaArquivos.add(botaoRemover);

        JButton botaoTocarPlaylist = Tema.botao("Tocar Playlist", Tema.VERDE, Color.WHITE);
        botaoTocarPlaylist.addActionListener(e -> iniciarPlaylist());

        JPanel botoesPlaylist = Tema.painel(new GridLayout(2, 1, 0, 8));
        botoesPlaylist.add(linhaArquivos);
        botoesPlaylist.add(botaoTocarPlaylist);

        lateral.add(topo, BorderLayout.NORTH);
        lateral.add(Tema.rolagem(jListPlaylist, new Color(0, 0, 0, 0)), BorderLayout.CENTER);
        lateral.add(botoesPlaylist, BorderLayout.SOUTH);
        return lateral;
    }

    //Monta o painel central com os botoes de arquivo TXT e a caixa de texto expansivel
    private JPanel criarPainelCentral() {
        JPanel barraTopo = Tema.painel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        JButton botaoUpload = Tema.botao("Abrir TXT", Tema.CARD_CLARO, Tema.TEXTO);
        JButton botaoSalvar = Tema.botao("Salvar", Tema.CARD_CLARO, Tema.TEXTO);
        JButton botaoSalvarComo = Tema.botao("Salvar Como", Tema.CARD_CLARO, Tema.TEXTO);
        JButton botaoExportarMidi = Tema.botao("Exportar MIDI", Tema.CARD_CLARO, Tema.TEXTO);

        botaoExportarMidi.addActionListener(e -> exportarMidi());
        botaoUpload.addActionListener(e -> carregarArquivo());
        botaoSalvar.addActionListener(e -> salvarArquivo(false));
        botaoSalvarComo.addActionListener(e -> salvarArquivo(true));

        barraTopo.add(botaoUpload);
        barraTopo.add(botaoSalvar);
        barraTopo.add(botaoSalvarComo);
        barraTopo.add(botaoExportarMidi);

        JLabel tituloCentral = Tema.rotulo("Digite aqui a sua música", Tema.FONTE_GRANDE, Tema.TEXTO);
        tituloCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloCentral.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel painelFechado = Tema.painel(new BorderLayout());
        painelFechado.add(Tema.rotulo("Clique para expandir a caixa", Tema.FONTE, Tema.TEXTO_SECUNDARIO), BorderLayout.NORTH);
        painelFechado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        painelFechado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                expandirCaixa();
            }
        });

        caixaMusica.add(painelFechado, MODO_FECHADO);
        caixaMusica.add(Tema.rolagem(areaTexto, Tema.EDITOR), MODO_EXPANDIDO);
        caixaMusica.setPreferredSize(new Dimension(520, 74));
        caixaMusica.setMaximumSize(new Dimension(520, 74));
        caixaMusica.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel blocoMeio = Tema.painel(null);
        blocoMeio.setLayout(new BoxLayout(blocoMeio, BoxLayout.Y_AXIS));
        blocoMeio.add(tituloCentral);
        blocoMeio.add(Box.createVerticalStrut(16));
        blocoMeio.add(caixaMusica);

        JPanel centro = Tema.painel(new BorderLayout(0, 12));
        centro.add(barraTopo, BorderLayout.NORTH);
        centro.add(Tema.centralizado(blocoMeio), BorderLayout.CENTER);
        return centro;
    }

    //Expande a caixa central mantendo o editor no mesmo lugar da tela
    private void expandirCaixa() {
        if (!caixaExpandida) {
            caixaExpandida = true;
            layoutCaixa.show(caixaMusica, MODO_EXPANDIDO);
            Dimension tam = new Dimension(580, 220);
            caixaMusica.setPreferredSize(tam);
            caixaMusica.setMaximumSize(tam);
            caixaMusica.revalidate();
            painelOceano.revalidate();
            painelOceano.repaint();
        }
        areaTexto.requestFocusInWindow();
    }

    //Monta a barra inferior com selecao de instrumento, botoes de reproducao e sliders
    private JPanel criarBarraPlayer() {
        Cartao barra = new Cartao(new BorderLayout(16, 0));
        barra.setBorder(new EmptyBorder(10, 16, 10, 16));

        JPanel esquerda = Tema.painel(new BorderLayout(0, 6));
        esquerda.setPreferredSize(new Dimension(260, 56));
        esquerda.add(Tema.rotulo("Selecione instrumento inicial:", Tema.FONTE_NEGRITO, Tema.TEXTO), BorderLayout.NORTH);
        esquerda.add(comboInstrumentos, BorderLayout.SOUTH);

        JButton botaoAnterior = Tema.botaoIcone("⏮");
        JButton botaoProximo = Tema.botaoIcone("⏭");

        botaoAleatorio.addActionListener(e -> playlistAtual.setAleatorio(botaoAleatorio.isSelected()));
        botaoLoop.addActionListener(e -> playlistAtual.setLoop(botaoLoop.isSelected()));
        botaoAnterior.addActionListener(e -> anteriorMusica());
        botaoPlayPause.addActionListener(e -> alternarPlayPause());
        botaoParar.addActionListener(e -> parar());
        botaoProximo.addActionListener(e -> proximaMusica());

        JPanel botoesTransporte = Tema.painel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        botoesTransporte.add(botaoAleatorio);
        botoesTransporte.add(botaoAnterior);
        botoesTransporte.add(botaoPlayPause);
        botoesTransporte.add(botaoParar);
        botoesTransporte.add(botaoProximo);
        botoesTransporte.add(botaoLoop);

        JPanel centro = Tema.painel(new BorderLayout(0, 4));
        centro.add(botoesTransporte, BorderLayout.NORTH);
        centro.add(Tema.centralizado(barraProgresso), BorderLayout.SOUTH);

        JPanel direita = Tema.painel(new GridLayout(3, 1, 0, 2));
        direita.setPreferredSize(new Dimension(260, 56));
        direita.add(criarControleSlider("Oitava", sliderOitava));
        direita.add(criarControleSlider("BPM", sliderBpm));
        direita.add(criarControleSlider("Volume", sliderVolume));

        barra.add(esquerda, BorderLayout.WEST);
        barra.add(centro, BorderLayout.CENTER);
        barra.add(direita, BorderLayout.EAST);
        return barra;
    }

    private JComponent criarControleSlider(String nome, JSlider slider) {
        JPanel linha = Tema.painel(new BorderLayout(6, 0));
        JLabel rotulo = Tema.rotulo(nome + ": " + slider.getValue(), Tema.FONTE_PEQUENA, Tema.TEXTO_SECUNDARIO);
        rotulo.setPreferredSize(new Dimension(78, 18));
        slider.addChangeListener(e -> rotulo.setText(nome + ": " + slider.getValue()));
        linha.add(rotulo, BorderLayout.WEST);
        linha.add(slider, BorderLayout.CENTER);
        return linha;
    }

    //Atualiza periodicamente a barra de progresso enquanto a musica estiver tocando
    private void iniciarMonitorProgresso() {
        new Timer(80, e -> {
            ManagedPlayer mp = player.getManagedPlayer();
            if (mp.isPlaying()) {
                atualizarBpmEmTempoReal();
                long total = mp.getTickLength();
                long atual = mp.getTickPosition();
                double prog = total > 0 ? (double) atual / total : 0;
                barraProgresso.setProgresso(prog);
            } else if (!mp.isPaused()) {
                barraProgresso.setProgresso(0);
            }
        }).start();
    }

    private void adicionarPlaylistAoAlbum() {
        if (album.atingiuLimite()) {
            JOptionPane.showMessageDialog(this, "O álbum já atingiu o limite máximo de " + Album.LIMITE_MAXIMO + " playlists.");
            return;
        }
        String nome = JOptionPane.showInputDialog(this, "Nome da nova playlist:", "Playlist " + (album.tamanho() + 1));
        if (nome != null && !nome.trim().isEmpty()) {
            ListaPlaylist nova = new ListaPlaylist(nome.trim());
            if (album.adicionar(nova)) {
                comboPlaylists.addItem(nova.getNome());
                comboPlaylists.setSelectedIndex(comboPlaylists.getItemCount() - 1);
            }
        }
    }

    private void alternarPlaylistSelecionada() {
        int idx = comboPlaylists.getSelectedIndex();
        if (idx >= 0 && idx < album.tamanho()) {
            playlistAtual = album.getPlaylist(idx);
            atualizarVisualListaMusicas();
        }
    }

    private void atualizarVisualListaMusicas() {
        modeloLista.clear();
        if (playlistAtual != null) {
            for (File f : playlistAtual.getArquivos()) {
                modeloLista.addElement(f.getName());
            }
            botaoAleatorio.setSelected(playlistAtual.isAleatorio());
            botaoLoop.setSelected(playlistAtual.isLoop());
        }
    }

    private void definirEstadoBotaoPlay(boolean tocando) {
        botaoPlayPause.setActionCommand(tocando ? "PAUSAR" : "TOCAR");
        botaoPlayPause.repaint();
    }

    private void alternarPlayPause() {
        ManagedPlayer mp = player.getManagedPlayer();

        if (mp.isPlaying()) {
            mp.pause();
            definirEstadoBotaoPlay(false);
        } else if (mp.isPaused() && !mp.isFinished()) {
            // Só dá resume se estiver pausado E se o player ainda estiver "vivo"
            mp.resume();
            definirEstadoBotaoPlay(true);
        } else {
            // Se não está tocando, não está pausado ou já foi finalizado, começa do zero
            tocarTextoAtual();
        }
    }

    private void parar() {
        tocandoPlaylist = false;
        player.getManagedPlayer().finish();

        // ATUALIZAÇÃO: Joga o player velho fora imediatamente ao parar!
        // Assim o isPaused() dele não vai enganar o próximo Play.
        this.player = new Player();

        definirEstadoBotaoPlay(false);
        barraProgresso.setProgresso(0);
    }

    private int getInstrumentoSelecionado() {
        Instrumento inst = (Instrumento) comboInstrumentos.getSelectedItem();
        return (inst != null) ? inst.getIdMidi() : 1;
    }

    //Ajusta o fator de velocidade do Sequencer MIDI caso o usuario mude o slider de BPM durante a musica
    private void atualizarBpmEmTempoReal() {
        try {
            ManagedPlayer mp = player.getManagedPlayer();
            if (mp.isPlaying() || mp.isPaused()) {
                float fator = (float) sliderBpm.getValue() / Math.max(1, bpmBaseExecucao);
                SequencerManager.getInstance().getSequencer().setTempoFactor(fator);
            }
        } catch (Exception e) {
        }
    }

    private void resetarFatorTempo(int bpmInicial) {
        bpmBaseExecucao = bpmInicial;
        try {
            SequencerManager.getInstance().getSequencer().setTempoFactor(1.0f);
        } catch (Exception e) {
        }
    }

    //Traduz o texto da caixa e inicia a reproducao em uma Thread separada para nao travar a interface
    private void tocarTextoAtual() {
        String texto = areaTexto.getText();
        if (texto.trim().isEmpty()) {
            expandirCaixa();
            JOptionPane.showMessageDialog(this, "Digite ou abra um arquivo .txt antes de tocar.");
            return;
        }
        parar();

        int bpm = sliderBpm.getValue();
        int oitava = sliderOitava.getValue();
        int volume = sliderVolume.getValue();
        int instrumento = getInstrumentoSelecionado();

        try {
            String musica = tradutor.traduzir(texto, bpm, instrumento, volume, oitava);
            resetarFatorTempo(bpm);
            definirEstadoBotaoPlay(true);

            // ATUALIZAÇÃO AQUI: Recria o player para garantir sincronia
            this.player = new Player();

            new Thread(() -> {
                this.player.play(musica);
                SwingUtilities.invokeLater(() -> {
                    if (!tocandoPlaylist) {
                        definirEstadoBotaoPlay(false);
                        barraProgresso.setProgresso(0); // Garante que zera ao final
                    }
                });
            }).start();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }


    private void exportarMidi() {
        String texto = areaTexto.getText();

        if (texto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Digite ou abra uma música antes de exportar."
            );
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Exportar música para MIDI");
        chooser.setFileFilter(
                new FileNameExtensionFilter("Arquivo MIDI (*.mid)", "mid")
        );

        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File arquivo = chooser.getSelectedFile();

        if (!arquivo.getName().toLowerCase().endsWith(".mid")) {
            arquivo = new File(arquivo.getAbsolutePath() + ".mid");
        }

        try {
            int bpm = sliderBpm.getValue();
            int oitava = sliderOitava.getValue();
            int volume = sliderVolume.getValue();
            int instrumento = getInstrumentoSelecionado();

            String musica = tradutor.traduzir(
                    texto, bpm, instrumento, volume, oitava
            );

            Pattern pattern = new Pattern(musica);
            MidiFileManager.savePatternToMidi(pattern, arquivo);

            JOptionPane.showMessageDialog(
                    this,
                    "MIDI exportado com sucesso!\n" + arquivo.getAbsolutePath()
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao exportar MIDI: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    //Reproduz em sequencia os arquivos da playlist selecionada
    private void iniciarPlaylist() {
        if (playlistAtual == null || playlistAtual.estaVazia()) {
            JOptionPane.showMessageDialog(this, "A playlist selecionada está vazia. Adicione músicas primeiro.");
            return;
        }
        parar();
        expandirCaixa();

        int selecionado = jListPlaylist.getSelectedIndex();
        if (selecionado >= 0) {
            playlistAtual.setIndiceAtual(selecionado);
        }

        tocandoPlaylist = true;
        definirEstadoBotaoPlay(true);

        new Thread(() -> {
            File arquivo = playlistAtual.getArquivoAtual();
            while (tocandoPlaylist && arquivo != null) {
                int idx = playlistAtual.getIndiceAtual();
                File arquivoDaVez = arquivo;
                try {
                    String texto = LeitorTxt.carregar(arquivoDaVez);
                    SwingUtilities.invokeLater(() -> {
                        jListPlaylist.setSelectedIndex(idx);
                        areaTexto.setText(texto);
                        arquivoAtual = arquivoDaVez;
                    });
                    int bpm = sliderBpm.getValue();
                    int oitava = sliderOitava.getValue();
                    int volume = sliderVolume.getValue();
                    int instrumento = getInstrumentoSelecionado();
                    String musica = tradutor.traduzir(texto, bpm, instrumento, volume, oitava);
                    resetarFatorTempo(bpm);

                    // ATUALIZAÇÃO AQUI: Recria o player a cada nova música da playlist
                    this.player = new Player();

                    this.player.play(musica);
                } catch (Exception ex) {
                    System.err.println("Erro ao tocar: " + arquivoDaVez.getName());
                }

                if (!tocandoPlaylist) {
                    break;
                }
                arquivo = playlistAtual.proxima();
            }

            tocandoPlaylist = false;
            SwingUtilities.invokeLater(() -> {
                definirEstadoBotaoPlay(false);
                barraProgresso.setProgresso(0);
            });
        }).start();
    }

    private void proximaMusica() {
        if (playlistAtual == null || playlistAtual.estaVazia()) {
            return;
        }
        if (!tocandoPlaylist) {
            File f = playlistAtual.proxima();
            if (f != null) {
                jListPlaylist.setSelectedIndex(playlistAtual.getIndiceAtual());
            }
            return;
        }

        // Força a parada imediata do som no motor do Java e finaliza o player
        try {
            SequencerManager.getInstance().getSequencer().stop();
            player.getManagedPlayer().finish();
        } catch (Exception e) {
            System.err.println("Erro ao pular a música: " + e.getMessage());
        }
    }

    private void anteriorMusica() {
        if (playlistAtual == null || playlistAtual.estaVazia()) {
            return;
        }
        if (!tocandoPlaylist) {
            File f = playlistAtual.anterior();
            if (f != null) {
                jListPlaylist.setSelectedIndex(playlistAtual.getIndiceAtual());
            }
            return;
        }

        playlistAtual.recuarParaAnteriorDuranteReproducao();

        // Força a parada imediata do som no motor do Java e finaliza o player
        try {
            SequencerManager.getInstance().getSequencer().stop();
            player.getManagedPlayer().finish();
        } catch (Exception e) {
            System.err.println("Erro ao voltar a música: " + e.getMessage());
        }
    }

    private void adicionarNaPlaylist() {
        if (playlistAtual == null) {
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileFilter(new FileNameExtensionFilter("Arquivos de Texto (*.txt)", "txt"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            for (File f : chooser.getSelectedFiles()) {
                if (playlistAtual.atingiuLimite()) {
                    JOptionPane.showMessageDialog(this, "A playlist atingiu o limite máximo de "
                            + ListaPlaylist.LIMITE_MAXIMO + " músicas.");
                    break;
                }
                if (playlistAtual.adicionar(f)) {
                    modeloLista.addElement(f.getName());
                }
            }
        }
    }

    private void removerDaPlaylist() {
        if (playlistAtual == null) {
            return;
        }
        int indice = jListPlaylist.getSelectedIndex();
        if (indice >= 0) {
            playlistAtual.remover(indice);
            modeloLista.remove(indice);
        }
    }

    private void selecionarMusicaPlaylist() {
        if (playlistAtual == null) {
            return;
        }
        int indice = jListPlaylist.getSelectedIndex();
        if (indice >= 0 && indice < playlistAtual.tamanho()) {
            File f = playlistAtual.getArquivos().get(indice);
            try {
                expandirCaixa();
                areaTexto.setText(LeitorTxt.carregar(f));
                this.arquivoAtual = f;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarArquivo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Arquivos de Texto (*.txt)", "txt"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selecionado = chooser.getSelectedFile();
            try {
                expandirCaixa();
                areaTexto.setText(LeitorTxt.carregar(selecionado));
                this.arquivoAtual = selecionado;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao abrir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void salvarArquivo(boolean forcarNovoArquivo) {
        if (arquivoAtual == null || forcarNovoArquivo) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Arquivos de Texto (*.txt)", "txt"));

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selecionado = chooser.getSelectedFile();
                if (!selecionado.getName().toLowerCase().endsWith(".txt")) {
                    selecionado = new File(selecionado.getAbsolutePath() + ".txt");
                }
                this.arquivoAtual = selecionado;
            } else {
                return;
            }
        }

        try {
            LeitorTxt.salvar(arquivoAtual, areaTexto.getText());
            JOptionPane.showMessageDialog(this, "Arquivo salvo com sucesso!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
