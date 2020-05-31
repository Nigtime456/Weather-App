/*
 * Сreated by Igor Pokrovsky. 2020/5/31
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.ui.screen.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : BaseActivity(),
    PreferenceFragmentCompat.OnPreferenceStartFragmentCallback,
    HostSettings {

    companion object {
        fun getIntent(context: Context) = Intent(context, SettingsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settingsFragContainer, MainSettingsFragment())
                .commit()
        }
        setSupportActionBar(settingsToolbar)
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
        val fragment = instantiateFragment(pref)

        fragment.arguments = pref.extras
        fragment.setTargetFragment(caller, 0)

        supportFragmentManager.beginTransaction()
            .replace(R.id.settingsFragContainer, fragment)
            .addToBackStack(null)
            .commit()

        return true
    }


    private fun instantiateFragment(pref: Preference): Fragment {
        return supportFragmentManager.fragmentFactory
            .instantiate(classLoader, pref.fragment)
    }

    override fun setAppBarTitle(@StringRes strId: Int) {
        settingsToolbar.setTitle(strId)
    }
}
