package nl.jjkester.crt.common.enumeration

import nl.jjkester.crt.api.text.RichTextString

public sealed interface Enumeration {

    public val child: Enumeration

    public interface Fixed<out T : RichTextString> : Enumeration {

        public val value: T

        public override val child: Fixed<T>
    }

    public interface Counting<out T : RichTextString> : Enumeration {

        public val sequence: Sequence<T>

        public override val child: Counting<T>
    }
}
