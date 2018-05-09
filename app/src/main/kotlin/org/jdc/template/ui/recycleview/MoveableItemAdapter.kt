package org.jdc.template.ui.recycleview

interface MoveableItemAdapter {
    fun move(originalPosition: Int, endPosition: Int)
}