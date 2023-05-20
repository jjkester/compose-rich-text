package nl.jjkester.crt.api.parser

import kotlin.time.Duration

/**
 * Metrics collected during parsing.
 *
 * @property fileReadTime Time to read the input file.
 * @property sourceParseTime Time to parse the source.
 * @property intermediateTransformTime Time to transform the parsed source to the internal model.
 */
public data class ParserMetrics(
    public val fileReadTime: Duration,
    public val sourceParseTime: Duration,
    public val intermediateTransformTime: Duration
) {

    /**
     * Total parse time.
     */
    public val totalTime: Duration = fileReadTime + sourceParseTime + intermediateTransformTime
}
