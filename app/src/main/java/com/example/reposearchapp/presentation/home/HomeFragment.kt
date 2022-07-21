package com.example.reposearchapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.example.reposearchapp.R
import com.example.reposearchapp.databinding.FragmentHomeBinding
import com.example.reposearchapp.presentation.adapter.home.HomeViewPagerAdapter
import com.example.reposearchapp.presentation.base.BaseFragment
import com.example.reposearchapp.presentation.home.issue.IssueFragment
import com.example.reposearchapp.presentation.home.notification.NotificationFragment
import com.example.reposearchapp.presentation.profile.ProfileFragment
import com.example.reposearchapp.presentation.profile.ProfileUiState
import com.example.reposearchapp.presentation.profile.ProfileViewModel
import com.example.reposearchapp.presentation.search.SearchFragment
import com.example.reposearchapp.util.setCircleImageFromImageUrl
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    // tab 에 보여지는
    private val tabTitleList = listOf(R.string.text_tab_issue, R.string.text_tab_noti)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        initViews()
        observeState()
    }

    private fun initViews() {
        binding.btnSearch.setOnClickListener {
            navigateToSearchFragment()
        }
    }

    private fun bindViews() = with(binding) {
        viewPager.adapter = HomeViewPagerAdapter(
            this@HomeFragment,
            listOf(
                IssueFragment.newInstance(),
                NotificationFragment.newInstance()
            )
        )
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = requireContext().getString(tabTitleList[position])
        }.attach()
        // 프로필 이미지 클릭
        ivProfile.setOnClickListener {
            navigateToProfileFragment()
        }

    }

    private fun observeState() {
        profileViewModel.profileUiState.observe(viewLifecycleOwner) {
            when (it) {
                is ProfileUiState.Loading -> {
                }
                is ProfileUiState.Success -> {
                    binding.ivProfile.setCircleImageFromImageUrl(it.user.avatarUrl)
                }
                is ProfileUiState.Error -> {
                    println(it.message)
                }
            }
        }
    }

    private fun navigateToProfileFragment() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(SearchFragment.TAG)
            replace(R.id.fragment_container_main, ProfileFragment(), ProfileFragment.TAG)
        }
    }

    private fun navigateToSearchFragment() {
        val f = parentFragmentManager.findFragmentByTag(SearchFragment.TAG)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(SearchFragment.TAG)
            f?.let { replace(R.id.fragment_container_main, it, SearchFragment.TAG) }
                ?: replace(R.id.fragment_container_main, SearchFragment(), SearchFragment.TAG)
        }
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}