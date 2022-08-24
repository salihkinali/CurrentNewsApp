package com.salihkinali.currentnewsapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.salihkinali.currentnewsapp.databinding.FragmentSearchBinding
import com.salihkinali.currentnewsapp.ui.adapter.SearchAdapter
import com.salihkinali.currentnewsapp.ui.viewmodel.SearchViewModel
import com.salihkinali.currentnewsapp.util.Status
import com.salihkinali.currentnewsapp.util.visible


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private val adapter by lazy {
        SearchAdapter{
            val action = SearchFragmentDirections.searchToNewDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
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
       viewModel.status.observe(viewLifecycleOwner){resource ->
           when(resource.status){
               Status.SUCCESS -> {
                   binding.loading.visible(false)
                   binding.searchView.visible(true)
                   if(resource.data != null){
                       adapter.submitList(resource.data.articles)
                   }else{
                      Toast.makeText(requireContext(),"Data is Null",Toast.LENGTH_SHORT).show()
                   }
               }
               Status.ERROR -> {
                   binding.loading.visible(false)
                   binding.searchView.visible(false)
                   Toast.makeText(requireContext(), "There is problem from data", Toast.LENGTH_SHORT).show()
               }
               Status.LOADING ->{
                   binding.loading.visible(true)
                   binding.searchView.visible(false)
               }
           }

       }
    }

    private fun getResultFilter() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.nonResult.text = p0
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.searchQuery(query)
                return true
            }
        })
    }

}