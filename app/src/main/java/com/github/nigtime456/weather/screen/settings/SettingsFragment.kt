/*
 * Сreated by Igor Pokrovsky. 2020/5/31
 */

package com.github.nigtime456.weather.screen.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.github.nigtime456.weather.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preferences, rootKey)
    }
}
