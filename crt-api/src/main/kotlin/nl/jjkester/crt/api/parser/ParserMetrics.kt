package nl.jjkester.crt.api.parser

public data class ParserMetrics(
    public val fileReadTimeMillis: Long,
    public val sourceParseTimeMillis: Long,
    public val intermediateTransformTimeMillis: Long
) {

    public val totalTimeMillis: Long = fileReadTimeMillis + sourceParseTimeMillis + intermediateTransformTimeMillis
}
