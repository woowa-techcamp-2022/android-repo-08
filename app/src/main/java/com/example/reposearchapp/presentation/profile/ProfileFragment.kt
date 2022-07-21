package com.example.reposearchapp.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.reposearchapp.R
import com.example.reposearchapp.databinding.FragmentProfileBinding
import com.example.reposearchapp.presentation.base.BaseFragment

/**
 * 페어 프로그래밍
 */
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val profileViewModel by activityViewModels<ProfileViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeState()
    }

    private fun initView() = with(binding) {
        ivBackProfile.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeState() {
        profileViewModel.profileUiState.observe(viewLifecycleOwner) {
            when (it) {
                is ProfileUiState.Loading -> {

                }
                is ProfileUiState.Success -> {
                    binding.profile = it.user
                }
                is ProfileUiState.Error -> {
                }
            }
        }
    }

    companion object {
        const val TAG = "ProfileFragment"
    }
}