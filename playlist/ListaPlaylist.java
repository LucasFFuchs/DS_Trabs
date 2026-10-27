package playlist;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaPlaylist {

    public static final int LIMITE_MAXIMO = 10;

    private String nome = "Playlist";
    private final List<File> arquivos = new ArrayList<>();
    // Guarda a ordem de reprodução sem alterar a ordem original dos arquivos.
    private final List<Integer> ordem = new ArrayList<>();
    private int posicaoFila = 0;

    private boolean aleatorio = false;
    private boolean loop = false;

    public ListaPlaylist() {
        this("Playlist");
    }

    public ListaPlaylist(String nome) {
        this.nome = nome;
    }

    public boolean adicionar(File arquivo) {
        if (arquivo != null && !arquivos.contains(arquivo) && arquivos.size() < LIMITE_MAXIMO) {
            arquivos.add(arquivo);
            atualizarOrdem();
            return true;
        }
        return false;
    }

    public void remover(int indice) {
        if (indice >= 0 && indice < arquivos.size()) {
            arquivos.remove(indice);
            atualizarOrdem();
        }
    }

    public void atualizarOrdem() {
        if (arquivos.isEmpty()) {
            ordem.clear();
            posicaoFila = 0;
            return;
        }

        // Salva qual música está tocando agora para não perdê-la
        int indiceAtualReal = -1;
        if (posicaoFila >= 0 && posicaoFila < ordem.size()) {
            indiceAtualReal = ordem.get(posicaoFila);
        }

        ordem.clear();
        for (int i = 0; i < arquivos.size(); i++) {
            ordem.add(i);
        }

        if (aleatorio) {
            Collections.shuffle(ordem);
            // Mantém a música atual como a primeira da fila (para não reiniciar ou repetir do nada)
            if (indiceAtualReal != -1 && ordem.contains(indiceAtualReal)) {
                ordem.remove(Integer.valueOf(indiceAtualReal));
                ordem.add(0, indiceAtualReal);
            }
        }

        // Atualiza a posição na fila para continuar exatamente da música que estava tocando
        if (indiceAtualReal != -1 && ordem.contains(indiceAtualReal)) {
            for (int i = 0; i < ordem.size(); i++) {
                if (ordem.get(i) == indiceAtualReal) {
                    posicaoFila = i;
                    break;
                }
            }
        } else {
            posicaoFila = 0;
        }
    }

    public void setAleatorio(boolean aleatorio) {
        if (this.aleatorio != aleatorio) {
            this.aleatorio = aleatorio;
            atualizarOrdem();
        }
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean isAleatorio() {
        return aleatorio;
    }

    public boolean isLoop() {
        return loop;
    }

    public File getArquivoAtual() {
        if (arquivos.isEmpty() || posicaoFila < 0 || posicaoFila >= ordem.size()) {
            return null;
        }
        return arquivos.get(ordem.get(posicaoFila));
    }

    public int getIndiceAtual() {
        if (arquivos.isEmpty() || posicaoFila < 0 || posicaoFila >= ordem.size()) {
            return -1;
        }
        return ordem.get(posicaoFila);
    }

    public void setIndiceAtual(int indice) {
        for (int i = 0; i < ordem.size(); i++) {
            if (ordem.get(i) == indice) {
                posicaoFila = i;
                return;
            }
        }
    }

    public File proxima() {
        if (arquivos.isEmpty()) {
            return null;
        }
        posicaoFila++;
        if (posicaoFila >= ordem.size()) {
            if (loop) {
                if (aleatorio && arquivos.size() > 1) {
                    // Guarda a última música que tocou
                    int ultimoTocado = ordem.get(ordem.size() - 1);
                    Collections.shuffle(ordem);

                    // Se o embaralhamento colocou a mesma música no começo, embaralha de novo!
                    while (ordem.get(0) == ultimoTocado) {
                        Collections.shuffle(ordem);
                    }
                }
                posicaoFila = 0;
            } else {
                // Sem loop, marca a fila como concluída.
                posicaoFila = ordem.size();
                return null;
            }
        }
        return getArquivoAtual();
    }

    public File anterior() {
        if (arquivos.isEmpty()) {
            return null;
        }
        posicaoFila--;
        if (posicaoFila < 0) {
            posicaoFila = loop ? ordem.size() - 1 : 0;
        }
        return getArquivoAtual();
    }

    public void recuarParaAnteriorDuranteReproducao() {
        posicaoFila -= 2;
        if (posicaoFila < 0) {
            posicaoFila = loop ? (ordem.size() + (posicaoFila % ordem.size())) : -1;
        }
    }

    public boolean estaVazia() {
        return arquivos.isEmpty();
    }

    public int tamanho() {
        return arquivos.size();
    }

    public boolean atingiuLimite() {
        return arquivos.size() >= LIMITE_MAXIMO;
    }

    public List<File> getArquivos() {
        return arquivos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
