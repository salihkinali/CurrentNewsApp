package com.salihkinali.currentnewsapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.salihkinali.currentnewsapp.data.local.NewsDatabase
import com.salihkinali.currentnewsapp.databinding.FragmentFavoriteBinding
import com.salihkinali.currentnewsapp.ui.adapter.Adapter
import com.salihkinali.currentnewsapp.ui.adapter.FavoriteAdapter
import com.salihkinali.currentnewsapp.ui.viewmodel.FavoriteViewModel
import com.salihkinali.currentnewsapp.ui.viewmodel.NewViewModelFactory
import com.salihkinali.currentnewsapp.util.Status
import com.salihkinali.currentnewsapp.util.visible

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private val database by lazy { context?.let { NewsDatabase.getDatabase(it.applicationContext) }}
    private val adapter by lazy { FavoriteAdapter() }


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
        viewModel.newList.observe(viewLifecycleOwner){resources ->

            when(resources.status){
                 Status.LOADING -> {
                     binding.savedRecyclerView.visible(false)
                 }
                Status.SUCCESS -> {
                    adapter.submitList(resources.data)
                    binding.savedRecyclerView.visible(true)
                }
                Status.ERROR -> {
                    binding.savedRecyclerView.visible(false)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.savedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.savedRecyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        val factory = database?.let { NewViewModelFactory(it.articleDao()) }
        viewModel = factory?.let { ViewModelProvider(this, it) }!![FavoriteViewModel::class.java]
    }

}