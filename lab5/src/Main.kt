import java.io.File
import java.lang.StringBuilder
import java.util.*


val machine = Machine(File("/Users/shaniser/Desktop/Учеба/Компиляторы/CompilerLabs/lab5/graph.dot").readText())

fun main(args: Array<String>) {
    val program = File(args[0]).readText()

    var cur = Position(program)

    val tokens = ArrayList<Token>()
    val outputs: ArrayList<Output> = ArrayList<Output>()

    var isError = false

    while (!cur.isEOF) {
        while (cur.isWhitespace) cur++

        nextToken(cur).apply {
            if (tag == Tag.ERROR) {
                isError = true
                outputs.add(
                    when (value) {
                        "Error" -> Output(true, "Unexpected symbol in string", coords.end)
                        else -> Output(true, "Unrecognized token", cur)
                    }
                )
            } else {
                isError = false
                tokens.add(this)
            }
            cur = coords.end.next()
        }
    }

    println("TOKENS:")
    tokens.forEach { println(it) }

    if (outputs.size != 0) {
        println("DEBUG:")
        outputs.forEach { println(it) }
    }

//        if (prev_state != 1 && prev_state != 0 && prev_state != 15) {
//            end = Position(cur)
//            if (prev_state == 12) errorMessages.add(
//                ErrorMessage(
//                    true,
//                    "error: \"|+\" within comment",
//                    cur
//                )
//            ) else if (!final_state.contains(DomainTag.values().get(prev_state)) && DomainTag.values()
//                    .get(prev_state) === DomainTag.OPERATION_OR_COMMENT
//            ) errorMessages.add(
//                ErrorMessage(
//                    true,
//                    "error: expected \"|\" or \"+\" for token \"|",
//                    cur
//                )
//            ) else if (!final_state.contains(DomainTag.values().get(prev_state)) && DomainTag.values()
//                    .get(prev_state) === DomainTag.COMMENT
//            ) errorMessages.add(
//                ErrorMessage(
//                    true,
//                    "error: expected \"+|\" for commentary",
//                    cur
//                )
//            ) else if (!final_state.contains(DomainTag.values().get(prev_state))) errorMessages.add(
//                ErrorMessage(
//                    true,
//                    "error: unknown token \"" + program.substring(start.index, end.index) + "\"",
//                    cur
//                )
//            ) else tokens.add(
//                Token(
//                    DomainTag.values().get(prev_state),
//                    program.substring(start.index, end.index),
//                    start,
//                    end
//                )
//            )
//        } else {
//            while (!cur.isEOF && numberColumn(cur) === -1) {
//                cur = cur.next()
//            }
//            end = Position(cur)
//            errorMessages.add(
//                ErrorMessage(
//                    true,
//                    "error: unknown token \"" + program.substring(start.index, end.index) + "\"",
//                    cur
//                )
//            )
//        }

//    for (token in tokens) println(token)
//
//    if (!errorMessages.isEmpty()) {
//        println("Errors:")
//        for (mes in errorMessages) System.out.println(mes.toString())
//    }
}

fun nextToken(start: Position): Token {
    var cur = Position(start)

    val startState = machine.nodes.indexOf("Start_state")
    var state = startState
    var prevState = state
    val sb = StringBuilder()

    while (state != -1) {
        val next = if (state == startState) cur else cur.next()
        val nextState = machine.charToTransition(next.char, state)
        if (next.isEOF || nextState == -1) break
        cur = next
        prevState = state
        state = nextState
        sb.append(cur.char)
    }
    return machine.nodes[state].let { label ->
        return (Tag.values().firstOrNull { it.labels.contains(label) }?:Tag.ERROR).let {
            if (it == Tag.ERROR)
                Token(it, start, cur, label)
            else
                Token(it, start, cur, sb.toString())
        }
    }
}