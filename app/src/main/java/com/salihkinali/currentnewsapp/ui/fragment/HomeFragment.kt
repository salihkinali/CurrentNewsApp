package com.salihkinali.currentnewsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.salihkinali.currentnewsapp.databinding.FragmentHomeBinding
import com.salihkinali.currentnewsapp.ui.adapter.Adapter
import com.salihkinali.currentnewsapp.ui.viewmodel.NewsViewModel
import com.salihkinali.currentnewsapp.util.Status
import com.salihkinali.currentnewsapp.util.visible


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsViewModel
    private val adapter by lazy {
        Adapter(requireContext()) { article ->
            val action = HomeFragmentDirections.homeToDetailFragment(article)
            findNavController().navigate(action)
        }
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
                        binding.recylerView.visible(true)
                        binding.progressBar.visible(false)
                        resource.data?.let { adapter.submitList(it.articles) }
                    }
                    Status.ERROR -> {
                        binding.recylerView.visible(true)
                        binding.progressBar.visible(false)
                        Toast.makeText(requireActivity(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    Status.LOADING -> {
                        binding.recylerView.visible(false)
                        binding.progressBar.visible(true)
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }

    private fun setupUI() {
        binding.recylerView.layoutManager = LinearLayoutManager(activity)
        binding.recylerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}