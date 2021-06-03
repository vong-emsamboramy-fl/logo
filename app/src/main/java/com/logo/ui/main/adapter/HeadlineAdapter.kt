package com.logo.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.logo.data.model.headline.Article
import com.logo.databinding.ItemHeadlineBinding
import com.logo.ui.base.BaseAdapter

class HeadlineAdapter : BaseAdapter<Article>() {
    override fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return ArticleViewHolder(ItemHeadlineBinding.inflate(inflater, parent, false))
    }

    override fun onBindItemHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ArticleViewHolder).bind(items[position])
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
}