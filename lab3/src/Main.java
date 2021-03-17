import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        String program = Files.readString(Paths.get(args[0]), StandardCharsets.UTF_8);

        Lexer lexer = new Lexer(program);
        for (Lexem l : lexer.getLexems()) {
            System.out.println(l);
        }
    }
}
