import org.jfugue.player.Player;
public class TesteJFugue {
    public static void main(String []args) {
        Player player = new Player();
        System.out.println("Tocando...");
        player.play(
    "V0 I[Violin] " +
    "C5q E5q G5h B4q G5q E5h " +

    "V1 I[String_Ensemble_1] " +
    "C3w G2w A2w F2w"
);
    }
}
