package ru.ozh.draganddropit.view

import android.view.DragEvent
import android.view.View

class DragAndDropHelper constructor(
        private val onDragStart: ((view: View, dragView: View, dragEvent: DragEvent) -> Unit)? = null,
        private val onDragEnd: ((view: View, dragView: View, dragEvent: DragEvent) -> Unit)? = null,
        private val onDragLocation: ((view: View, dragView: View, dragEvent: DragEvent) -> Unit)? = null,
        private val onDrop: ((view: View, dragView: View, dragEvent: DragEvent) -> Unit)? = null,
        private val onEntered: ((view: View, dragView: View, dragEvent: DragEvent) -> Unit)? = null,
        private val onExited: ((view: View, dragView: View, dragEvent: DragEvent) -> Unit)? = null) : View.OnDragListener {

    override fun onDrag(view: View, dragEvent: DragEvent): Boolean {

        val (dragAction, dragView) = destructureDragEvent(dragEvent)

        when (dragAction) {
            DragEvent.ACTION_DRAG_STARTED -> {
                onDragStart?.invoke(view, dragView, dragEvent)
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                onDragLocation?.invoke(view, dragView, dragEvent)
            }
            DragEvent.ACTION_DROP -> {
                onDrop?.invoke(view, dragView, dragEvent)
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                onDragEnd?.invoke(view, dragView, dragEvent)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                onEntered?.invoke(view, dragView, dragEvent)
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                onExited?.invoke(view, dragView, dragEvent)
            }
        }
        return true
    }

    private fun destructureDragEvent(dragEvent: DragEvent): DragEventComponent {
        val action = dragEvent.action
        val view = dragEvent.localState as View
        return DragEventComponent(action, view)
    }

    private data class DragEventComponent(val action: Int, val dragView: View)
}