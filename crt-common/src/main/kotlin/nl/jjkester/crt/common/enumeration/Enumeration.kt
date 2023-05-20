package nl.jjkester.crt.common.enumeration

/**
 * Enumeration sequence.
 *
 * @param T Type of enumerated values.
 */
public sealed interface Enumeration<out T : Any> : Iterable<T> {

    /**
     * Enumeration sequence for the next nested level.
     */
    public val child: Enumeration<T>

    /**
     * Iterates over the values of the enumeration sequence.
     */
    override fun iterator(): Iterator<T>

    /**
     * Fixed enumeration sequence.
     */
    public interface Fixed<out T : Any> : Enumeration<T>

    /**
     * Counting enumeration sequence to provide every item with a distinct value.
     */
    public interface Counting<out T : Any> : Enumeration<T>
}
