package com.salihkinali.currentnewsapp.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.salihkinali.currentnewsapp.R
import com.salihkinali.currentnewsapp.databinding.FragmentOnboardingBinding
import com.salihkinali.currentnewsapp.data.model.OnBoardingItem


class OnboardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!
    private val sharedPref by lazy { requireActivity().getSharedPreferences("boarding", Context.MODE_PRIVATE) }
    private val adapter:OnBoardingAdapter by lazy { OnBoardingAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!onBoarding()){
            navigateHome()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setOnBoardingItems(items())
        setupIndicators()
    }

    private fun setupUi() {

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding.nextButton.setOnClickListener {
            val edit = sharedPref.edit()
            edit.putBoolean("isFirstTime", false)
            edit.apply()
            navigateHome()
        }

    }

    private fun navigateHome() {
        val action = OnboardingFragmentDirections.onboardingToHomeFragment()
        findNavController().navigate(action)
    }

    private fun setupIndicators() {
        val indicator = arrayOfNulls<ImageView>(adapter.currentList.size)
        val layoutParams = LinearLayout.LayoutParams(50, 20)
        layoutParams.setMargins(20, 20, 20, 20)
        for (i in indicator.indices) {
            indicator[i] = ImageView(context)
            indicator[i]?.let {
                it.setImageDrawable(
                    context?.let { ct ->
                        ContextCompat.getDrawable(
                            ct, R.drawable.indicator_inactive_background
                        )
                    }
                )
                it.layoutParams = layoutParams
                binding.indicatorContainer.addView(it)
            }
        }
    }

    private fun setupCurrentIndicator(position: Int) {
        val childCount = binding.indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.indicator_active_background
                        )
                    }
                )
            } else {
                imageView.setImageDrawable(
                    context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.indicator_inactive_background
                        )
                    }
                )
            }
        }
    }

    private fun setOnBoardingItems(list: List<OnBoardingItem>) {
        adapter.submitList(list)
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setupCurrentIndicator(position)
                }
            }
        )
        (binding.viewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
    }

    private fun items(): List<OnBoardingItem> {
        return listOf(
            OnBoardingItem(
                imageId = R.drawable.new_search,
                textTitle = getString(R.string.news_connect),
                textDescription = getString(R.string.first_presentation)
            ),
            OnBoardingItem(
                imageId = R.drawable.new_guide,
                textTitle = getString(R.string.news_find),
                textDescription = getString(R.string.second_presentation)
            ),
            OnBoardingItem(
                imageId = R.drawable.news_connect,
                textTitle = getString(R.string.news_search),
                textDescription = getString(R.string.thirt_presentation)
            )
        )
    }
    private fun onBoarding():Boolean{
        return sharedPref.getBoolean("isFirstTime",true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

}