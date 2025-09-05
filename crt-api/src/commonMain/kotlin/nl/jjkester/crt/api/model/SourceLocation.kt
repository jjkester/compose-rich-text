package nl.jjkester.crt.api.model

/**
 * Metadata of a node containing the location of the node in the input.
 *
 * @property row Row index. The first row has index 1.
 * @property column Column (character) index. The first character has index 1. Index 0 represents the start of the row.
 */
public data class SourceLocation(
    public val row: Int,
    public val column: Int
) {
    init {
        require(row > 0) { "Row must be 1 or greater" }
        require(column >= 0) { "Column must be 0 or greater" }
    }
}
