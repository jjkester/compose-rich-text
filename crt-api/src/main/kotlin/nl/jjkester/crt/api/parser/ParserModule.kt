package nl.jjkester.crt.api.parser

import nl.jjkester.crt.api.annotations.InternalParserApi

/**
 * Module of a parser.
 *
 * A parser module optionally transforms an intermediate result. Parser modules can be combined to add additional
 * functionality for nodes that are not transformed by another module.
 */
public interface ParserModule<T : Any, I : Any> {

    /**
     * Transforms the [value], if able.
     *
     * Implementations must call [parseNext] in order for every next element, providing that element as argument. This
     * decouples transformations from the structure of the source. This also allows the result of the next element to be
     * used in the result of this element, which is necessary for the tree-shaped internal model.
     *
     * @param value Value to transform.
     * @param parseNext Function to transform a next element.
     */
    @InternalParserApi
    public fun parse(value: T, parseNext: (T) -> I?): I?
}
