package com.salihkinali.currentnewsapp.ui.adapter.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.salihkinali.currentnewsapp.databinding.ItemContainerViewBinding


class OnBoardingAdapter :
    ListAdapter<OnBoardingItem, OnBoardingAdapter.ViewHolder>(DiffUtilCallBack) {

    class ViewHolder(private val binding: ItemContainerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OnBoardingItem) {
            binding.apply {
                imageView.setImageResource(item.imageId)
                onBoardingTitle.text = item.textTitle
                descriptionText.text = item.textDescription
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemContainerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffUtilCallBack : DiffUtil.ItemCallback<OnBoardingItem>() {
        override fun areItemsTheSame(oldItem: OnBoardingItem, newItem: OnBoardingItem): Boolean {
            return oldItem.imageId == newItem.imageId
        }

        override fun areContentsTheSame(oldItem: OnBoardingItem, newItem: OnBoardingItem): Boolean {
            return oldItem == newItem
        }
    }
}