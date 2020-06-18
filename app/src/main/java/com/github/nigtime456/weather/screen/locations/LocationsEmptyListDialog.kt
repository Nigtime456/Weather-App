/*
 * Сreated by Igor Pokrovsky. 2020/5/31
 */

package com.github.nigtime456.weather.screen.locations

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.github.nigtime456.weather.R

class LocationsEmptyListDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_launcher)
            .setTitle(R.string.activity_locations_dialog_title)
            .setMessage(R.string.activity_locations_dialog_message)
            .setPositiveButton(R.string.button_ok, null)
            .create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            //align text
            it.findViewById<TextView>(android.R.id.message)?.gravity = Gravity.CENTER
        }
    }
}