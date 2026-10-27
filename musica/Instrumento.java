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

    /*Quando o println eh chamado ele chama o toString automatico
      Ex: Instrumento piano = new Instrumento(1, Piano)
      System.out.println(piano)
      "Piano (GM #1)" */
    @Override
    public String toString() {
        return nome + " (GM #" + idMidi + ")";
    }

    //Faz um array com as instancias dos instrumentos desejados
    public static Instrumento[] getCatalogo() {
        return new Instrumento[] {
                // Pianos (1-8)
                new Instrumento(1, "Piano Acústico"),
                new Instrumento(2, "Piano Acústico Brilhante"),
                new Instrumento(3, "Piano Elétrico de Cauda"),
                new Instrumento(4, "Piano Honky-tonk"),
                new Instrumento(5, "Piano Elétrico 1"),
                new Instrumento(6, "Piano Elétrico 2"),
                new Instrumento(7, "Cravo"),
                new Instrumento(8, "Clavinete"),

                // Percussão Cromática (9-16)
                new Instrumento(9, "Celesta"),
                new Instrumento(10, "Glockenspiel"),
                new Instrumento(11, "Caixa de Música"),
                new Instrumento(12, "Vibrafone"),
                new Instrumento(13, "Marimba"),
                new Instrumento(14, "Xilofone"),
                new Instrumento(15, "Tubular Bells"),
                new Instrumento(16, "Dulcimer"),

                // Órgãos (17-24)
                new Instrumento(17, "Órgão Hammond"),
                new Instrumento(18, "Órgão Percussivo"),
                new Instrumento(19, "Órgão de Rock"),
                new Instrumento(20, "Órgão"),
                new Instrumento(21, "Harmônio"),
                new Instrumento(22, "Acordeom"),
                new Instrumento(23, "Gaita"),
                new Instrumento(24, "Bandoneon"),

                // Violões e Guitarras (25-32)
                new Instrumento(25, "Violão Nylon"),
                new Instrumento(26, "Violão Aço"),
                new Instrumento(27, "Guitarra Jazz"),
                new Instrumento(28, "Guitarra Limpa"),
                new Instrumento(29, "Guitarra Mutada"),
                new Instrumento(30, "Guitarra Distorcida"),
                new Instrumento(31, "Guitarra com Overdrive"),
                new Instrumento(32, "Harmônicos de Guitarra"),

                // Baixos (33-40)
                new Instrumento(33, "Baixo Acústico"),
                new Instrumento(34, "Baixo Elétrico"),
                new Instrumento(35, "Baixo Elétrico (Palheta)"),
                new Instrumento(36, "Baixo Fretless"),
                new Instrumento(37, "Baixo Slap 1"),
                new Instrumento(38, "Baixo Slap 2"),
                new Instrumento(39, "Baixo Sintético 1"),
                new Instrumento(40, "Baixo Sintético 2"),

                // Cordas Clássicas (41-48)
                new Instrumento(41, "Violino"),
                new Instrumento(42, "Viola"),
                new Instrumento(43, "Violoncelo"),
                new Instrumento(44, "Contrabaixo"),
                new Instrumento(45, "Cordas em Tremolo"),
                new Instrumento(46, "Cordas em Pizzicato"),
                new Instrumento(47, "Harpa"),
                new Instrumento(48, "Tímpano"),

                // Conjuntos e Corais (49-56)
                new Instrumento(49, "Cordas"),
                new Instrumento(50, "Cordas Lentas"),
                new Instrumento(51, "Vozes Sintéticas 1"),
                new Instrumento(52, "Vozes Sintéticas 2"),
                new Instrumento(53, "Coro Aah"),
                new Instrumento(54, "Voz Ooh"),
                new Instrumento(55, "Voz Sintética"),
                new Instrumento(56, "Orquestra Hit"),

                // Metais (57-64)
                new Instrumento(57, "Trompete"),
                new Instrumento(58, "Trombone"),
                new Instrumento(59, "Tuba"),
                new Instrumento(60, "Trompete com Surdina"),
                new Instrumento(61, "Trompa"),
                new Instrumento(62, "Seção de Metais"),
                new Instrumento(63, "Metais Sintéticos 1"),
                new Instrumento(64, "Metais Sintéticos 2"),

                // Madeiras / Palhetas (65-72)
                new Instrumento(65, "Saxofone Soprano"),
                new Instrumento(66, "Saxofone Alto"),
                new Instrumento(67, "Saxofone Tenor"),
                new Instrumento(68, "Saxofone Barítono"),
                new Instrumento(69, "Oboé"),
                new Instrumento(70, "Corne Inglês"),
                new Instrumento(71, "Fagote"),
                new Instrumento(72, "Clarinete"),

                // Flautas (73-80)
                new Instrumento(73, "Flautim"),
                new Instrumento(74, "Flauta"),
                new Instrumento(75, "Flauta Doce"),
                new Instrumento(76, "Flauta de Pã"),
                new Instrumento(77, "Garrafa Soprada"),
                new Instrumento(78, "Shakuhachi"),
                new Instrumento(79, "Assobio"),
                new Instrumento(80, "Ocarina"),

                // Sintetizadores - Leads (81-88)
                new Instrumento(81, "Sintetizador Lead 1 (Square)"),
                new Instrumento(82, "Sintetizador Lead 2 (Sawtooth)"),
                new Instrumento(83, "Sintetizador Lead 3 (Calliope)"),
                new Instrumento(84, "Sintetizador Lead 4 (Chiff)"),
                new Instrumento(85, "Sintetizador Lead 5 (Charang)"),
                new Instrumento(86, "Sintetizador Lead 6 (Voice)"),
                new Instrumento(87, "Sintetizador Lead 7 (Fifths)"),
                new Instrumento(88, "Sintetizador Lead 8 (Bass+Lead)"),

                // Sintetizadores - Pads (89-96)
                new Instrumento(89, "Sintetizador Pad 1 (New Age)"),
                new Instrumento(90, "Sintetizador Pad 2 (Warm)"),
                new Instrumento(91, "Sintetizador Pad 3 (Polysynth)"),
                new Instrumento(92, "Sintetizador Pad 4 (Choir)"),
                new Instrumento(93, "Sintetizador Pad 5 (Bowed)"),
                new Instrumento(94, "Sintetizador Pad 6 (Metallic)"),
                new Instrumento(95, "Sintetizador Pad 7 (Halo)"),
                new Instrumento(96, "Sintetizador Pad 8 (Sweep)"),

                // Efeitos Sintéticos (97-104)
                new Instrumento(97, "Efeito Sintético 1 (Rain)"),
                new Instrumento(98, "Efeito Sintético 2 (Soundtrack)"),
                new Instrumento(99, "Efeito Sintético 3 (Crystal)"),
                new Instrumento(100, "Efeito Sintético 4 (Atmosphere)"),
                new Instrumento(101, "Efeito Sintético 5 (Brightness)"),
                new Instrumento(102, "Efeito Sintético 6 (Goblins)"),
                new Instrumento(103, "Efeito Sintético 7 (Echoes)"),
                new Instrumento(104, "Efeito Sintético 8 (Sci-Fi)"),

                // Étnicos (105-112)
                new Instrumento(105, "Sitar"),
                new Instrumento(106, "Banjo"),
                new Instrumento(107, "Shamisen"),
                new Instrumento(108, "Koto"),
                new Instrumento(109, "Kalimba"),
                new Instrumento(110, "Gaita de Foles"),
                new Instrumento(111, "Rabeca"),
                new Instrumento(112, "Shanai"),

                // Percussivos (113-120)
                new Instrumento(113, "Sino de Alerta"),
                new Instrumento(114, "Agogô"),
                new Instrumento(115, "Tambores de Aço"),
                new Instrumento(116, "Bloco de Madeira"),
                new Instrumento(117, "Tambor Taiko"),
                new Instrumento(118, "Tom Melódico"),
                new Instrumento(119, "Tambor Sintético"),
                new Instrumento(120, "Prato Reverso"),

                // Efeitos Sonoros (121-128)
                new Instrumento(121, "Ruído de Traste"),
                new Instrumento(122, "Respiração"),
                new Instrumento(123, "Ondas do Mar"),
                new Instrumento(124, "Canto de Pássaro"),
                new Instrumento(125, "Toque de Telefone"),
                new Instrumento(126, "Helicóptero"),
                new Instrumento(127, "Aplausos"),
                new Instrumento(128, "Tiro")

        };
    }
}
