open class Token(
    val tag: Tag,
    val coords: Coords
) {
    constructor(tag: Tag, start: Position, end: Position) : this(tag, Coords(start, end))

    @Override
    override fun toString(): String {
        return coords.toString()
    }

    class Unknown(tag: Tag, starting: Position, following: Position, public val value: String = "") :
        Token(tag, starting, following)

    class Comment(public val value: String, starting: Position, following: Position) :
        Token(Tag.COMMENT, starting, following) {
        override fun toString(): String {
            return "COMMENT ${super.toString()}: $value"
        }
    }

    public class Coord(public val value: String, starting: Position, following: Position) :
        Token(Tag.COORD, starting, following) {
        override fun toString(): String {
            return "COORD ${super.toString()}: $value"
        }
    }
}

