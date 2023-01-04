package nl.jjkester.crt.api.model

/**
 * Node representing a heading block.
 *
 * @property level Importance level of this heading.
 * @property children Contents of this heading. A heading can only contain spans.
 * @property metadata Optional metadata of this node.
 */
public class Heading(
    public val level: Level,
    override val children: List<Node.Span>,
    override val metadata: NodeMetadata?
) : Node.Block {

    /**
     * Level of a heading.
     *
     * @property index Integer for comparing levels. A lower number gives the heading higher priority.
     */
    public sealed class Level(private val index: Int) : Comparable<Level> {

        override fun compareTo(other: Level): Int {
            // 1 is highest, so we reverse the comparison
            return other.index.compareTo(index)
        }

        public companion object {

            /**
             * Parses the provided [value] to a predefined heading level. Returns `null` when the value does not match a
             * predefined level.
             */
            public fun fromIntOrNull(value: Int): Level? = when (value) {
                1 -> One
                2 -> Two
                3 -> Three
                4 -> Four
                5 -> Five
                6 -> Six
                else -> null
            }
        }

        /**
         * First heading level. This is the most important predefined level.
         */
        public object One : Level(1)

        /**
         * Second heading level.
         */
        public object Two : Level(2)

        /**
         * Third heading level.
         */
        public object Three : Level(3)

        /**
         * Fourth heading level.
         */
        public object Four : Level(4)

        /**
         * Fifth heading level.
         */
        public object Five : Level(5)

        /**
         * Sixth heading level. This is the least important predefined level.
         */
        public object Six : Level(6)
    }
}
