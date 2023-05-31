package com.example.unimed.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.unimed.R

class SettingsFragment2 : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}