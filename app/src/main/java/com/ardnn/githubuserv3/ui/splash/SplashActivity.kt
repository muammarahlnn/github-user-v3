package com.ardnn.githubuserv3.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.ardnn.githubuserv3.R
import com.ardnn.githubuserv3.databinding.ActivitySplashBinding
import com.ardnn.githubuserv3.ui.main.MainActivity
import com.ardnn.githubuserv3.ui.settings.SettingsPreferences
import com.ardnn.githubuserv3.ui.settings.SettingsViewModel
import com.ardnn.githubuserv3.ui.settings.SettingsViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var settingsViewModel: SettingsViewModel

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val pref = SettingsPreferences.getInstance(dataStore)
        settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref))
            .get(SettingsViewModel::class.java)

        // observe theme flag
        settingsViewModel.getThemeSetting().observe(this, { isLightModeActive ->
            if (isLightModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        })

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set timer for 1 second
        Handler(Looper.getMainLooper()).postDelayed({
            // to main activity
            val toMain = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(toMain)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, 500)

    }
}