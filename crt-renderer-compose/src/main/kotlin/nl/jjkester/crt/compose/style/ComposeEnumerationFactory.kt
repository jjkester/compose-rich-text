package nl.jjkester.crt.compose.style

import androidx.compose.ui.text.AnnotatedString
import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.common.enumeration.EnumerationFactory
import nl.jjkester.crt.compose.text.AnnotatedStringWithExtras
import nl.jjkester.crt.compose.text.withoutExtras

object ComposeEnumerationFactory : EnumerationFactory<AnnotatedStringWithExtras>() {

    @InternalRendererApi
    override fun convert(value: String): AnnotatedStringWithExtras = AnnotatedString(value).withoutExtras()
}
