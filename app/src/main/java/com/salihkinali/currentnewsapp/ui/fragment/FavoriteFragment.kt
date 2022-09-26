package com.salihkinali.currentnewsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.salihkinali.currentnewsapp.R
import com.salihkinali.currentnewsapp.data.local.ArticleDao
import com.salihkinali.currentnewsapp.data.local.NewsDatabase
import com.salihkinali.currentnewsapp.data.repository.MainRepository
import com.salihkinali.currentnewsapp.data.service.ApiHelper
import com.salihkinali.currentnewsapp.data.service.RetrofitBuilder
import com.salihkinali.currentnewsapp.databinding.FragmentFavoriteBinding
import com.salihkinali.currentnewsapp.ui.adapter.Adapter
import com.salihkinali.currentnewsapp.ui.viewmodel.FavoriteViewModel
import com.salihkinali.currentnewsapp.ui.viewmodel.NewsViewModelFactory
import com.salihkinali.currentnewsapp.util.SwipeDeleteCallback
import com.salihkinali.currentnewsapp.util.visible



class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var factory:NewsViewModelFactory
    private val database by lazy { context?.let { NewsDatabase.getDatabase(it.applicationContext) }}
    private lateinit var dao: ArticleDao
    private lateinit var mainRepository: MainRepository
    private val adapter by lazy { Adapter(requireContext()) {
        val action = FavoriteFragmentDirections.favoriteToNewDetailFragment(it,true)
        findNavController().navigate(action)
    }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao = database?.articleDao()!!
    }


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
        setupViewModel()
        setupObserver()

    }

    private fun setupObserver() {
        val itemTouchHelper = object :SwipeDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val article = adapter.currentList[position]
                viewModel.deleteFromFavorite(article)

                view?.let {
                    Snackbar.make(it, "Article deleted successfully", Snackbar.LENGTH_SHORT).apply {
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

    private fun setupViewModel() {
        mainRepository = MainRepository(ApiHelper(RetrofitBuilder.apiService), dao)
        factory = NewsViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
    }

}