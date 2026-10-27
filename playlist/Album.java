package playlist;

import java.util.ArrayList;
import java.util.List;

// Um album reune playlists e limita quantas podem ser adicionadas.
public class Album {

    public static final int LIMITE_MAXIMO = 3;

    private String nome;
    private final List<ListaPlaylist> playlists = new ArrayList<>();

    public Album() {
        this("Novo Álbum");
    }

    public Album(String nome) {
        this.nome = nome;
    }

    public boolean adicionar(ListaPlaylist playlist) {
        // Recusa playlists repetidas, nulas ou alem do limite do album.
        if (playlist != null && !playlists.contains(playlist) && playlists.size() < LIMITE_MAXIMO) {
            playlists.add(playlist);
            return true;
        }
        return false;
    }

    public boolean remover(int indice) {
        if (indice >= 0 && indice < playlists.size()) {
            playlists.remove(indice);
            return true;
        }
        return false;
    }

    public boolean remover(ListaPlaylist playlist) {
        return playlists.remove(playlist);
    }

    public ListaPlaylist getPlaylist(int indice) {
        if (indice >= 0 && indice < playlists.size()) {
            return playlists.get(indice);
        }
        return null;
    }

    public List<ListaPlaylist> getPlaylists() {
        return playlists;
    }

    public int tamanho() {
        return playlists.size();
    }

    public boolean atingiuLimite() {
        return playlists.size() >= LIMITE_MAXIMO;
    }

    public boolean estaVazio() {
        return playlists.isEmpty();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
