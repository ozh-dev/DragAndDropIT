package ru.ozh.draganddropit.controller


interface ItemDragPositions {
    fun onItemDrag(from: Int, to: Int)

    fun onDragEnd(position: Int)
}