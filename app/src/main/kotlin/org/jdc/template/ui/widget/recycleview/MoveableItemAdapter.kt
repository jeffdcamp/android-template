package org.jdc.template.ui.widget.recycleview

interface MoveableItemAdapter {
    fun move(originalPosition: Int, endPosition: Int)
}