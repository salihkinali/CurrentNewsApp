package com.salihkinali.currentnewsapp.ui.home.technology

import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.databinding.ItemHorizontalDesignBinding
import com.salihkinali.currentnewsapp.ui.base.BaseViewHolder
import com.salihkinali.currentnewsapp.util.downloadImage

class TechnologyViewHolder(
    private val binding:ItemHorizontalDesignBinding,
    private val onItemClickListener: ((item: Article) -> Unit)?): BaseViewHolder<Article>(binding.root) {

    override fun onBind(data: Article) {
        binding.apply {
            newsImage.downloadImage(data.image)
            technologyCardView.setOnClickListener { onItemClickListener?.invoke(data) }
        }
    }
}