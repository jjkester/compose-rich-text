package nl.jjkester.crt.api.model.tree

public class Heading(
    public val level: Level,
    override val children: List<Span>,
    override val metadata: NodeMetadata?
) : Node.Block() {

    public sealed class Level(private val index: Int) : Comparable<Level> {

        override fun compareTo(other: Level): Int {
            // 1 is highest, so we reverse the comparison
            return other.index.compareTo(index)
        }

        public companion object {

            public fun fromInt(value: Int): Level? = when (value) {
                1 -> One
                2 -> Two
                3 -> Three
                4 -> Four
                5 -> Five
                6 -> Six
                else -> null
            }
        }

        public object One : Level(1)

        public object Two : Level(2)

        public object Three : Level(3)

        public object Four : Level(4)

        public object Five : Level(5)

        public object Six : Level(6)
    }
}
