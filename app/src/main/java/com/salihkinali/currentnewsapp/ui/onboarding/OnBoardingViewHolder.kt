package com.salihkinali.currentnewsapp.ui.onboarding

import com.salihkinali.currentnewsapp.data.model.OnBoardingItem
import com.salihkinali.currentnewsapp.databinding.ItemContainerViewBinding
import com.salihkinali.currentnewsapp.ui.base.BaseViewHolder

class OnBoardingViewHolder(private val binding: ItemContainerViewBinding) :
    BaseViewHolder<OnBoardingItem>(binding.root) {
    override fun onBind(data: OnBoardingItem) {
        binding.apply {
            imageView.setImageResource(data.imageId)
            onBoardingTitle.text = data.textTitle
            descriptionText.text = data.textDescription
        }
    }
}