package com.example.reposearchapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reposearchapp.data.repository.ProfileRepository
import com.example.reposearchapp.model.profile.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _profileUiState = MutableLiveData<ProfileUiState>(ProfileUiState.Loading)
    val profileUiState: LiveData<ProfileUiState>
        get() = _profileUiState

    init {
        viewModelScope.launch {
            try {
                val user = profileRepository.getUser()
                _profileUiState.value = ProfileUiState.Success(user.toModel())
            } catch (e: Exception) {
                _profileUiState.value = ProfileUiState.Error(e.message.toString())
            }
        }
    }
}

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val user: UserModel) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}