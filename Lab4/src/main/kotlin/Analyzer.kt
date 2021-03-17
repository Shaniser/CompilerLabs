class Analyzer(
    val program: String,
    private val compiler: Compiler
) {
    private var currentPos: Position

    fun nextToken(): Token {
        var isError = false
        while (!currentPos.isEOF) {
            while (currentPos.isWhitespace) currentPos++

            val token: Token? = when {
                currentPos.char == '/' -> readComment(currentPos)
                currentPos.isLetter -> readKeyWordOrIdent(currentPos)
                currentPos.isDigit -> readNumber(currentPos)
                else -> null
            }
            if (token == null || token.tag == Tag.UNKNOWN) {
                if (!isError) compiler.addOutput(true, currentPos, "Unrecognized token")
                isError = true
                currentPos++
            } else {
                currentPos = token.coords.end.next()
                return token
            }
        }
        return Token.Unknown(Tag.END, currentPos, currentPos)
    }

    private fun readComment(cur: Position): Token? {
        if (currentPos.next().char != '/')  return null
        var p: Position = cur.next().next()
        val sb = StringBuilder()
        while (true) {
            if (p.isNewLine) break else {
                sb.append(p.char)
                p = p.next()
            }
        }
        return Token.Comment(sb.toString(), cur, p)
    }

    private fun readKeyWordOrIdent(cur: Position): Token? {
        var p = Position(cur)
        val sb = StringBuilder()
        while (true) {
            sb.append(p.char)
            val next = p.next()
            if (next.isDigit || next.isLetter) {
                p++
            } else return when {
                Token.KeyWord.keywords.contains(sb.toString()) -> Token.KeyWord(sb.toString(), cur, p)
                p.char == cur.char -> Token.Ident(sb.toString(), cur, p)
                else -> null
            }
        }
    }

    private fun readNumber(cur: Position): Token {
        var p = Position(cur)
        val sb = StringBuilder()
        while (true) {
            sb.append(p.char)
            val next = p.next()
            if (next.isDigit || next.char in 'A'..'F' || next.char in 'a'..'f') p++
            else return Token.Number(sb.toString(), cur, p)
        }
    }

    init {
        currentPos = Position(program)
    }
}