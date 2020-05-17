/*
 * Сreated by Igor Pokrovsky. 2020/5/16
 */

package com.nigtime.weatherapplication.screen.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import com.nigtime.weatherapplication.R

class MainSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preferences, rootKey)
    }

    override fun getCallbackFragment(): Fragment? {
        return parentFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (parentFragment is HostSettings) {
            (parentFragment as HostSettings).setTitle(R.string.settings_main_title)
        }
    }
}
