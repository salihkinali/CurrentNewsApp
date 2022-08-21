package com.salihkinali.currentnewsapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.salihkinali.currentnewsapp.R
import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.databinding.FragmentNewDetailBinding
import com.salihkinali.currentnewsapp.util.downloadImage


class NewDetailFragment : Fragment() {
    private var _binding: FragmentNewDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var article: Article
    private val args: NewDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        article = args.article
        binding.apply {
            descriptionText.text = article.description
            newsImage.downloadImage(article.image)
            newContext.text = article.content
            sourceName.text = article.source.name
            publishedTime.text = article.publishedAt
            sourcePlace.setOnClickListener {
                showBottomSheetDialog(article.url)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun showBottomSheetDialog(url:String) {
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.fragment_bottom_sheet)
        val webview = dialog.findViewById<WebView>(R.id.webView)
        if (webview != null) {
            webview.settings.javaScriptEnabled = true
            webview.loadUrl(url)
        }
        dialog.dismiss()
        dialog.show()
    }

}