package com.salihkinali.currentnewsapp.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salihkinali.currentnewsapp.data.model.OnBoardingItem
import com.salihkinali.currentnewsapp.databinding.ItemContainerViewBinding
import com.salihkinali.currentnewsapp.ui.base.BaseListAdapter


class OnBoardingAdapter : BaseListAdapter<OnBoardingItem>(
    itemsSame = {old,new -> old.imageId == new.imageId},
    contentsSame = {old,new -> old == new}
){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            ItemContainerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnBoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       when(holder){
           is OnBoardingViewHolder -> {
               getItem(position)?.let { item ->
                   holder.onBind(item)
               }
           }
       }
    }


}