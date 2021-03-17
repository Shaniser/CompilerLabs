import kotlin.math.max

class Output (
        private val isError: Boolean,
        private val text: String,
        private val position: Position
) {
    @Override
    override fun toString(): String {
        return if (isError) {
            val lines: Array<String> = position.text.split("\n").toTypedArray()
            val sb = StringBuilder()
            sb.append("Error${position}:\n")
            if (position.line != 0) sb.append("\t...\n")
            sb.append("\t${lines[position.line]}\n\t")
            sb.append(" ".repeat(max(0, position.pos)))
            sb.append("^\n\t")
            sb.append(" ".repeat(max(0, position.pos)))
            sb.append("$text\n")
            if (lines.lastIndex != position.line) sb.append("\t...\n")
            return sb.toString()
        } else "Warning${position}: $text"
    }
}