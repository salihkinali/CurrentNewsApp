package com.salihkinali.currentnewsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.databinding.FragmentHomeBinding
import com.salihkinali.currentnewsapp.ui.home.technology.TechnologyAdapter
import com.salihkinali.currentnewsapp.util.Status
import com.salihkinali.currentnewsapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private val techAdapter: TechnologyAdapter by lazy { TechnologyAdapter(::navigateDetailPage) }

    private val adapter: HomeAdapter by lazy { HomeAdapter(::navigateDetailPage) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun setupUI() {
        binding.rvBreaking.layoutManager = LinearLayoutManager(activity)
        binding.rvBreaking.adapter = adapter

        binding.rvTechnology.adapter = techAdapter
        binding.rvTechnology.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    }


    private fun navigateDetailPage(item: Article) {
        val action = HomeFragmentDirections.homeToDetailFragment(item)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}