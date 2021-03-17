import kotlin.math.max

class Output (val isError: Boolean, val text: String, val position: Position) {
    @Override
    override fun toString(): String {
        return if (isError) {
            val lines: Array<String> = position.text.split("\n").toTypedArray()
            val sb = StringBuilder()
            sb.append("Error${position}:\n")
            sb.append(lines[position.line])
            sb.append("\n")
            sb.append(" ".repeat(max(0, position.pos)))
            sb.append("^")
            sb.append("\n")
            sb.append(" ".repeat(max(0, position.pos)))
            sb.append(text)
            sb.append("\n")
            return sb.toString()
        } else "Warning${position}: $text"
    }
}