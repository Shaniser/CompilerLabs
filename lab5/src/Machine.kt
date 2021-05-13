class Machine(dotText: String) {
    val nodes = arrayListOf<String>()
    var finalNodes = listOf<String>()
    val transitions = arrayListOf<String>()
    val transitionMatrix = dotToMatrix(dotText)

    private fun dotToMatrix(dotText: String): ArrayList<ArrayList<Int>> {
        val transitInfo = dotText.split("\n").toList().filter {
            it.contains("->")
        }.map {
            it.replace(" ","").replace("\"]","")
        }.map {
            it.split("->", "[label=\"")
        }
        transitInfo.forEach {
            if (!nodes.contains(it[0])) nodes.add(it[0])
            if (!nodes.contains(it[1])) nodes.add(it[1])
            if (!transitions.contains(it[2])) transitions.add(it[2])
        }

        val ans = arrayListOf<ArrayList<Int>>()
        for (i in 0 until nodes.size) {
            val curList = arrayListOf<Int>()
            for (j in 0 until transitions.size) {
                curList.add(-1)
            }
            ans.add(curList)
        }

        transitInfo.forEach {
            ans[nodes.indexOf(it[0])][transitions.indexOf(it[2])] = nodes.indexOf(it[1])
        }

        print("arrayListOf(\n")
        print(" ".repeat("  arrayListOf(".length))
        transitions.forEach {
            print(" ".repeat(5 - "(${transitions.indexOf(it)})".length))
            print("(${transitions.indexOf(it)})")
        }
        for (i in 0 until nodes.size) {
            print("\n\tarrayListOf(")
            for (j in 0 until transitions.size) {
                print(" ".repeat(3 - "${ans[i][j]}".length))
                print("${ans[i][j]}, ")
            }
            print(") //${nodes[i]} ($i)")
        }
        println("\n)\n// Transitions:")
        transitions.forEach {
            println("// $it (${transitions.indexOf(it)})")
        }

        // finalNodes
        finalNodes = dotText.split("\n").toList().filter {
            it.contains("[shape=doublecircle]")
        }.map {
            it.replace(" ","").replace("[shape=doublecircle]","")
        }

        println(finalNodes)

        return ans
    }

    fun charToTransition(char: Char, curNode: Int): Int {
        return transitionMatrix[curNode].find {
            it != -1 && isCorrectTransition(char, transitions[transitionMatrix[curNode].indexOf(it)])
        } ?: -1
    }

    private fun isCorrectTransition(char: Char, transition: String): Boolean {
        return (transition == "_,\\\\n,\\\\t" && char.isWhitespace())
                || (transition == "Digit" && char.isDigit())
                || (transition == "Letter/Digit" && char.isLetterOrDigit())
                || (transition == "Letter/Digit\\\\)" && char.isLetterOrDigit() && char != ')')
                || (transition == "Letter/Digit\\\\{u}" && char.isLetterOrDigit() && char != 'u')
                || (transition == "Letter/Digit\\\\{n}" && char.isLetterOrDigit() && char != 'n')
                || (transition == "Letter/Digit\\\\{s}" && char.isLetterOrDigit() && char != 's')
                || (transition == "Letter/Digit\\\\{e}" && char.isLetterOrDigit() && char != 'e')
                || (transition == "Letter/Digit\\\\{t}" && char.isLetterOrDigit() && char != 't')
                || (transition == "u" && char == 'u')
                || (transition == "s" && char == 's')
                || (transition == "(" && char == '(')
                || (transition == "n" && char == 'n')
                || (transition == "e" && char == 'e')
                || (transition == "t" && char == 't')
                || (transition == ")" && char == ')')
                || (transition == "(,\\\\n" && (char == '(' || char == '\n'))
    }
}