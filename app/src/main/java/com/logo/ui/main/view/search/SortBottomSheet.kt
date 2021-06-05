package com.logo.ui.main.view.search

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.logo.data.model.search.SortQuery
import com.logo.databinding.BottomSheetSortBinding
import com.logo.utils.PreferencesManager
import com.logo.utils.SharePreferenceKey

class SortBottomSheet(
    private val listener: SortBottomSheetListener
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetSortBinding

    private lateinit var sortQuery: SortQuery

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener.onBottomSheetDismissed(this.sortQuery)
    }

    override fun onResume() {
        super.onResume()
        initListener()
    }

    fun setQuery(sortQuery: SortQuery) {
        this.sortQuery = sortQuery
    }

    private fun initView() {
        binding.radioButtonUploadDate.isChecked = sortQuery.text == SortQuery.UPLOADED_DATE.text
        binding.radioButtonRelevance.isChecked = sortQuery.text == SortQuery.RELEVANCE.text
    }

    private fun initListener() {
        binding.radioButtonRelevance.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                this.sortQuery = SortQuery.RELEVANCE
            } else {
                this.sortQuery = SortQuery.UPLOADED_DATE
            }
        }
    }
}

interface SortBottomSheetListener {
    fun onBottomSheetDismissed(sortQuery: SortQuery)
}