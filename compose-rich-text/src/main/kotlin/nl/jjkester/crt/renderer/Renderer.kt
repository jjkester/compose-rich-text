package nl.jjkester.crt.renderer

interface Renderer<out R : Any> {
    fun render(input: String): R
}