/*
 * Сreated by Igor Pokrovsky. 2020/5/16
 */

package com.nigtime.weatherapplication.screen.settings

import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import com.nigtime.weatherapplication.R

class DataSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.data_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (parentFragment is HostSettings) {
            (parentFragment as HostSettings).setTitle(R.string.settings_data_title)
        }
    }
}
