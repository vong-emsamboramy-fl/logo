package com.logo.ui.main.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.logo.R
import com.logo.databinding.ActivitySplashScreenBinding
import com.logo.ui.base.BaseActivity
import com.logo.ui.main.view.master.MasterActivity
import java.util.*

class SplashScreenActivity :
    BaseActivity<ActivitySplashScreenBinding>() {

    private val splashScreenTimeOut: Long = 3000

    override var layoutResource = R.layout.activity_splash_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            MasterActivity.launchClearTask(this)
        }, splashScreenTimeOut)
    }

}