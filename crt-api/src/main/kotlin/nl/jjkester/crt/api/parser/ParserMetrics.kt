package nl.jjkester.crt.api.parser

/**
 * Metrics collected during parsing.
 *
 * @property fileReadTimeMillis Time to read the input file, in milliseconds.
 * @property sourceParseTimeMillis Time to parse the source, in milliseconds.
 * @property intermediateTransformTimeMillis Time to transform the parsed source to the internal model, in milliseconds.
 */
public data class ParserMetrics(
    public val fileReadTimeMillis: Long,
    public val sourceParseTimeMillis: Long,
    public val intermediateTransformTimeMillis: Long
) {

    /**
     * Total parse time.
     */
    public val totalTimeMillis: Long = fileReadTimeMillis + sourceParseTimeMillis + intermediateTransformTimeMillis
}
