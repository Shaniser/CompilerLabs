import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


fun main(args: Array<String>) {
    println()
    val compiler = Compiler()
    val program = String(Files.readAllBytes(Paths.get(args[0])))
    println(program)
    println()
    val analyzer = Analyzer(program, compiler)
    println("TOKENS:")
    while (true) {
        val token: Token = analyzer.nextToken()
        if (token.tag != Tag.END) {
            when (token) {
                is Token.KeyWord -> {
                    println(token)
                }
                is Token.Ident -> {
                    println(token)
                }
                is Token.Number -> {
                    println(token)
                }
                is Token.Comment -> {
                    println(token)
                }
            }
            println()
        } else {
            break
        }
    }
    if (compiler.errors()) {
        println("MESSAGES:")
        compiler.outputMessages()
    }
}
