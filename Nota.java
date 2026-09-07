public class Nota extends Letra_maiuscula {
    
    char simbolo;
    String nome;
    Letra_maiuscula Letra;



    Nota(char simbolo, String nome, Letra_maiuscula l_simbolo){
        this.simbolo = simbolo;
        this.nome = nome;
        this.Letra = new Letra_maiuscula(l_simbolo);
    }
    void TocaNota(){
        System.out.println("sooommmm");
    }
}
