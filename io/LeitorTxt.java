package io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LeitorTxt {

    public static String carregar(File arquivo) throws IOException {
        return Files.readString(arquivo.toPath());
    }

    public static void salvar(File arquivo, String conteudo) throws IOException {
        Files.writeString(arquivo.toPath(), conteudo);
    }
}
