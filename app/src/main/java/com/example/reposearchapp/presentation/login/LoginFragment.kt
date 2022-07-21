package com.example.reposearchapp.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.example.reposearchapp.BuildConfig
import com.example.reposearchapp.R
import com.example.reposearchapp.databinding.FragmentLoginBinding
import com.example.reposearchapp.presentation.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val loginUrl = Uri.Builder().scheme("https").authority("github.com")
                .appendPath("login")
                .appendPath("oauth")
                .appendPath("authorize")
                .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
                .appendQueryParameter("scope", "user,repo,notifications")
                .build()

            val intent = Intent(Intent.ACTION_VIEW, loginUrl)
            startActivity(intent)
        }
    }

    companion object {
        const val TAG = "LoginFragment"
    }
}