package com.salihkinali.currentnewsapp.ui.home.technology

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.databinding.ItemHorizontalDesignBinding
import com.salihkinali.currentnewsapp.ui.base.BaseListAdapter

class TechnologyAdapter(private val onItemClickListener: ((Article) -> Unit)?) :
    BaseListAdapter<Article>(
        itemsSame = { old, new -> old.url == new.url },
        contentsSame = { old, new -> old == new }
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            ItemHorizontalDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TechnologyViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TechnologyViewHolder -> {
                getItem(position)?.let { item ->
                    holder.onBind(item)
                }
            }
        }
    }
}
