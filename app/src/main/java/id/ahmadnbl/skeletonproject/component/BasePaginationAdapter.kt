package id.ahmadnbl.skeletonproject.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ahmadnbl.skeletonproject.R
import kotlinx.android.synthetic.main.l_pagination_loading_item.view.*

abstract class BasePaginationAdapter(
        private val delegate: PaginationAdapterEventDelegate?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        /**
         * value for indicating what is the item type
         */
        const val ITEM_TYPE = 0
        const val LOADING_TYPE = 9999
    }

    private var useLoadMoreAction = false
    private var loadMoreCaption: String? = null
    var isLoading = true
    var isErrorLoadNextItem = false
    var isHasNextPage = false
        set(value) { // field: backing field   ;   value: new value
            if (field && !value) {
                field = value
                notifyItemRemoved(itemCount)
            } else if (!field && value) {
                field = value
                notifyItemInserted(itemCount)
            }
        }

    abstract fun onCreateItemHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    fun getItemCountForPaging(totalItemCount: Int): Int =
            if (isErrorLoadNextItem || isHasNextPage) {
                totalItemCount + 1 // add 1 item for loading item
            } else {
                totalItemCount
            }

    fun setUserLoadMoreAction(value: Boolean, text: String?){
        this.useLoadMoreAction = value
        this.loadMoreCaption = text
        this.isLoading = false
    }

    override fun getItemViewType(position: Int): Int =
            if (position == itemCount - 1 && (isErrorLoadNextItem || isHasNextPage)) {
                LOADING_TYPE
            } else {
                ITEM_TYPE
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            if (viewType == LOADING_TYPE) {
                LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.l_pagination_loading_item, parent, false))
            } else {
                onCreateItemHolder(parent, viewType)
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            LOADING_TYPE -> {
                if (isErrorLoadNextItem) {
                    holder.itemView.pagination_loading_item_load_more_action_btn.visibility = View.GONE
                    holder.itemView.pagination_loading_item_error_layout.visibility = View.VISIBLE
                    holder.itemView.pagination_loading_item_loading_layout.visibility = View.GONE

                } else  if (!isLoading && useLoadMoreAction) {
                    holder.itemView.pagination_loading_item_load_more_action_btn.visibility = View.VISIBLE
                    holder.itemView.pagination_loading_item_error_layout.visibility = View.GONE
                    holder.itemView.pagination_loading_item_loading_layout.visibility = View.GONE
                    loadMoreCaption?.let {
                        holder.itemView.pagination_loading_item_load_more_action_btn.text = it
                    } ?: {
                        holder.itemView.pagination_loading_item_load_more_action_btn.text =
                                holder.itemView.context.getString(R.string.load_more)
                    }()
                } else {
                    holder.itemView.pagination_loading_item_load_more_action_btn.visibility = View.GONE
                    holder.itemView.pagination_loading_item_error_layout.visibility = View.GONE
                    holder.itemView.pagination_loading_item_loading_layout.visibility = View.VISIBLE
                }
            }
        }
    }


    /**
     * View holder that holding view for loading item
     */

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.pagination_loading_item_error_try_btn.setOnClickListener {
                delegate?.onRetryClicked()
            }

            itemView.pagination_loading_item_load_more_action_btn.setOnClickListener {
                delegate?.onRetryClicked()
                isLoading = true
                notifyItemChanged(adapterPosition)
            }
        }
    }

    /**
     * Interface for handling pagination adapter delegate
     */
    interface PaginationAdapterEventDelegate {
        fun onRetryClicked()
    }

}