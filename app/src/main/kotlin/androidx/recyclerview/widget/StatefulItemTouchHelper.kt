package androidx.recyclerview.widget

class StatefulItemTouchHelper(callback: Callback) : ItemTouchHelper(callback) {

    private var actionState = ACTION_STATE_IDLE
    private var onActionStateChangedListener: OnActionStateChangedListener? = null

    internal override fun select(selected: RecyclerView.ViewHolder?, actionState: Int) {
        if (this.actionState != actionState) {
            if (onActionStateChangedListener != null) {
                onActionStateChangedListener!!.onActionStateChanged(actionState)
            }
        }
        this.actionState = actionState
        super.select(selected, actionState)
    }

    fun setOnActionStateChangedListener(onActionStateChangedListener: OnActionStateChangedListener) {
        this.onActionStateChangedListener = onActionStateChangedListener
    }

    interface OnActionStateChangedListener {
        fun onActionStateChanged(actionState: Int)
    }
}
