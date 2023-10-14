package nl.jjkester.crt.api.factory

import nl.jjkester.crt.api.model.*

object TestUtils {

    fun blocks() = listOf(
        Container(emptyList(), null),
        Paragraph(emptyList(), null),
        CodeBlock("", null, null)
    ).shuffled()

    fun spans() = listOf(
        Text("", null),
        Emphasis(emptyList(), null),
        Link(Link.Destination(""), emptyList(), null)
    ).shuffled()

    fun metadata() = object : NodeMetadata {
        override val sourceLocation: SourceLocation = SourceLocation(1, 0)
    }

    fun headingLevels() = Heading.Level::class.sealedSubclasses.map { it.objectInstance!! }
}
