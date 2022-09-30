package nl.jjkester.crt.renderer

import nl.jjkester.crt.text.RichTextString

interface TextRenderer<out R : RichTextString> : Renderer<R>
