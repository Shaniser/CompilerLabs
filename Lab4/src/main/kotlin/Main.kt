import java.nio.file.Files
import java.nio.file.Paths


fun main(args: Array<String>) {
    println()
    val compiler = Compiler()
    val program = String(Files.readAllBytes(Paths.get(args[0])))
    println(program)
    println()
    val analyzer = Analyzer(program, compiler)
    println("${"-".repeat(10)}TOKENS${"-".repeat(10)}")
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
        println("${"-".repeat(10)}DEBUG${"-".repeat(10)}")
        compiler.outputMessages()
    }
}
