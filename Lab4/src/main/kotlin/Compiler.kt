import java.util.ArrayList

class Compiler {
    private val outputs: MutableList<Output>
    private val nameCodes: MutableMap<String, Int>
    private val names: MutableList<String>

    fun addName(name: String): Int {
        return if (nameCodes.containsKey(name)) nameCodes[name]!! else {
            val code = names.size
            names.add(name)
            nameCodes[name] = code
            code
        }
    }

    fun errors(): Boolean {
        return outputs.size > 0
    }

    fun getName(code: Int): String {
        return names[code]
    }

    fun addOutput(isErr: Boolean, c: Position, text: String) {
        outputs.add(Output(isErr, text, c))
    }

    fun outputMessages() {
        for (output in outputs) {
            println(output)
        }
    }

    fun getScanner(program: String): Analyzer {
        return Analyzer(program, this)
    }

    init {
        outputs = ArrayList<Output>()
        nameCodes = HashMap()
        names = ArrayList()
    }
}