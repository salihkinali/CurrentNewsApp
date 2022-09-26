package com.salihkinali.currentnewsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.salihkinali.currentnewsapp.data.local.ArticleDao
import com.salihkinali.currentnewsapp.data.local.NewsDatabase
import com.salihkinali.currentnewsapp.data.repository.MainRepository
import com.salihkinali.currentnewsapp.data.service.ApiHelper
import com.salihkinali.currentnewsapp.data.service.RetrofitBuilder
import com.salihkinali.currentnewsapp.databinding.FragmentSearchBinding
import com.salihkinali.currentnewsapp.ui.adapter.Adapter
import com.salihkinali.currentnewsapp.ui.viewmodel.NewsViewModelFactory
import com.salihkinali.currentnewsapp.ui.viewmodel.SearchViewModel
import com.salihkinali.currentnewsapp.util.Status
import com.salihkinali.currentnewsapp.util.visible


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private lateinit var factory: NewsViewModelFactory
    private val database by lazy { context?.let { NewsDatabase.getDatabase(it.applicationContext) } }
    private lateinit var dao: ArticleDao
    private lateinit var mainRepository: MainRepository
    private val adapter by lazy {
        Adapter(requireContext()){
            val action = SearchFragmentDirections.searchToNewDetailFragment(it)
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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getResultFilter()
        setupViewModel()
        setupObserver()
        setupUi()
    }

    private fun setupViewModel() {
        mainRepository = MainRepository(ApiHelper(RetrofitBuilder.apiService), dao)
        factory = NewsViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]
    }

    private fun setupUi() {
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.searchRecyclerView.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.status.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.loading.visible(false)
                    if (it.data?.totalArticles != 0) {
                        binding.searchRecyclerView.visible(true)
                        binding.alertSymbol.visible(false)
                        binding.alertText.visible(false)
                        adapter.submitList(it.data!!.articles)
                    }else{
                        binding.searchRecyclerView.visible(false)
                        binding.alertSymbol.visible(true)
                        binding.alertText.visible(true)
                    }
                }
                Status.ERROR -> {
                    binding.loading.visible(false)
                    binding.alertSymbol.visible(false)
                    binding.alertText.visible(false)
                }
                Status.LOADING -> {
                    binding.loading.visible(true)
                    binding.alertSymbol.visible(false)
                    binding.alertText.visible(false)
                    binding.searchRecyclerView.visible(false)

                }
           }

       }
    }

    private fun getResultFilter() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.searchQuery(p0)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
    }

}