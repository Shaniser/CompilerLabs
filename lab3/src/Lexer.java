import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final ArrayList<Lexem> lexems = new ArrayList<>();
    private final String source;


    public Lexer(String source) {
        this.source = source;
        analyze();
    }

    private void analyze() {
        String tail = source;
        boolean isError = false;
        int pointerSymbol = 0;
        int pointerLine = 0;
        String regex_id = "(\\p{L})([\\p{L}|\\d]+)\\7";
        String regex_hexNum = "(\\d(\\d|[A-F])*)";
        String regex_keyWord = "(qeq|xxx|xx)";
        String regex_comment = "//[^\n]+";
        String regex_space = " ";
        String regex_newLine = "\n";

        String patternString =
                        "(?<keyWord>^" + regex_keyWord +
                        ")|(?<hexNum>^" + regex_hexNum +
                        ")|(?<id>^" + regex_id +
                        ")|(?<comment>^" + regex_comment +
                        ")|(?<newLine>^" + regex_newLine +
                        ")|(?<space>^" + regex_space + ")";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(tail);

        while (tail.length() != 0) {
            if (matcher.find()) {
                if (matcher.group("keyWord") != null) {
                    lexems.add(new Lexem("KEYWORD",
                            tail.substring(matcher.start(), matcher.end()),
                            new Coords(pointerLine, pointerSymbol)));
                    pointerSymbol += matcher.end();
                } else if (matcher.group("id") != null) {
                    lexems.add(new Lexem("ID",
                            tail.substring(matcher.start(), matcher.end()),
                            new Coords(pointerLine, pointerSymbol)));
                    pointerSymbol += matcher.end();
                } else if (matcher.group("hexNum") != null) {
                    lexems.add(new Lexem("HEX_NUMBER",
                            tail.substring(matcher.start(), matcher.end()),
                            new Coords(pointerLine, pointerSymbol)));
                    pointerSymbol += matcher.end();
                } else if (matcher.group("comment") != null) {
                    lexems.add(new Lexem("COMMENT",
                            tail.substring(matcher.start(), matcher.end()),
                            new Coords(pointerLine, pointerSymbol)));
                    pointerSymbol += matcher.end();
                } else if (matcher.group("space") != null) {
                    pointerSymbol += matcher.end();
                } else if (matcher.group("newLine") != null) {
                    pointerSymbol = 0;
                    pointerLine += 1;
                }

                tail = tail.substring(matcher.end());
                isError = false;
            } else {
                if (!isError) {
                    printError(new Coords(pointerLine, pointerSymbol));
                    isError = true;
                }
                tail = tail.substring(1);
                pointerSymbol += 1;
            }
            matcher = pattern.matcher(tail);
        }
    }

    private void printError(Coords pos) {
        var lines = source.split("\n");
        StringBuilder sb = new StringBuilder();
        sb.append(lines[pos.getLine()]);
        sb.append("\n");
        sb.append(" ".repeat(Math.max(0, pos.getSymbol())));
        sb.append("^");
        sb.append("\n");
        sb.append(" ".repeat(Math.max(0, pos.getSymbol())));
        sb.append("Syntax error").append(pos);
        sb.append("\n");

        System.out.println(sb.toString());
    }

    public String getSource() {
        return source;
    }

    public ArrayList<Lexem> getLexems() {
        return lexems;
    }
}
