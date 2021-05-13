import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.random.Random


fun main(args: Array<String>) {
    println()
    val compiler = Compiler()
//    val programSb = StringBuilder()
//    for (line in 0 until 5) {
//        for (pos in 0 until 3) {
//            programSb.append(arrayOf('W','S','N','E')[Random.nextInt(0, 4)])
//            programSb.append(Random.nextInt(-20,200))
//            if (Random.nextFloat() < 0.5) {
//                programSb.append('.')
//                programSb.append(Random.nextInt())
//            } else {
//                programSb.append('D')
//                programSb.append(Random.nextInt(-10, 70))
//                programSb.append('\'')
//                programSb.append(Random.nextInt(-10, 70))
//                programSb.append('\"')
//            }
//            if (Random.nextBoolean()) programSb.append(' ')
//        }
//        programSb.append("\n")
//    }
    val program = Files.readString(Paths.get(args[0]), StandardCharsets.UTF_8)
//    val program = programSb.toString()
    println(program)
    println()
    val analyzer = Analyzer(program, compiler)
    println("${"-".repeat(10)}TOKENS${"-".repeat(10)}")
    while (true) {
        val token: Token = analyzer.nextToken()
        if (token.tag != Tag.END) {
            when (token) {
                is Token.Comment -> {
                    println(token)
                }
                is Token.Coord -> {
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
