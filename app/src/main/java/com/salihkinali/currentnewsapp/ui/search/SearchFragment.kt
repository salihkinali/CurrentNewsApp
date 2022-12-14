package com.salihkinali.currentnewsapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.databinding.FragmentSearchBinding
import com.salihkinali.currentnewsapp.util.Status
import com.salihkinali.currentnewsapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val adapter:SearchAdapter by lazy { SearchAdapter(::navigateDetailPage)

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
        setupObserver()
        setupUi()
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


    private fun navigateDetailPage(item: Article) {
        val action = SearchFragmentDirections.searchToNewDetailFragment(item)
        findNavController().navigate(action)
    }

}