package com.salihkinali.currentnewsapp.ui.favorite

import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.databinding.NewsCardDesignBinding
import com.salihkinali.currentnewsapp.ui.base.BaseViewHolder
import com.salihkinali.currentnewsapp.util.downloadImage
import com.salihkinali.currentnewsapp.util.timeConversion

class FavoriteViewHolder(
    private val binding: NewsCardDesignBinding,
    private val onItemClickListener: ((item: Article) -> Unit)?
) : BaseViewHolder<Article>(binding.root) {


    override fun onBind(data: Article) {
        binding.apply {
            titleText.text = data.title
            shapeableImageView.downloadImage(data.image)
            publishedTime.text = data.publishedAt?.let {
                it.timeConversion(it)
            }
            materialCardView.setOnClickListener { onItemClickListener?.invoke(data) }
        }
    }
}