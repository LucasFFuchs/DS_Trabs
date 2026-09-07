public class Letra_minuscula extends Caractere {
    private Caractere CaractereBase;

    public Letra_minuscula(Caractere CaractereBase){
        this.CaractereBase = CaractereBase;
        this.setNome(CaractereBase.getNome().toLowerCase());
        this.setSimbolo(Character.toLowerCase(CaractereBase.getSimbolo()));
    }
    public Caractere getCaractereBase(){
        return CaractereBase;
    }

}
