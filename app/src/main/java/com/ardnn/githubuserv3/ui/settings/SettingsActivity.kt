package com.ardnn.githubuserv3.ui.settings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.ardnn.githubuserv3.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingsPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, SettingsViewModelFactory(pref))
            .get(SettingsViewModel::class.java)

        // observe light mode flag
        viewModel.getThemeSetting().observe(this, { isLightModeActive ->
            binding.switchTheme.isChecked = isLightModeActive
        })

        // save light mode  flag
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
        }
    }
}