package ru.ozh.draganddropit

import android.content.ClipData
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import ru.ozh.draganddropit.controller.PojoController
import ru.ozh.draganddropit.model.SomePojo
import ru.ozh.draganddropit.view.DragAndDropHelper
import ru.surfstudio.easyadapter.recycler.EasyAdapter
import ru.surfstudio.easyadapter.recycler.ItemList

class MainActivity : AppCompatActivity() {

    private val controllerDragAndDropHelper = DragAndDropHelper(
            onEntered = { v, _, e ->
                if (e.clipDescription.label != "controller") {
                    scale(v, true)
                }

            },
            onExited = { v, _, e ->
                if (e.clipDescription.label != "controller") {
                    scale(v, false)
                }

            },
            onDrop = { v, _, e ->
                if (e.clipDescription.label != "controller") {
                    list.find { it.id == (v.tag as Int) }?.apply { count += 1 }
                    scale(v, false, {
                        render()
                    })
                }

            }
    )

    private val deleteDragAndDropHelper = DragAndDropHelper(
            onEntered = { v, _, e ->
                doIf(e.clipDescription.label != "add",
                        {
                            scale(v, true)
                        })

            },
            onExited = { v, _, e ->
                doIf(e.clipDescription.label != "add",
                        {
                            scale(v, false)
                        })
            },
            onDrop = { v, dg, e ->
                doIf(e.clipDescription.label != "add",
                        {
                            val element = list.find { it.id == (dg.tag as Int) }
                            list.remove(element)
                            scale(v, false, {
                                render()
                            })
                        })
            }
    )

    private val pojoController = PojoController(controllerDragAndDropHelper)

    private val easyAdapter = EasyAdapter()

    private val list = mutableListOf<SomePojo>()

    init {
        inflateValues()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.layoutManager = GridLayoutManager(this, 3)
        recycler.adapter = easyAdapter
        (recycler.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        increment_view.setOnTouchListener(View.OnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val clipData = ClipData.newPlainText("add", "")
                    val shadowBuilder = View.DragShadowBuilder(increment_view)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        increment_view.startDragAndDrop(clipData, shadowBuilder, increment_view, 0)
                    } else {
                        increment_view.startDrag(clipData, shadowBuilder, increment_view, 0)
                    }
                    return@OnTouchListener true
                }
            }
            false
        })

        delete_tv.setOnDragListener(deleteDragAndDropHelper)

        reset_btn.setOnClickListener {
            inflateValues()
            render()
        }

        render()
    }

    private fun render() {
        easyAdapter.setItems(ItemList.create()
                .addAll(list, pojoController))
    }

    private fun inflateValues() {
        list.clear()
        list.apply {
            for (index in 0..8) {
                add(SomePojo(id = index))
            }
        }
    }

    private fun scale(v: View, upScale: Boolean, f: (() -> Unit)? = null) {
        ViewCompat.animate(v)
                .scaleX(if (upScale) 1.2F else 1F)
                .scaleY(if (upScale) 1.2F else 1F)
                .setDuration(150)
                .withEndAction(f)
                .start()
    }

    private fun doIf(condition: Boolean, f: (() -> Unit)?) {
        if (condition) f?.invoke()
    }

}