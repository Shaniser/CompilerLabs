class Coords(
    val start: Position,
    val end: Position
) {
    @Override
    override fun toString(): String {
        return "$start-$end"
    }
}