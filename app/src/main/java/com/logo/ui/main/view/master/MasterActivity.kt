package com.logo.ui.main.view.master

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.logo.R
import com.logo.data.model.headline.SearchPlace
import com.logo.data.model.headline.SearchPlaceList
import com.logo.data.model.search.SortQuery
import com.logo.databinding.ActivityMasterBinding
import com.logo.ui.base.BaseActivity
import com.logo.utils.PreferencesManager
import com.logo.utils.SharePreferenceKey


class MasterActivity : BaseActivity<ActivityMasterBinding>() {

    companion object {
        fun launchClearTask(context: Context) {
            context.startActivity(Intent(context, MasterActivity::class.java).apply {
                flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            })
        }
    }

    private val preference by lazy {
        PreferencesManager.instantiate(this)
    }

    override var layoutResource: Int = R.layout.activity_master

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    private fun initData() {
        val sortBy = Gson().fromJson(
            preference.get(SharePreferenceKey.SORT_QUERY),
            SortQuery::class.java
        )

        if (sortBy == null) {
            preference.store(SharePreferenceKey.SORT_QUERY, SortQuery.UPLOADED_DATE)
        }
    }
}