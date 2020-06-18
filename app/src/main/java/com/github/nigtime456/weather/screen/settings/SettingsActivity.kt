/*
 * Сreated by Igor Pokrovsky. 2020/5/31
 */

package com.github.nigtime456.weather.screen.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.screen.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, SettingsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initViews()
        loadFrag(savedInstanceState)
    }

    private fun loadFrag(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_settings_frag_container, SettingsFragment())
                .commit()
        }
    }

    private fun initViews() {
        activity_settings_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}
