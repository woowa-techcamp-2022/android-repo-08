package com.example.reposearchapp.presentation.home.notification

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.reposearchapp.R
import com.example.reposearchapp.databinding.FragmentNotificationBinding
import com.example.reposearchapp.presentation.adapter.notification.NotificationAdapter
import com.example.reposearchapp.presentation.adapter.notification.NotificationItemSwipeHelper
import com.example.reposearchapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {

    private val viewModel: NotificationViewModel by viewModels()

    private lateinit var notificationAdapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViews()
        observeNotification()
    }

    private fun initViews() = with(binding) {
        notificationAdapter = NotificationAdapter()
        recyclerNotification.adapter = notificationAdapter

        val notificationSwipeHelper = NotificationItemSwipeHelper(requireContext())
        notificationSwipeHelper.setOnSwipedListener { position ->
            val notificationModel = notificationAdapter.getItemByPosition(position)
            notificationModel?.let {
                viewModel.readNotification(it)
            }
        }

        val itemTouchHelper = ItemTouchHelper(notificationSwipeHelper)
        itemTouchHelper.attachToRecyclerView(recyclerNotification)
    }

    private fun bindViews() = with(binding) {
        swipeRefreshLayout.setOnRefreshListener {
            notificationAdapter.refresh()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeNotification() =
        viewModel.notificationStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                NotificationState.UnInitialState -> viewModel.getNotifications()
                NotificationState.Loading -> handleLoading()
                NotificationState.FetchFinish -> handleSuccess()
                is NotificationState.Error -> handleError(it)
                NotificationState.SuccessRead -> handleSuccessRead()
            }
        }

    private fun handleLoading() {

    }

    private fun handleSuccess() {
        // notification paging data
        lifecycleScope.launch {
            viewModel.notificationDataFlow.collectLatest {
                notificationAdapter.submitData(it)
            }
        }
    }

    private fun handleError(state: NotificationState.Error) {

    }

    private fun handleSuccessRead() {
        notificationAdapter.refresh()
    }

    companion object {
        const val TAG = "NotificationFragment"
        fun newInstance() = NotificationFragment()
    }
}