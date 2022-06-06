package com.pakollya.moviecollection.presentation.adapter.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalItemDecoration(
    private val innerDivider: Int,
    private val outerDivider: Int,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val adapter = parent.adapter ?: return
        val currentPosition = parent.getChildAdapterPosition(view).takeIf { it != RecyclerView.NO_POSITION } ?: return

        val isPrevView = adapter.isPrevView(currentPosition)
        val isNextView = adapter.isNextView(currentPosition)

        val oneSideInnerDivider = innerDivider/2

        with(outRect) {
            top = if (isPrevView) oneSideInnerDivider else outerDivider
            bottom = if (isNextView) oneSideInnerDivider else outerDivider
        }
    }

    private fun RecyclerView.Adapter<*>.isPrevView(currentPosition: Int) = currentPosition != 0

    private fun RecyclerView.Adapter<*>.isNextView(currentPosition: Int): Boolean {
        val lastIndex = itemCount - 1
        return currentPosition != lastIndex
    }
}