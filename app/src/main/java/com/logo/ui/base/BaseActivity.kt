package com.logo.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.logo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    abstract var layoutResource: Int
    protected lateinit var binding: T

    private lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResource)
        initProgressDialog()
    }

    private fun initProgressDialog() {
        progressDialog = AlertDialog.Builder(this).create()
        val progressBar = ProgressBar(this)
        progressBar.indeterminateDrawable =
            ContextCompat.getDrawable(this, R.drawable.progress_bar)
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
}