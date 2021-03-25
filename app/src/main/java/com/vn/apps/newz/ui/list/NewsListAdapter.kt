package com.vn.apps.newz.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.vn.apps.newz.R
import com.vn.apps.newz.data.entities.ArticlesItem
import com.vn.apps.newz.databinding.LayoutNewsListItemBinding


class NewsListAdapter(private val listener: NewsItemListener) :
    RecyclerView.Adapter<NewsItemViewHolder>() {

    interface NewsItemListener {
        fun onSeeFullNewsButtonClicked(url: String, title: String)
    }

    private val items = ArrayList<ArticlesItem>()

    fun setItems(items: ArrayList<ArticlesItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val binding: LayoutNewsListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_news_list_item,
            parent,
            false
        )
        return NewsItemViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) =
        holder.bind(items[position])
}

class NewsItemViewHolder(
    private val itemBinding: LayoutNewsListItemBinding,
    private val listener: NewsListAdapter.NewsItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var articlesItem: ArticlesItem

    init {
        itemBinding.btnSeeFillNews.setOnClickListener(this)
    }

    fun bind(item: ArticlesItem) {
        this.articlesItem = item
        itemBinding.txtAuthorName.text = item.author
        itemBinding.txtNewsTitle.text = item.title
        itemBinding.txtNewsContent.text = item.content
        Glide.with(itemBinding.root)
            .load(item.urlToImage)
            .placeholder(R.drawable.ic_image_paceholder)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(28)))
            .into(itemBinding.imgNewsItem)
    }

    override fun onClick(v: View?) {
        val url = articlesItem.url
        val title = articlesItem.url
        if (url != null && title != null) {
            listener.onSeeFullNewsButtonClicked(url, title)
        }
    }
}

