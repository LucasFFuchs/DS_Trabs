public class Letra_maiuscula extends Caractere {
    private Caractere CaractereBase;

    public Letra_maiuscula(Caractere CaractereBase){
        this.CaractereBase = CaractereBase;
        super(CaractereBase.getNome().toUpperCase(), Character.toUpperCase(CaractereBase.getSimbolo());
    }
    public Caractere getCaractereBase(){
        return CaractereBase;
    }
}
