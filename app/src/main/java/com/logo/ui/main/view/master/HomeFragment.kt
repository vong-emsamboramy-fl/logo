package com.logo.ui.main.view.master

import android.os.Bundle
import android.view.View
import com.logo.R
import com.logo.databinding.FragmentBlankBinding
import com.logo.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentBlankBinding>() {
    override val layoutResource = R.layout.fragment_blank

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.text = "Home Fragment"
    }
}