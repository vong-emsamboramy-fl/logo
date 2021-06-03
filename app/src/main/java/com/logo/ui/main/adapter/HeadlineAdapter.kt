package com.logo.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.logo.data.model.headline.Article
import com.logo.databinding.ItemHeadlineBinding
import com.logo.databinding.ItemHeadlineHeaderBinding
import com.logo.ui.base.BaseAdapter
import retrofit2.http.Header

class HeadlineAdapter : BaseAdapter<Article>() {
    private companion object {
        const val HEADER = 1
        const val ARTICLE = 2
    }

    override fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == ARTICLE) {
            ArticleViewHolder(ItemHeadlineBinding.inflate(inflater, parent, false))
        } else {
            HeaderViewHolder(ItemHeadlineHeaderBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindItemHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder) {
            holder.bind(items[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return HEADER
        }
        return ARTICLE
    }

    override fun getItemCount() = items.size

    private inner class ArticleViewHolder(private val binding: ItemHeadlineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            Glide.with(binding.imageView.context)
                .load(article.image)
                .into(binding.imageView)
            binding.textViewHeadlineTitle.text = article.title
            binding.textViewArticleDescription.text = article.description
        }
    }

    private inner class HeaderViewHolder(private val binding: ItemHeadlineHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)
}