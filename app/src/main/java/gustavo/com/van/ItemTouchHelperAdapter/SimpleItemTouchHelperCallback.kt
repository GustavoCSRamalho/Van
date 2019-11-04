package gustavo.com.van.ItemTouchHelperAdapter

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags
import androidx.recyclerview.widget.ItemTouchHelper
import gustavo.com.van.adapter.VanListAdapter


class SimpleItemTouchHelperCallback(private val mAdapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    fun getMovementFlags(recyclerView: RecyclerView, viewHolder: VanListAdapter.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    fun onMove(
        recyclerView: RecyclerView, viewHolder: VanListAdapter.ViewHolder,
        target: VanListAdapter.ViewHolder
    ): Boolean {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition())
        return true
    }

    fun onSwiped(viewHolder: VanListAdapter.ViewHolder, direction: Int) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition())
    }

}