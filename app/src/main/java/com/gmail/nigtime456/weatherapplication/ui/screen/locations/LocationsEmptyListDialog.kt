/*
 * Сreated by Igor Pokrovsky. 2020/5/31
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.locations

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gmail.nigtime456.weatherapplication.R

class LocationsEmptyListDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_launcher)
            .setTitle(R.string.locations_dialog_title)
            .setMessage(R.string.locations_dialog_message)
            .setPositiveButton(R.string.locations_dialog_button_ok, null)
            .create()
    }
}