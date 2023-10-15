package nl.jjkester.crt.markdown

import org.mockito.Mockito
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.jvmErasure

inline fun <reified T : Any> anyValue(): T = anyValue(T::class)

fun <T : Any> anyValue(kclass: KClass<T>): T {
    require(kclass.isValue) { "anyValue() only supports value classes" }
    val valueProperty = kclass.declaredMemberProperties.single { it.javaField != null }
    val inner = valueProperty.returnType.jvmErasure
    return kclass.primaryConstructor!!.call(
        Mockito.any(inner.java) ?: createInstance(inner)
    )
}

private fun <T : Any> createInstance(kclass: KClass<T>): T =
    runCatching { kclass.createInstance() }.getOrNull() ?: Mockito.mock(kclass.java)
