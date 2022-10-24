package com.salihkinali.currentnewsapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.databinding.NewsCardDesignBinding
import com.salihkinali.currentnewsapp.ui.base.BaseListAdapter
import com.salihkinali.currentnewsapp.ui.home.HomeViewHolder

class FavoriteAdapter(private val onItemClickListener: ((Article)-> Unit)?) : BaseListAdapter<Article>(
    itemsSame = { old, new -> old.url == new.url },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            NewsCardDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding,onItemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is FavoriteViewHolder -> {
                getItem(position)?.let { item ->
                    holder.onBind(item)
                }
            }
        }
    }
}