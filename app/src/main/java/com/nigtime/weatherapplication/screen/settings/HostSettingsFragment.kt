/*
 * Сreated by Igor Pokrovsky. 2020/5/16
 */

package com.nigtime.weatherapplication.screen.settings

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.screen.common.FragmentWithListener
import com.nigtime.weatherapplication.screen.common.NavigationController
import kotlinx.android.synthetic.main.fragment_host_settings.*

/**
 * A simple [Fragment] subclass.
 */
class HostSettingsFragment :
    FragmentWithListener<NavigationController>(R.layout.fragment_host_settings),
    PreferenceFragmentCompat.OnPreferenceStartFragmentCallback, HostSettings {

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java


    private val backPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            childFragmentManager.popBackStack()
            isEnabled = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBar()
        setupBackPressed()
        if (savedInstanceState == null) {
            loadMainSettings()
        } else {
            backPressedCallback.isEnabled = childFragmentManager.backStackEntryCount > 1
        }
    }

    override fun setTitle(@StringRes strId: Int) {
        hostSettingsToolbar.setTitle(strId)
    }

    private fun setupBackPressed() {
        requireActivity().onBackPressedDispatcher
            .addCallback(this, backPressedCallback)
    }

    private fun loadMainSettings() {
        childFragmentManager.beginTransaction()
            .replace(R.id.hostSettingsFragContainer, MainSettingsFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun setupAppBar() {
        hostSettingsToolbar.setNavigationOnClickListener {
            parentListener?.toPreviousScreen()
        }
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
        val fragment = instantiateFragment(pref)

        fragment.arguments = pref.extras
        fragment.setTargetFragment(caller, 0)

        commitFragment(fragment)

        backPressedCallback.isEnabled = true

        return true
    }

    private fun instantiateFragment(pref: Preference) =
        childFragmentManager.fragmentFactory
            .instantiate(requireActivity().classLoader, pref.fragment)


    private fun commitFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.hostSettingsFragContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
