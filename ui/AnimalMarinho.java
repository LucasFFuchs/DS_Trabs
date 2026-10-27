package ui;

//Armazena a posicao, velocidade e escala de um animal que nada continuamente pela tela
public class AnimalMarinho {

    public double x;
    public double y;
    public double vx;
    public double vy;
    public double escala;
    public double fase;

    public AnimalMarinho(double x, double y, double vx, double vy, double escala, double fase) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.escala = escala;
        this.fase = fase;
    }

    //Atualiza a posicao do animal e faz ele reaparecer do lado oposto quando sai da tela
    public void mover(int maxC, int maxR, int margem) {
        x = envolver(x + vx, -margem, maxC + margem);
        y = envolver(y + vy, -margem, maxR + margem);
    }

    private double envolver(double valor, double min, double max) {
        double amplitude = max - min;
        if (valor < min) {
            return valor + amplitude;
        }
        if (valor > max) {
            return valor - amplitude;
        }
        return valor;
    }
}
