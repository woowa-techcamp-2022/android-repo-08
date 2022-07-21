package com.example.reposearchapp.presentation.adapter.notification

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.reposearchapp.R

class NotificationItemSwipeHelper(
    private val context: Context,
    private val background: ColorDrawable = ColorDrawable(
        ContextCompat.getColor(
            context,
            R.color.primary
        )
    ),
    private val icon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_check)
) : ItemTouchHelper.Callback() {

    // swipe 시 호출될 listener
    private lateinit var onSwipedListener: (Int) -> Unit

    // drag 와 swipe 의 위치 결정
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        // 오른쪽에서 왼쪽으로 이동하는 swipe 만 가능하도록 설정
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    // drag 시 호출되므로 무시
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition

        if (::onSwipedListener.isInitialized) {
            onSwipedListener(position)
        }
    }

    fun setOnSwipedListener(listener: (Int) -> Unit) {
        this.onSwipedListener = listener
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        if (dX < 0 && actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val item = viewHolder.itemView
            background.setBounds(item.right + dX.toInt(), item.top, item.right, item.bottom)
            background.draw(canvas)

            icon?.let {
                if (dX < -icon.intrinsicWidth * 4) {
                    val iconTop = item.top + (item.height - icon.intrinsicHeight) / 2
                    val iconBottom = iconTop + icon.intrinsicHeight
                    val iconLeft = item.right + dX.toInt() / 2
                    val iconRight = iconLeft + icon.intrinsicWidth
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    icon.draw(canvas)
                }
            }
        }
    }
}