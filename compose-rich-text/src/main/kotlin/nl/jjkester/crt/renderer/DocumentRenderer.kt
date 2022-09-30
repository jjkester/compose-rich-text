package nl.jjkester.crt.renderer

import nl.jjkester.crt.document.RichTextDocument

interface DocumentRenderer<out R : RichTextDocument> : Renderer<R>