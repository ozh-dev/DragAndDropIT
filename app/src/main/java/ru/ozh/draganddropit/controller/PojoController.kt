package ru.ozh.draganddropit.controller

import android.content.ClipData
import android.graphics.Color
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.pojo_controller_view.view.*
import ru.ozh.draganddropit.R
import ru.ozh.draganddropit.model.SomePojo
import ru.ozh.draganddropit.view.DragAndDropHelper
import ru.surfstudio.easyadapter.recycler.controller.BindableItemController
import ru.surfstudio.easyadapter.recycler.holder.BindableViewHolder

class PojoController(val dragAndDropHelper: DragAndDropHelper) : BindableItemController<SomePojo, PojoController.Holder>() {

    private val colors = arrayListOf(
            "#F44336",
            "#E91E63",
            "#9C27B0",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B")

    override fun getItemId(data: SomePojo) = data.id.toLong()

    override fun createViewHolder(parent: ViewGroup) = Holder(parent, dragAndDropHelper)

    inner class Holder(parent: ViewGroup, private val dragAndDropHelper: DragAndDropHelper): BindableViewHolder<SomePojo>(parent, R.layout.pojo_controller_view) {
        override fun bind(data: SomePojo) {
            itemView.root_item.setBackgroundColor(Color.parseColor(colors[data.id]))
            itemView.tag  = data.id
            itemView.setOnDragListener(dragAndDropHelper)
            itemView.count_tv.text = data.count.toString()

            itemView.setOnTouchListener(View.OnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val clipData = ClipData.newPlainText("controller", "ignore")
                        val shadowBuilder = View.DragShadowBuilder(itemView)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            itemView.startDragAndDrop(clipData, shadowBuilder, itemView, 0)
                        } else {
                            itemView.startDrag(clipData, shadowBuilder, itemView, 0)
                        }
                        return@OnTouchListener true
                    }
                }
                false
            })
        }

    }
}