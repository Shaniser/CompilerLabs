enum class Tag(public val labels: ArrayList<String>) {
    IDENT(arrayListOf("Ident", "Ident1", "Ident2", "Ident3", "Ident4")),
    STRING(arrayListOf("String")),
    SPACE(arrayListOf("Space")),
    NUMBER(arrayListOf("Num")),
    KEYWORD(arrayListOf("Keyword")),
    OPERATION(arrayListOf("Operation")),
    ERROR(arrayListOf("Error"))
}