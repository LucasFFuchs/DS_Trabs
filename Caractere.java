
public class Caractere {

    private String nome;
    private char simbolo;

    public Caractere(String nome, char simbolo){
        this.nome = nome;
        this.simbolo = simbolo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isEmpty()) {
            this.nome = nome;
        }
    }

    public char getSimbolo() {
        return simbolo;
    }
    public void setSimbolo(char simbolo){
        
            this.simbolo = simbolo;
    }
}
