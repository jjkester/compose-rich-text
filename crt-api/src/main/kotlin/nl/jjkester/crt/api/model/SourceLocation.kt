package nl.jjkester.crt.api.model

public data class SourceLocation(
    public val row: Int,
    public val column: Int
) {
    init {
        require(row > 0) { "Row must be 1 or greater" }
        require(column >= 0) { "Column must be 0 or greater" }
    }
}
