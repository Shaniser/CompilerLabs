class Analyzer(
    program: String,
    private val compiler: Compiler
) {
    private var currentPos: Position

    fun nextToken(): Token {
        var isError = false
        while (!currentPos.isEOF) {
            while (currentPos.isWhitespace) currentPos++
            if (currentPos.isEOF) break

            val token: Token? = when(currentPos.char) {
                '/' -> readComment(currentPos)
                'S','E','N','W' -> readCoord(currentPos)
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

    private fun readCoord(cur: Position): Token? {
        var p = Position(cur)
        val sb = StringBuilder()
        sb.append((p++).char)
        if (!p.isDigit) return null
        val numbers = StringBuilder()
        while (true) {
            numbers.append(p.char)
            val next = p.next()
            if (next.isDigit) {
                p++
            } else break
        }
        val numbersOrNull = numbers.toString().toIntOrNull()
        if (numbersOrNull == null || numbersOrNull > 180) return null
        sb.append(numbers)

        val tail = when((++p).char) {
            '.' -> {
                sb.append('.')
                if (!(++p).isDigit) return null
                while (true) {
                    sb.append(p.char)
                    val next = p.next()
                    if (next.isDigit) {
                        p++
                    } else break
                }
                ""
            }
            'D' -> {
                sb.append('D')
                if (p.next().isDigit) {
                    readMinutesAndSeconds(p.next()).let {
                        if (it == null) {
                            ""
                        } else {
                            (it as Token.Unknown).value
                        }
                    }
                } else ""
            }
            else -> null
        }
        return if (tail != null) {
            Token.Coord(sb.append(tail).toString(), cur, p + tail.length)
        } else null
    }

    private fun readMinutesAndSeconds(cur: Position): Token? {
        var p = Position(cur)
        val minutesSb = StringBuilder()
        while (true) {
            minutesSb.append(p.char)
            val next = p.next()
            if (next.isDigit) {
                p++
            } else break
        }
        val minutesOrNull = minutesSb.toString().toIntOrNull()
        if (minutesOrNull == null || minutesOrNull > 59 || (++p).char != '\'') return null
        minutesSb.append((p++).char)

        if (p.isDigit) {
            val secondsSb = StringBuilder()
            while (true) {
                secondsSb.append(p.char)
                val next = p.next()
                if (next.isDigit) {
                    p++
                } else break
            }

            val secondsOrNull = secondsSb.toString().toIntOrNull()
            if (secondsOrNull == null || secondsOrNull > 59 || (++p).char != '\"') return null
            secondsSb.append(p.char)
            minutesSb.append(secondsSb)
        } else return null
        return Token.Unknown(Tag.UNKNOWN, cur, p, minutesSb.toString())
    }

    init {
        currentPos = Position(program)
    }
}