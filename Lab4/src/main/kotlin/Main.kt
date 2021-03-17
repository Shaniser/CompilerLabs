import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


fun main(args: Array<String>) {
//    val compiler = Compiler()
//    val program = String(Files.readAllBytes(Paths.get(args[0])))
//    val scanner = Scanner(program, compiler)
//    println("TOKENS:")
//    while (true) {
//        val token: Token = scanner.nextToken()
//        if (token.tag !== DomainTag.END_OF_PROGRAM) {
//            print(token.tag.name().toString() + " " + token.coords.toString() + ": ")
//            if (token is WordToken) {
//                System.out.print((token as WordToken).value)
//            } else if (token is LabelToken) {
//                System.out.print((token as LabelToken).value)
//            } else if (token is CommentToken) {
//                System.out.print((token as CommentToken).value)
//            }
//            println()
//        } else {
//            break
//        }
//    }
//    if (compiler.errors()) {
//        println("MESSAGES:")
//        compiler.outputMessages()
//    }
}
