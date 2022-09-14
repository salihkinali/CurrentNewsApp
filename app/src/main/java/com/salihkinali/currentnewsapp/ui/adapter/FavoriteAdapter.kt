package com.salihkinali.currentnewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.salihkinali.currentnewsapp.data.model.ArticleRoom
import com.salihkinali.currentnewsapp.databinding.NewsCardDesignBinding
import com.salihkinali.currentnewsapp.util.downloadImage

class FavoriteAdapter :
    ListAdapter<ArticleRoom, FavoriteAdapter.ViewHolder>(DiffUtilCallBack) {
    class ViewHolder(val cardDesignBinding: NewsCardDesignBinding) :
        RecyclerView.ViewHolder(cardDesignBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardDesignBinding =
            NewsCardDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(cardDesignBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.cardDesignBinding.apply {
            titleText.text = item.title
            shapeableImageView.downloadImage(item.image)
            publishedTime.text = item.publishedAt
        }
    }

    companion object DiffUtilCallBack : DiffUtil.ItemCallback<ArticleRoom>() {
        override fun areItemsTheSame(oldItem: ArticleRoom, newItem: ArticleRoom): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArticleRoom, newItem: ArticleRoom): Boolean {
            return oldItem == newItem
        }
    }
}