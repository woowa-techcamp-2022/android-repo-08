package com.example.reposearchapp.presentation.adapter.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeViewPagerAdapter(
    container: Fragment,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(container) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}