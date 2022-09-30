package nl.jjkester.crt.api.renderer

public interface Renderer<out R : Any> {
    public fun render(input: String): R
}
