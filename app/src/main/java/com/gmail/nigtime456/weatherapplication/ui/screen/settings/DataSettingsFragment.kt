/*
 * Сreated by Igor Pokrovsky. 2020/5/31
 */


package com.gmail.nigtime456.weatherapplication.ui.screen.settings

import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import com.gmail.nigtime456.weatherapplication.R

class DataSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.data_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is HostSettings) {
            (activity as HostSettings).setAppBarTitle(R.string.settings_data_title)
        }
    }
}
