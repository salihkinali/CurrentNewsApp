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
import com.salihkinali.currentnewsapp.data.local.NewsDatabase
import com.salihkinali.currentnewsapp.data.model.ArticleRoom
import com.salihkinali.currentnewsapp.databinding.FragmentNewDetailBinding
import com.salihkinali.currentnewsapp.ui.viewmodel.FavoriteViewModel
import com.salihkinali.currentnewsapp.ui.viewmodel.NewViewModelFactory
import com.salihkinali.currentnewsapp.util.downloadImage


class NewDetailFragment : Fragment() {

    private var _binding: FragmentNewDetailBinding? = null
    private val binding get() = _binding!!
    private val args: NewDetailFragmentArgs by navArgs()
    private val article by lazy { args.article }
    private var isActiveFavorite = false
    private lateinit var database: NewsDatabase
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewDetailBinding.inflate(inflater, container, false)
        database = context?.let { NewsDatabase.getDatabase(it.applicationContext) }!!
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
        val factory = NewViewModelFactory(database.articleDao())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
    }

    private fun setupUi() {

        binding.apply {
            descriptionText.text = article.description
            newsImage.downloadImage(article.image)
            newContext.text = article.content
            sourceName.text = article.source.name
            publishedTime.text = article.publishedAt

            sourcePlace.setOnClickListener {
                val action = NewDetailFragmentDirections.newDetailToWebViewFragment(article.url)
                findNavController().navigate(action)
            }
            val checkedNew = viewModel.isFavorite(article.title)

            if (checkedNew) activeNew() else deactivateNew()

            addFavorite.setOnClickListener { favorite() }
        }
    }

    private fun favorite() {
        if (isActiveFavorite) {
            isActiveFavorite = false
            viewModel.deleteNew(article.title)
            deactivateNew()
        } else {
            isActiveFavorite = true
            val articleRoom = ArticleRoom(
                content = article.content,
                description = article.description,
                image = article.image,
                publishedAt = article.publishedAt,
                title = article.title,
                url = article.url
            )
            viewModel.insertNew(articleRoom)
            activeNew()
        }
    }
}