package nl.jjkester.crt.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import nl.jjkester.crt.compose.test.RichTextTestCase

class RichTextTest {

    @Preview(showBackground = true)
    @Composable
    fun AllElementsTest() {
        RichTextTestCase {
            blockquote {
                paragraph {
                    +"Blockquote"
                }
            }
            code("<p>HTML paragraph</p>", "html")
            code("text") {
                +"Plain text"
            }
            divider()
            repeat(6) {
                heading(it + 1) {
                    +"Heading ${it + 1}"
                }
            }
            orderedList {
                repeat(3) {
                    listItem {
                        paragraph {
                            +"Item ${it + 1}"
                        }
                    }
                }
            }
            unorderedList {
                repeat(3) {
                    listItem {
                        paragraph {
                            +"Item ${it + 1}"
                        }
                    }
                }
            }
            paragraph {
                code("<p>HTML paragraph</p>", "html")
                code("text") {
                    +"Plain text"
                }
                emphasis {
                    +"Emphasis"
                }
                link("destination") {
                    +"Link"
                }
                strongEmphasis {
                    +"Strong emphasis"
                }
                text("Text")
                +"Text"
            }
        }
    }
}
