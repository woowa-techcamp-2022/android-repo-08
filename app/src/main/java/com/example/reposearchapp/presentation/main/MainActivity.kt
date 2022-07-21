package com.example.reposearchapp.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.reposearchapp.R
import com.example.reposearchapp.data.remote.GithubApi
import com.example.reposearchapp.databinding.ActivityMainBinding
import com.example.reposearchapp.presentation.home.HomeFragment
import com.example.reposearchapp.presentation.login.LoginFragment
import com.example.reposearchapp.util.Event
import com.example.reposearchapp.util.showSnackBar
import com.example.reposearchapp.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container_main, LoginFragment(), LoginFragment.TAG)
            }
        }

        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    is Event.Success -> {
                        showToast(getString(R.string.login_success))
                        navigateToHomeFragment()
                    }
                    is Event.Error -> {
                        showSnackBar(
                            binding.root,
                            String.format(getString(R.string.login_fail, it.message))
                        )
                    }
                }
            }
        }
    }

    private fun navigateToHomeFragment() {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_main, HomeFragment(), HomeFragment.TAG)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.data?.getQueryParameters("code")?.let {
            viewModel.requestAccessToken(it[0])
        }
    }
}