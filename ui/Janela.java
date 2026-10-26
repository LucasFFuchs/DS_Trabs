package ui;

import io.LeitorTxt;
import musica.Instrumento;
import musica.Tradutor;
import org.jfugue.player.Player;
import playlist.Album;
import playlist.ListaPlaylist;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

public class Janela extends JFrame {

    private final JTextArea areaTexto = new JTextArea(12, 45);
    private final JComboBox<Instrumento> comboInstrumentos = new JComboBox<>(Instrumento.getCatalogo());
    private final JSpinner campoOitava = new JSpinner(new SpinnerNumberModel(6, 0, 9, 1));
    private final JSpinner campoVolume = new JSpinner(new SpinnerNumberModel(100, 0, 127, 5));
    private final JSpinner campoBpm = new JSpinner(new SpinnerNumberModel(120, 20, 300, 10));
    private final JLabel labelArquivo = new JLabel("Nenhum arquivo aberto");
    private final Tradutor tradutor;

    private final Player player = new Player();
    private final JButton botaoPlayPause = new JButton("Tocar");
    private final JButton botaoParar = new JButton("Parar");

    private final Album album = new Album("Meu Álbum");
    private ListaPlaylist playlistAtual;
    private final JComboBox<String> comboPlaylists = new JComboBox<>();

    private final DefaultListModel<String> modeloLista = new DefaultListModel<>();
    private final JList<String> jListPlaylist = new JList<>(modeloLista);

    private final JToggleButton botaoAleatorio = new JToggleButton("🔀 Aleatório");
    private final JToggleButton botaoLoop = new JToggleButton("🔁 Loop");

    private boolean tocandoPlaylist = false;
    private File arquivoAtual = null;

