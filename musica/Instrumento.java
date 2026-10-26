package musica;

public class Instrumento {

    private final int idMidi;
    private final String nome;

    public Instrumento(int idMidi, String nome) {
        this.idMidi = idMidi;
        this.nome = nome;
    }

    public int getIdMidi() {
        return idMidi;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome + " (GM #" + idMidi + ")";
    }

    public static Instrumento[] getCatalogo() {
        return new Instrumento[] {
            new Instrumento(1, "Piano Acústico"),
            new Instrumento(7, "Cravo"),
            new Instrumento(15, "Tubular Bells"),
            new Instrumento(20, "Órgão"),
            new Instrumento(22, "Acordeom"),
            new Instrumento(24, "Bandoneon"),
            new Instrumento(25, "Violão Nylon"),
            new Instrumento(26, "Violão Aço"),
            new Instrumento(27, "Guitarra Jazz"),
            new Instrumento(30, "Guitarra Distorcida"),
            new Instrumento(33, "Baixo Acústico"),
            new Instrumento(34, "Baixo Elétrico"),
            new Instrumento(41, "Violino"),
            new Instrumento(42, "Viola"),
            new Instrumento(43, "Violoncelo"),
            new Instrumento(44, "Contrabaixo"),
            new Instrumento(47, "Harpa"),
            new Instrumento(49, "Cordas"),
            new Instrumento(57, "Trompete"),
            new Instrumento(58, "Trombone"),
            new Instrumento(59, "Tuba"),
            new Instrumento(61, "Trompa"),
            new Instrumento(65, "Saxofone Soprano"),
            new Instrumento(66, "Saxofone Alto"),
            new Instrumento(71, "Fagote"),
            new Instrumento(72, "Clarinete"),
            new Instrumento(74, "Flauta"),
            new Instrumento(110, "Gaita de Foles"),
            new Instrumento(114, "Agogô"),
            new Instrumento(123, "Ondas do Mar")
        };
    }
}
