open class Token(
    val tag: Tag,
    val coords: Coords,
    val value: String
) {
    constructor(tag: Tag, start: Position, end: Position, value: String) : this(tag, Coords(start, end), value)

    @Override
    override fun toString(): String {
        return "${tag.name} $coords: $value"
    }
}

