package com.logo.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.logo.R
import com.logo.data.model.search.SearchHistory
import com.logo.databinding.ItemHeadlineHeaderBinding
import com.logo.databinding.ItemSearchHistoryBinding
import com.logo.ui.base.BaseAdapter

class SearchHistoryAdapter(private val listener: SearchHistoryListener) :
    BaseAdapter<SearchHistory>() {
    private companion object {
        const val HEADER = 1
        const val HISTORY = 2
    }

    override fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == HISTORY) {
            SearchHistoryViewHolder(ItemSearchHistoryBinding.inflate(inflater, parent, false))
        } else {
            HeaderViewHolder(ItemHeadlineHeaderBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindItemHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchHistoryViewHolder -> {
                holder.bind(items[position])
            }
            is HeaderViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return HEADER
        }
        return HISTORY
    }

    override fun getItemCount() = items.size

    private inner class SearchHistoryViewHolder(private val binding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(items[bindingAdapterPosition])
            }
        }

        fun bind(searchHistory: SearchHistory) {
            binding.model = searchHistory
            binding.executePendingBindings()
        }
    }

    private inner class HeaderViewHolder(private val binding: ItemHeadlineHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.textView.text = binding.root.context.getString(R.string.search_history)
        }
    }
}

interface SearchHistoryListener {
    fun onItemClick(searchHistory: SearchHistory)
}