package com.salihkinali.currentnewsapp.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.salihkinali.currentnewsapp.R
import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.databinding.FragmentFavoriteBinding
import com.salihkinali.currentnewsapp.util.SwipeDeleteCallback
import com.salihkinali.currentnewsapp.util.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter: FavoriteAdapter by lazy { FavoriteAdapter(::navigateDetailPage) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObserver()
    }

    private fun setupObserver() {
        val itemTouchHelper = object :SwipeDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val article = adapter.currentList[position]
                viewModel.deleteFromFavorite(article)

                view?.let {
                    Snackbar.make(it, getString(R.string.deleted_article), Snackbar.LENGTH_SHORT).apply {
                        anchorView = it.rootView.findViewById(R.id.bottomNavigation)
                        show()
                    }
                }

            }
        }

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.savedRecyclerView)
        }

        viewModel.getList().observe(viewLifecycleOwner){articles ->

            if(articles.isNullOrEmpty()){
                binding.savedRecyclerView.visible(false)
            }else{
                adapter.submitList(articles)
                binding.savedRecyclerView.visible(true)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.savedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.savedRecyclerView.adapter = adapter
    }

    @SuppressLint("SuspiciousIndentation")
    private fun navigateDetailPage(article: Article) {
      val action = FavoriteFragmentDirections.favoriteToNewDetailFragment(article,true)
        findNavController().navigate(action)
    }

}