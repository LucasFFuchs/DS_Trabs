public class Main {

    public static void main(String []args){

        System.out.println("teste");
        Nota Do = new Nota('C', "Do");

        System.out.println("Nome: "+ Do.nome);
        System.out.println("Simbolo: "+ Do.simbolo);

        Nota Re = new Nota('D', "Re");
        System.out.println("Nome: "+ Re.nome);
        System.out.println("Simbolo: "+ Re.simbolo);

        Nota Mi = new Nota('E', "Mi");
        Nota Fa = new Nota('F', "Fa");
        Nota Sol = new Nota('G', "Sol");
        Nota La = new Nota('A', "La");
        Nota Si = new Nota('B', "Si");

        Mi.TocaNota();



    }
    
}
