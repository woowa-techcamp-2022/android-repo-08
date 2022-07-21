package com.example.reposearchapp.presentation.adapter.issue

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.reposearchapp.R
import com.example.reposearchapp.databinding.ItemIssueOptionBinding
import com.example.reposearchapp.model.issue.IssueType
import com.example.reposearchapp.presentation.home.issue.IssueViewModel

class IssueOptionAdapter(
    context: Context,
    private val optionValue: List<IssueType>,
    private val viewModel: IssueViewModel
) :
    ArrayAdapter<IssueType>(context, R.layout.item_issue_option) {

    private val layoutInflater = LayoutInflater.from(context)


    override fun getCount() = optionValue.size
    override fun getItem(position: Int) = optionValue[position]
    override fun getItemId(position: Int) = optionValue[position].ordinal.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return convertView ?: layoutInflater.inflate(R.layout.header_issue_option, parent, false)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
        convertView?.let {
            convertView
        } ?: kotlin.run {
            ItemIssueOptionBinding.inflate(layoutInflater, parent, false).apply {
                val issueType = getItem(position)
                tvIssueType.text = context.getString(issueType.optionName)

                val isSelected = issueType == viewModel.issueType.value
                tvIssueType.setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (isSelected) R.color.white else R.color.grey
                    )
                )
                imgCheckIcon.isVisible = isSelected
            }.root
        }
}