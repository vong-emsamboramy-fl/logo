package com.logo.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.logo.R

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private lateinit var progressDialog: AlertDialog

    protected abstract val layoutResource: Int

    protected lateinit var binding: T


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        initProgressDialog()
        binding = DataBindingUtil.inflate(inflater, layoutResource, container, false)
        return binding.root
    }

    private fun initProgressDialog() {
        progressDialog = AlertDialog.Builder(requireContext()).create()
        val progressBar = ProgressBar(requireContext())
        progressBar.indeterminateDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.progress_bar)
        progressDialog.let {
            it.setView(progressBar)
            it.window?.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setCanceledOnTouchOutside(false)
            it.setCancelable(false)
        }
    }

    fun showProgress() {
        progressDialog.show()
    }

    fun dismissProgress() {
        progressDialog.dismiss()
    }
}