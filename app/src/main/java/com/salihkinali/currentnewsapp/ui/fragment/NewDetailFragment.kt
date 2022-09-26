package com.salihkinali.currentnewsapp.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.salihkinali.currentnewsapp.R
import com.salihkinali.currentnewsapp.data.local.ArticleDao
import com.salihkinali.currentnewsapp.data.local.NewsDatabase
import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.data.repository.MainRepository
import com.salihkinali.currentnewsapp.data.service.ApiHelper
import com.salihkinali.currentnewsapp.data.service.RetrofitBuilder
import com.salihkinali.currentnewsapp.databinding.FragmentNewDetailBinding
import com.salihkinali.currentnewsapp.ui.viewmodel.FavoriteViewModel
import com.salihkinali.currentnewsapp.ui.viewmodel.NewsViewModelFactory
import com.salihkinali.currentnewsapp.util.downloadImage


class NewDetailFragment : Fragment() {

    private var _binding: FragmentNewDetailBinding? = null
    private val binding get() = _binding!!
    private val args: NewDetailFragmentArgs by navArgs()
    private val article by lazy { args.article }
    private var isActiveFavorite = false
    private lateinit var factory: NewsViewModelFactory
    private val database by lazy { context?.let { NewsDatabase.getDatabase(it.applicationContext) } }
    private lateinit var dao: ArticleDao
    private lateinit var mainRepository: MainRepository
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao = database?.articleDao()!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupUi()

    }

    private fun deactivateNew() {
        binding.addFavorite.setBackgroundColor(
            ContextCompat.getColor(
                binding.addFavorite.context,
                R.color.orange
            )
        )
        binding.addFavorite.text = getString(R.string.add_to_favorite_list)
    }

    private fun activeNew() {
        binding.addFavorite.setBackgroundColor(
            ContextCompat.getColor(
                binding.addFavorite.context,
                R.color.dark_gray
            )
        )
        binding.addFavorite.text = getString(R.string.added_favorite)
        isActiveFavorite = true
    }

    private fun setupViewModel() {
        mainRepository = MainRepository(ApiHelper(RetrofitBuilder.apiService), dao)
        factory = NewsViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
    }

    private fun setupUi() {

        binding.apply {
            descriptionText.text = article.description
            newsImage.downloadImage(article.image)
            newContext.text = article.content
            sourceName.text = article.source!!.name
            publishedTime.text = article.publishedAt
            isActiveFavorite = args.isFavorite


            sourcePlace.setOnClickListener { _ ->
                val action = article.url?.let { it ->
                    NewDetailFragmentDirections.newDetailToWebViewFragment(it)
                }
                if (action != null) {
                    findNavController().navigate(action)
                }
            }

            if (isActiveFavorite) {
                activeNew()
            } else {
                deactivateNew()
            }
            binding.addFavorite.setOnClickListener {
                favorite()
            }

        }
    }

    private fun favorite() {
        if (isActiveFavorite) {
            isActiveFavorite = false
            article.title?.let { viewModel.deleteNew(it) }
            deactivateNew()
        } else {
            isActiveFavorite = true
            val articleRoom = article.content?.let {
                Article(
                    content = it,
                    description = article.description!!,
                    image = article.image!!,
                    publishedAt = article.publishedAt!!,
                    title = article.title!!,
                    url = article.url!!,
                    source = article.source!!
                )
            }
            if (articleRoom != null) {
                viewModel.insertNew(articleRoom)
            }
            activeNew()
        }
    }
}