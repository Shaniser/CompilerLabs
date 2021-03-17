public class Coords {
    private final int line;
    private final int symbol;

    public Coords(int line, int symbol) {
        this.line = line;
        this.symbol = symbol;
    }

    public int getSymbol() {
        return symbol;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "(Line: " + line + ", symbol: " + symbol + ")";
    }
}
