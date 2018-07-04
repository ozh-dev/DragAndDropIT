package ru.ozh.draganddropit

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View.GONE
import ru.ozh.draganddropit.controller.ItemDragPositions


class DragCallBack(val callBack: ItemDragPositions) : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean  {
        callBack.onItemDrag(viewHolder.adapterPosition - 1, target.adapterPosition - 1)
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {

    }

    override fun canDropOver(recyclerView: RecyclerView?, current: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return super.canDropOver(recyclerView, current, target)
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        //callBack.onDragEnd(viewHolder.adapterPosition - 1)
        viewHolder.itemView.visibility = GONE

        Log.d(MainActivity.TAG, "DRAG END")
    }
}