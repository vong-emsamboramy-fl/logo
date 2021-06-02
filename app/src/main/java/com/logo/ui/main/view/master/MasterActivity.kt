package com.logo.ui.main.view.master

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.logo.R
import com.logo.databinding.ActivityMasterBinding
import com.logo.ui.base.BaseActivity


class MasterActivity : BaseActivity<ActivityMasterBinding>() {

    companion object {
        fun launchClearTask(context: Context) {
            context.startActivity(Intent(context, MasterActivity::class.java).apply {
                flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            })
        }
    }

    override var layoutResource: Int = R.layout.activity_master

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }
}