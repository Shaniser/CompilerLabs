open class Token(
    val tag: Tag,
    val coords: Coords
) {
    constructor(tag: Tag, start: Position, end: Position) : this(tag, Coords(start, end))

    @Override
    override fun toString(): String {
        return coords.toString()
    }

    class Unknown(tag: Tag, starting: Position, following: Position) :
        Token(tag, starting, following)

    class Comment(public val value: String, starting: Position, following: Position) :
        Token(Tag.COMMENT, starting, following) {
        override fun toString(): String {
            return "COMMENT ${super.toString()}: $value"
        }
    }

    class Ident(public val value: String, starting: Position, following: Position) :
        Token(Tag.IDENT, starting, following) {
        override fun toString(): String {
            return "IDENT ${super.toString()}: $value"
        }
    }

    class Number(public val value: String, starting: Position, following: Position) :
        Token(Tag.NUMBER, starting, following) {
        override fun toString(): String {
            return "NUMBER ${super.toString()}: $value"
        }
    }

    class KeyWord(public val value: String, starting: Position, following: Position) :
        Token(Tag.KEYWORD, starting, following) {
        companion object {
            val keywords = arrayListOf(
                "qeq", "xx", "xxx"
            )
        }
        override fun toString(): String {
            return "KEYWORD ${super.toString()}: $value"
        }
    }
}

