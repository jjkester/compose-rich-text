package nl.jjkester.crt.common.enumeration

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import org.junit.jupiter.api.Test

class SequenceEnumerationTest {

    @Test
    fun `different child enumeration`() {
        val child = StaticEnumeration({ "child" })
        val enumeration = SequenceEnumeration(sequenceOf("parent"), child)

        assertThat(enumeration).all {
            transform { it.child }.isSameAs(child)
            transform { it.iterator().next() }.isEqualTo("parent")
        }
    }

    @Test
    fun `default child enumeration`() {
        val enumeration = SequenceEnumeration(sequenceOf("parent"))

        assertThat(enumeration).all {
            transform { it.child }.isSameAs(enumeration)
        }
    }
}
