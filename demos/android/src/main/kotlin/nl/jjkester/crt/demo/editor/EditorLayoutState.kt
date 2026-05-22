package nl.jjkester.crt.demo.editor

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope

/**
 * State of the editor layout.
 *
 * @property showEditor Whether to display the editor pane.
 * @property showPreview Whether to display the preview pane.
 */
sealed class EditorLayoutState(val showEditor: Boolean, val showPreview: Boolean) {

    /**
     * Display only the editor pane.
     */
    data object Editor : EditorLayoutState(true, false)

    /**
     * Display only the preview pane.
     */
    data object Preview : EditorLayoutState(false, true)

    /**
     * Display both the editor and preview pane.
     */
    data object Split : EditorLayoutState(true, true)
}

object EditorLayoutStateSaver : Saver<EditorLayoutState, Array<Boolean>> {

    override fun restore(value: Array<Boolean>): EditorLayoutState? = runCatching {
        val (showEditor, showPreview) = value

        when {
            showEditor && showPreview -> EditorLayoutState.Split
            showEditor -> EditorLayoutState.Editor
            showPreview -> EditorLayoutState.Preview
            else -> null
        }
    }.getOrNull()

    override fun SaverScope.save(value: EditorLayoutState): Array<Boolean> =
        arrayOf(value.showEditor, value.showPreview)
}

fun EditorLayoutState.toggle(): EditorLayoutState = when (this) {
    is EditorLayoutState.Editor -> EditorLayoutState.Preview
    is EditorLayoutState.Preview -> EditorLayoutState.Editor
    is EditorLayoutState.Split -> EditorLayoutState.Editor
}

fun EditorLayoutState.split(): EditorLayoutState = when (this) {
    is EditorLayoutState.Editor -> EditorLayoutState.Split
    is EditorLayoutState.Preview -> EditorLayoutState.Split
    is EditorLayoutState.Split -> EditorLayoutState.Editor
}
