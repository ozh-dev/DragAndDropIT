package ru.ozh.draganddropit.controller

import android.graphics.Color
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.pojo_controller_view.view.*
import ru.ozh.draganddropit.R
import ru.ozh.draganddropit.model.SomePojo
import ru.surfstudio.easyadapter.recycler.controller.BindableItemController
import ru.surfstudio.easyadapter.recycler.holder.BindableViewHolder

class PojoController : BindableItemController<SomePojo, PojoController.Holder>() {

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

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    inner class Holder(parent: ViewGroup): BindableViewHolder<SomePojo>(parent, R.layout.pojo_controller_view) {
        override fun bind(data: SomePojo) {
            itemView.tag = data
            itemView.root_item.setBackgroundColor(Color.parseColor(colors[data.id]))
            itemView.count_tv.text = data.letter.toString()
        }

        private fun scale(v: View, upScale: Boolean, f: (() -> Unit)? = null) {
            ViewCompat.animate(v)
                    .scaleX(if (upScale) 1.2F else 1F)
                    .scaleY(if (upScale) 1.2F else 1F)
                    .setDuration(150)
                    .withEndAction(f)
                    .start()
        }
    }
}