    public Janela(Tradutor tradutor) {
        super("Gerador de música");
        this.tradutor = tradutor;

        ListaPlaylist inicial = new ListaPlaylist("Playlist 1");
        album.adicionar(inicial);
        playlistAtual = inicial;
        comboPlaylists.addItem(inicial.getNome());
        comboPlaylists.addActionListener(e -> alternarPlaylistSelecionada());

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton botaoUpload = new JButton("Upload TXT");
        JButton botaoSalvar = new JButton("Salvar TXT");
        JButton botaoSalvarComo = new JButton("Salvar Como...");

        botaoUpload.addActionListener(e -> carregarArquivo());
        botaoSalvar.addActionListener(e -> salvarArquivo(false));
        botaoSalvarComo.addActionListener(e -> salvarArquivo(true));

        painelTopo.add(botaoUpload);
        painelTopo.add(botaoSalvar);
        painelTopo.add(botaoSalvarComo);
        painelTopo.add(labelArquivo);

        JPanel painelLateral = new JPanel(new BorderLayout(5, 5));
        painelLateral.setPreferredSize(new Dimension(240, 0));

        JPanel topoPlaylist = new JPanel(new BorderLayout(2, 2));
        JPanel linhaAlbum = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 2));
        linhaAlbum.add(new JLabel("Álbum:"));
        comboPlaylists.setPreferredSize(new Dimension(110, 24));
        linhaAlbum.add(comboPlaylists);

        JButton botaoAddPL = new JButton("+");
        botaoAddPL.setToolTipText("Criar nova playlist no álbum (máx. 3)");
        botaoAddPL.addActionListener(e -> adicionarPlaylistAoAlbum());
        linhaAlbum.add(botaoAddPL);

        topoPlaylist.add(linhaAlbum, BorderLayout.NORTH);
        topoPlaylist.add(new JLabel("  Músicas (máx. 10):"), BorderLayout.SOUTH);

        jListPlaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListPlaylist.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selecionarMusicaPlaylist();
            }
        });

        JPanel painelControlesPlaylist = new JPanel(new GridLayout(3, 1, 2, 2));

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 2));
        JButton botaoAdd = new JButton("+ Adicionar");
        JButton botaoRemover = new JButton("- Remover");
        botaoAdd.addActionListener(e -> adicionarNaPlaylist());
        botaoRemover.addActionListener(e -> removerDaPlaylist());
        linha1.add(botaoAdd);
        linha1.add(botaoRemover);

        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 2));
        botaoAleatorio.addActionListener(e -> {
            if (playlistAtual != null) {
                playlistAtual.setAleatorio(botaoAleatorio.isSelected());
            }
        });
        botaoLoop.addActionListener(e -> {
            if (playlistAtual != null) {
                playlistAtual.setLoop(botaoLoop.isSelected());
            }
        });
        linha2.add(botaoAleatorio);
        linha2.add(botaoLoop);

        JPanel linha3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 2));
        JButton botaoAnterior = new JButton("⏮");
        JButton botaoTocarPlaylist = new JButton("▶ Playlist");
        JButton botaoProximo = new JButton("⏭");

        botaoAnterior.addActionListener(e -> anteriorMusica());
        botaoTocarPlaylist.addActionListener(e -> iniciarPlaylist());
        botaoProximo.addActionListener(e -> proximaMusica());

        linha3.add(botaoAnterior);
        linha3.add(botaoTocarPlaylist);
        linha3.add(botaoProximo);

        painelControlesPlaylist.add(linha1);
        painelControlesPlaylist.add(linha2);
        painelControlesPlaylist.add(linha3);

        painelLateral.add(topoPlaylist, BorderLayout.NORTH);
        painelLateral.add(new JScrollPane(jListPlaylist), BorderLayout.CENTER);
        painelLateral.add(painelControlesPlaylist, BorderLayout.SOUTH);

        botaoPlayPause.addActionListener(e -> alternarPlayPause());
        botaoParar.addActionListener(e -> parar());

        JPanel painelBaixo = new JPanel(new GridLayout(2, 1, 2, 2));

        JPanel linhaParametros = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 2));
        linhaParametros.add(new JLabel("Instrumento:"));
        linhaParametros.add(comboInstrumentos);
        linhaParametros.add(new JLabel("Oitava:"));
        linhaParametros.add(campoOitava);
        linhaParametros.add(new JLabel("Volume:"));
        linhaParametros.add(campoVolume);
        linhaParametros.add(new JLabel("BPM:"));
        linhaParametros.add(campoBpm);

        JPanel linhaBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 2));
        linhaBotoes.add(botaoPlayPause);
        linhaBotoes.add(botaoParar);

        painelBaixo.add(linhaParametros);
        painelBaixo.add(linhaBotoes);

        add(painelTopo, BorderLayout.NORTH);
        add(painelLateral, BorderLayout.WEST);
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);
        add(painelBaixo, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void adicionarPlaylistAoAlbum() {
        if (album.atingiuLimite()) {
            JOptionPane.showMessageDialog(this, "O álbum já atingiu o limite máximo de " + Album.LIMITE_MAXIMO + " playlists.");
            return;
        }
        String nome = JOptionPane.showInputDialog(this, "Nome da nova playlist:", "Playlist " + (album.tamanho() + 1));
        if (nome != null && !nome.isBlank()) {
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

    private void alternarPlayPause() {
        if (player.getManagedPlayer().isPlaying()) {
            player.getManagedPlayer().pause();
            botaoPlayPause.setText("Continuar");
        } else if (player.getManagedPlayer().isPaused()) {
            player.getManagedPlayer().resume();
            botaoPlayPause.setText("Pausar");
        } else {
            tocarTextoAtual();
        }
    }

    private void parar() {
        tocandoPlaylist = false;
        player.getManagedPlayer().finish();
        botaoPlayPause.setText("Tocar");
    }

    private int getInstrumentoSelecionado() {
        Instrumento inst = (Instrumento) comboInstrumentos.getSelectedItem();
        return (inst != null) ? inst.getIdMidi() : 1;
    }

    private void tocarTextoAtual() {
        String texto = areaTexto.getText();
        if (texto.isBlank()) {
            JOptionPane.showMessageDialog(this, "Digite um texto antes.");
            return;
        }
        parar();

        int bpm = (int) campoBpm.getValue();
        int oitava = (int) campoOitava.getValue();
        int volume = (int) campoVolume.getValue();
        int instrumento = getInstrumentoSelecionado();

        try {
            String musica = tradutor.traduzir(texto, bpm, instrumento, volume, oitava);
            botaoPlayPause.setText("Pausar");
            new Thread(() -> {
                player.play(musica);
                SwingUtilities.invokeLater(() -> botaoPlayPause.setText("Tocar"));
            }).start();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void iniciarPlaylist() {
        if (playlistAtual == null || playlistAtual.estaVazia()) {
            JOptionPane.showMessageDialog(this, "A playlist selecionada está vazia. Adicione músicas primeiro.");
            return;
        }
        parar();

        int selecionado = jListPlaylist.getSelectedIndex();
        if (selecionado >= 0) {
            playlistAtual.setIndiceAtual(selecionado);
        }

        tocandoPlaylist = true;
        botaoPlayPause.setText("Pausar");

        int bpm = (int) campoBpm.getValue();
        int oitava = (int) campoOitava.getValue();
        int volume = (int) campoVolume.getValue();
        int instrumento = getInstrumentoSelecionado();

        new Thread(() -> {
            File arquivo = playlistAtual.getArquivoAtual();
            while (tocandoPlaylist && arquivo != null) {
                int idx = playlistAtual.getIndiceAtual();
                SwingUtilities.invokeLater(() -> jListPlaylist.setSelectedIndex(idx));

                try {
                    String texto = LeitorTxt.carregar(arquivo);
                    String musica = tradutor.traduzir(texto, bpm, instrumento, volume, oitava);
                    player.play(musica);
                } catch (Exception ex) {
                    System.err.println("Erro ao tocar: " + arquivo.getName());
                }

                if (!tocandoPlaylist) {
                    break;
                }
                arquivo = playlistAtual.proxima();
            }

            tocandoPlaylist = false;
            SwingUtilities.invokeLater(() -> botaoPlayPause.setText("Tocar"));
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
        player.getManagedPlayer().finish();
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
        player.getManagedPlayer().finish();
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
                areaTexto.setText(LeitorTxt.carregar(f));
                this.arquivoAtual = f;
                labelArquivo.setText("Arquivo: " + f.getName());
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
                areaTexto.setText(LeitorTxt.carregar(selecionado));
                this.arquivoAtual = selecionado;
                labelArquivo.setText("Arquivo: " + selecionado.getName());
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
            labelArquivo.setText("Arquivo: " + arquivoAtual.getName());
            JOptionPane.showMessageDialog(this, "Arquivo salvo com sucesso!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
