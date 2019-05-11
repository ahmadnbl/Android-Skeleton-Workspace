package id.ahmadnbl.skeletonproject.component

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RvScrollPagingHandler(
        private val delegate: EventDelegate,
        private val loadThreshold: Int = 3)
    : RecyclerView.OnScrollListener() {

    /**
     * Send back event to delegate about next item will be required
     */
    interface EventDelegate {
        fun onRequiredNextItem()
    }

    var isLoading = false
    var isLastPage = true

    /**
     * Read the item position for handling the paging
     */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.layoutManager?.childCount ?: 0
        val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0

        // getting visible item position
        val llMgr = recyclerView.layoutManager
        val firstVisibleItemPosition = if (llMgr is LinearLayoutManager) {
            llMgr.findFirstVisibleItemPosition()
        } else if (llMgr is GridLayoutManager) {
            llMgr.findFirstVisibleItemPosition()
        } else {
            -1
        }

        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition + loadThreshold >= totalItemCount
                    && firstVisibleItemPosition >= 0
//                    && totalItemCount >= PAGE_SIZE
            ) {
                delegate.onRequiredNextItem()
            }
        }
    }
}