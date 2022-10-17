package com.salihkinali.currentnewsapp.ui.adapter.technology

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.databinding.ItemHorizontalDesignBinding
import com.salihkinali.currentnewsapp.util.downloadImage

class TechAdapter(private val itemClick: (Article) -> Unit):ListAdapter<Article,TechAdapter.ViewHolder>(DiffUtilCallBack){
    class ViewHolder(val binding: ItemHorizontalDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article) {
            binding.apply {
                newsImage.downloadImage(item.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemHorizontalDesignBinding = ItemHorizontalDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemHorizontalDesignBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.root.setOnClickListener{
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