package com.salihkinali.currentnewsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.databinding.NewsCardDesignBinding
import com.salihkinali.currentnewsapp.util.downloadImage

class Adapter(private val context: Context,private val itemClick: (Article) -> Unit):ListAdapter<Article,Adapter.ViewHolder>(DiffUtilCallBack){
    class ViewHolder(val cardDesignBinding: NewsCardDesignBinding) :
        RecyclerView.ViewHolder(cardDesignBinding.root) {

        fun bind(item: Article) {
            cardDesignBinding.apply {
                titleText.text = item.title
                shapeableImageView.downloadImage(item.image)
                publishedTime.text = item.publishedAt
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val cardDesignBinding = NewsCardDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(cardDesignBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.cardDesignBinding.root.setOnClickListener {
            itemClick.invoke(item)
        }

    }
    companion object DiffUtilCallBack : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}