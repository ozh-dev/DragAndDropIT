package ru.ozh.draganddropit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import ru.ozh.draganddropit.controller.ItemDragPositions
import ru.ozh.draganddropit.controller.PojoController
import ru.ozh.draganddropit.model.SomePojo
import ru.surfstudio.easyadapter.recycler.EasyAdapter
import ru.surfstudio.easyadapter.recycler.ItemList

class MainActivity : AppCompatActivity(), ItemDragPositions {
    companion object {
        public const val TAG = "MainActivity"
    }
    override fun onItemDrag(from: Int, to: Int) {
        Log.d(TAG, "from: $from ::: to $to")

        list[to].apply {
            letter = this.letter.plus(list[from].letter)
        }

        render()
    }

    override fun onDragEnd(position: Int) {
//        list.removeAt(position)
//        render()
    }

    private val pojoController = PojoController()

    private val easyAdapter = EasyAdapter()

    private val list = mutableListOf<SomePojo>()

    init {
        inflateValues()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = easyAdapter
        (recycler.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        recycler.addOn

//        increment_view.setOnTouchListener(View.OnTouchListener { _, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    val clipData = ClipData.newPlainText("add", "")
//                    val shadowBuilder = View.DragShadowBuilder(increment_view)
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        increment_view.startDragAndDrop(clipData, shadowBuilder, increment_view, 0)
//                    } else {
//                        increment_view.startDrag(clipData, shadowBuilder, increment_view, 0)
//                    }
//                    return@OnTouchListener true
//                }
//            }
//            false
//        })
//
//        reset_btn.setOnClickListener {
//            inflateValues()
//            render()
//        }

        render()

        val itemTouchHelper = ItemTouchHelper(DragCallBack(this))
        itemTouchHelper.attachToRecyclerView(recycler)
    }

    private fun render() {
        easyAdapter.setItems(ItemList.create()
                .addAll(list, pojoController))
    }

    private fun inflateValues() {
        list.clear()
        list.apply {
            for (index in 0..8) {
                add(SomePojo(index, ('A' + index).toString()))
            }
        }
    }

    private fun doIf(condition: Boolean, f: (() -> Unit)?) {
        if (condition) f?.invoke()
    }

}