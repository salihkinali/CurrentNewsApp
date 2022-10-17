package com.salihkinali.currentnewsapp.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.salihkinali.currentnewsapp.data.local.ArticleDao
import com.salihkinali.currentnewsapp.data.local.NewsDatabase
import com.salihkinali.currentnewsapp.data.repository.MainRepository
import com.salihkinali.currentnewsapp.data.service.ApiHelper
import com.salihkinali.currentnewsapp.data.service.RetrofitBuilder
import com.salihkinali.currentnewsapp.databinding.FragmentHomeBinding
import com.salihkinali.currentnewsapp.ui.adapter.Adapter
import com.salihkinali.currentnewsapp.ui.adapter.technology.TechAdapter
import com.salihkinali.currentnewsapp.ui.fragment.factory.NewsViewModelFactory
import com.salihkinali.currentnewsapp.util.Status
import com.salihkinali.currentnewsapp.util.visible


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsViewModel
    private lateinit var factory: NewsViewModelFactory
    private val database by lazy { context?.let { NewsDatabase.getDatabase(it.applicationContext) } }
    private lateinit var dao: ArticleDao
    private lateinit var mainRepository: MainRepository
    private val techAdapter by lazy { TechAdapter {
        val action = HomeFragmentDirections.homeToDetailFragment(it)
        findNavController().navigate(action)
    }
    }
    private val adapter by lazy {
        Adapter { article ->
            val action = HomeFragmentDirections.homeToDetailFragment(article)
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()
        setupUI()

    }

    private fun setupObservers() {
        viewModel.responseNews.observe(viewLifecycleOwner) { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visible(false)
                        binding.breakingNews.visible(true)
                        binding.technologyNews.visible(true)

                        resource.data?.let { adapter.submitList(it[0].articles)}
                        techAdapter.submitList(resource.data?.get(1)?.articles)
                    }
                    Status.ERROR -> {
                        binding.progressBar.visible(false)
                        Toast.makeText(requireActivity(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visible(true)
                        binding.breakingNews.visible(false)
                        binding.technologyNews.visible(false)
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        mainRepository = MainRepository(ApiHelper(RetrofitBuilder.apiService), dao)
        factory = NewsViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]
    }

    private fun setupUI() {
        binding.rvBreaking.layoutManager = LinearLayoutManager(activity)
        binding.rvBreaking.adapter = adapter

        binding.rvTechnology.adapter = techAdapter
        binding.rvTechnology.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}