package com.dicoding.submissionakhir.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissionakhir.R
import com.dicoding.submissionakhir.theme.ThemePreferences
import com.dicoding.submissionakhir.ui.viewmodel.ThemeFactory
import com.dicoding.submissionakhir.ui.viewmodel.ThemeViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)

        val switchTheme = findViewById<Switch>(R.id.switch_theme)
        val pref = ThemePreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ThemeFactory(pref)).get(ThemeViewModel::class.java)

        mainViewModel.getThemeSetting().observe(this) {
                isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }
}