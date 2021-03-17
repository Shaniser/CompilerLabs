class Token(
    val tag: Tag,
    val coords: Coords
) {
    constructor(tag: Tag, start: Position, end: Position) : this(tag, Coords(start, end))

    @Override
    override fun toString(): String {
        return coords.toString()
    }
}