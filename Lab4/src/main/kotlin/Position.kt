class Position {
    var text: String
        private set
    var line: Int
        private set
    var pos: Int
        private set
    var index: Int
        private set

    constructor(text: String) {
        this.text = text
        pos = 0
        line = pos
        index = 0
    }

    constructor(p: Position) {
        text = p.text
        line = p.line
        pos = p.pos
        index = p.index
    }

    override fun toString(): String {
        return "($line, $pos)"
    }

    val isEOF: Boolean
        get() = index == text.length

    val char: Char
        get() = if (isEOF) (-1).toChar() else text.codePointAt(index).toChar()

    val isWhitespace: Boolean
        get() = !isEOF && Character.isWhitespace(char)

    val isDigit: Boolean
        get() = !isEOF && text[index].isDigit()

    val isLetter: Boolean
        get() = !isEOF && Character.isLetter(char)

    val isNewLine: Boolean
        get() {
            if (index == text.length) {
                return true
            }
            return if (text[index] == '\r' && index + 1 < text.length) {
                text[index + 1] == '\n'
            } else text[index] == '\n'
        }

    fun next(): Position {
        val p = Position(this)
        if (!p.isEOF) {
            if (p.isNewLine) {
                if (p.text[p.index] == '\r') p.index++
                p.line++
                p.pos = 0
            } else {
                if (Character.isHighSurrogate(p.text[p.index])) p.index++
                p.pos++
            }
            p.index++
        }
        return p
    }

    operator fun inc(): Position {
        return next()
    }
}