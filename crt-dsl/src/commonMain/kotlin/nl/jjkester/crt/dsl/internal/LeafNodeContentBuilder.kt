package nl.jjkester.crt.dsl.internal

import nl.jjkester.crt.dsl.LeafScope

internal class LeafNodeContentBuilder : LeafScope {

    private val builder = StringBuilder()

    val string: String
        get() = builder.toString()

    override fun String.unaryPlus() {
        builder.append(this)
    }
}

internal fun (LeafScope.() -> Unit).resolve(): String = LeafNodeContentBuilder().apply(this).string
