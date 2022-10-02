package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.document.RichTextDocument

public interface DocumentRenderer<out R : RichTextDocument> : Renderer<R>
