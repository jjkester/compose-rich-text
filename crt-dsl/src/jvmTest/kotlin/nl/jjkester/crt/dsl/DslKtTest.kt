package nl.jjkester.crt.dsl

import assertk.Assert
import assertk.all
import assertk.assertThat
import assertk.assertions.each
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import assertk.assertions.single
import nl.jjkester.crt.api.model.Blockquote
import nl.jjkester.crt.api.model.Code
import nl.jjkester.crt.api.model.CodeBlock
import nl.jjkester.crt.api.model.Container
import nl.jjkester.crt.api.model.Divider
import nl.jjkester.crt.api.model.Emphasis
import nl.jjkester.crt.api.model.Heading
import nl.jjkester.crt.api.model.Language
import nl.jjkester.crt.api.model.Link
import nl.jjkester.crt.api.model.ListItem
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.model.OrderedList
import nl.jjkester.crt.api.model.Paragraph
import nl.jjkester.crt.api.model.StrongEmphasis
import nl.jjkester.crt.api.model.Text
import nl.jjkester.crt.api.model.UnorderedList
import org.junit.jupiter.api.Test

class DslKtTest {

    @Test
    fun buildRichText() {
        val result = buildRichText {
            heading(1) {
                +"Hello, "
                code("compose-rich-text", "text")
                +"!"
            }
            paragraph {
                +"Documentation can be found on "
                link("https://github.com/jjkester/compose-rich-text") {
                    +"GitHub"
                }
                +"."
            }
            divider()
            blockquote {
                paragraph {
                    +"Hello world"
                    emphasis {
                        +"Every software developer"
                    }
                }
                code("kotlin") {
                    +"data class KotlinCompose(val isAwesome: Boolean = true)"
                }
            }
            orderedList {
                listItem {
                    paragraph {
                        +"One"
                    }
                }
                listItem {
                    paragraph {
                        +"Two"
                    }
                }
            }
            unorderedList {
                listItem {
                    paragraph {
                        strongEmphasis {
                            +"Foo"
                        }
                    }
                }
                listItem {
                    paragraph {
                        +"Bar"
                    }
                }
            }
        }

        assertThat(result).isContainerNode(children = 6) {
            index(0).isHeadingNode(level = Heading.Level.One, children = 3) {
                index(0).isTextNode("Hello, ")
                index(1).isCodeNode("compose-rich-text", languageHint = Language("text"))
                index(2).isTextNode("!")
            }
            index(1).isParagraphNode(children = 3) {
                index(0).isTextNode()
                index(1).isLinkNode(
                    destination = Link.Destination("https://github.com/jjkester/compose-rich-text"),
                    children = 1
                ) {
                    single().isTextNode("GitHub")
                }
                index(2).isTextNode()
            }
            index(2).isDividerNode()
            index(3).isBlockquoteNode(children = 2) {
                index(0).isParagraphNode(children = 2) {
                    index(0).isTextNode()
                    index(1).isEmphasisNode(children = 1) {
                        single().isTextNode()
                    }
                }
                index(1).isCodeBlockNode(languageHint = Language("kotlin")) {
                    isNotEmpty()
                }
            }
            index(4).isOrderedListNode(children = 2) {
                each {
                    it.isListItemNode(child = true) {
                        isParagraphNode(children = 1) {
                            single().isTextNode()
                        }
                    }
                }
            }
            index(5).isUnorderedListNode(children = 2) {
                index(0).isListItemNode(child = true) {
                    isParagraphNode(children = 1) {
                        single().isStrongEmphasisNode(children = 1) {
                            single().isTextNode()
                        }
                    }
                }
                index(1).isListItemNode(child = true) {
                    isParagraphNode(children = 1) {
                        single().isTextNode()
                    }
                }
            }
        }
    }

    private fun <T : Node> Assert<T>.isContainerNode(
        children: Int = 0,
        body: Assert<List<Node>>.() -> Unit = {}
    ) {
        isInstanceOf<Container>().hasChildNodes(children, body)
    }

    private fun <T : Node> Assert<T>.isBlockquoteNode(
        children: Int = 0,
        body: Assert<List<Node>>.() -> Unit = {}
    ) {
        isInstanceOf<Blockquote>().hasChildNodes(children, body)
    }

    private fun <T : Node> Assert<T>.isCodeBlockNode(
        languageHint: Language,
        body: Assert<String>.() -> Unit
    ) {
        isInstanceOf<CodeBlock>().all {
            transform { it.languageHint }.isEqualTo(languageHint)
            transform { it.content }.body()
        }
    }

    private fun <T : Node> Assert<T>.isDividerNode() {
        isInstanceOf<Divider>()
    }

    private fun <T : Node> Assert<T>.isHeadingNode(
        level: Heading.Level,
        children: Int = 0,
        body: Assert<List<Node>>.() -> Unit = {}
    ) {
        isInstanceOf<Heading>().all {
            transform { it.level }.isEqualTo(level)
            hasChildNodes(children, body)
        }
    }

    private fun <T : Node> Assert<T>.isOrderedListNode(
        children: Int = 0,
        body: Assert<List<Node>>.() -> Unit = {}
    ) {
        isInstanceOf<OrderedList>().hasChildNodes(children, body)
    }

    private fun <T : Node> Assert<T>.isParagraphNode(
        children: Int = 0,
        body: Assert<List<Node>>.() -> Unit = {}
    ) {
        isInstanceOf<Paragraph>().hasChildNodes(children, body)
    }

    private fun <T : Node> Assert<T>.isUnorderedListNode(
        children: Int = 0,
        body: Assert<List<Node>>.() -> Unit = {}
    ) {
        isInstanceOf<UnorderedList>().hasChildNodes(children, body)
    }

    private fun <T : Node> Assert<T>.isListItemNode(
        child: Boolean = false,
        body: Assert<Node>.() -> Unit = {}
    ) {
        isInstanceOf<ListItem>().hasChildNodes(if (child) 1 else 0) {
            single().body()
        }
    }

    private fun Assert<Node>.isCodeNode(content: String, languageHint: Language) {
        isInstanceOf<Code>().all {
            transform { it.languageHint }.isEqualTo(languageHint)
            transform { it.content }.isEqualTo(content)
        }
    }

    private fun <T : Node> Assert<T>.isEmphasisNode(
        children: Int = 0,
        body: Assert<List<Node>>.() -> Unit = {}
    ) {
        isInstanceOf<Emphasis>().hasChildNodes(children, body)
    }

    private fun Assert<Node>.isLinkNode(
        destination: Link.Destination,
        children: Int = 0,
        body: Assert<List<Node>>.() -> Unit = {}
    ) {
        isInstanceOf<Link>().all {
            transform { it.destination }.isEqualTo(destination)
            hasChildNodes(children, body)
        }
    }

    private fun <T : Node> Assert<T>.isStrongEmphasisNode(
        children: Int = 0,
        body: Assert<List<Node>>.() -> Unit = {}
    ) {
        isInstanceOf<StrongEmphasis>().hasChildNodes(children, body)
    }

    private fun <T : Node> Assert<T>.isTextNode() {
        isInstanceOf<Text>()
    }

    private fun <T : Node> Assert<T>.isTextNode(text: String) {
        isInstanceOf<Text>().transform { it.text }.isEqualTo(text)
    }

    private fun Assert<Node>.hasChildNodes(size: Int, body: Assert<List<Node>>.() -> Unit) {
        transform { it.children }.all {
            hasSize(size)
            body()
        }
    }
}
