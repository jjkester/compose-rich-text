package nl.jjkester.crt.dsl.internal

import nl.jjkester.crt.api.model.ListItem
import nl.jjkester.crt.dsl.BlockScope
import nl.jjkester.crt.dsl.ListScope

internal class ListNodeContentBuilder : AbstractNodeContentBuilder<ListItem>(), ListScope {

    override fun listItem(init: BlockScope.() -> Unit) {
        add(ListItem(init.resolve(), null))
    }
}

internal fun (ListScope.() -> Unit).resolve(): List<ListItem> = ListNodeContentBuilder().apply(this).children
