package nl.jjkester.crt.common.enumeration

public sealed interface Enumeration {

    public val child: Enumeration

    public interface Fixed<out T : Any> : Enumeration {

        public val value: T

        public override val child: Fixed<T>
    }

    public interface Counting<out T : Any> : Enumeration {

        public val sequence: Sequence<T>

        public override val child: Counting<T>
    }
}
