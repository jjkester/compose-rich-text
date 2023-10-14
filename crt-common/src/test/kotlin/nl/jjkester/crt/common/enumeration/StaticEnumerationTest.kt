package nl.jjkester.crt.common.enumeration

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import org.junit.jupiter.api.Test

class StaticEnumerationTest {

    @Test
    fun `different child enumeration`() {
        val child = SequenceEnumeration(sequenceOf("child"))
        val enumeration = StaticEnumeration({ "parent" }, child)

        assertThat(enumeration).all {
            transform { it.child }.isSameAs(child)
            transform { it.iterator().next() }.isEqualTo("parent")
        }
    }

    @Test
    fun `default child enumeration`() {
        val enumeration = StaticEnumeration({ "parent" })

        assertThat(enumeration).all {
            transform { it.child }.isSameAs(enumeration)
        }
    }
}
