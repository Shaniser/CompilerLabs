public class Lexem {
    private final String type;
    private final String text;
    private final Coords pos;

    public Lexem(String type, String text, Coords pos) {
        this.type = type;
        this.text = text;
        this.pos = pos;
    }

    public Coords getPos() {
        return pos;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + pos + ": " + text;
    }
}
