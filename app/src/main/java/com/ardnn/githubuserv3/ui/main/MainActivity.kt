package com.ardnn.githubuserv3.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.githubuserv3.R
import com.ardnn.githubuserv3.api.responses.UserResponse
import com.ardnn.githubuserv3.databinding.ActivityMainBinding
import com.ardnn.githubuserv3.helper.ClickListener
import com.ardnn.githubuserv3.helper.Helper
import com.ardnn.githubuserv3.ui.favoriteuser.FavoriteUserActivity
import com.ardnn.githubuserv3.ui.settings.SettingsActivity
import com.ardnn.githubuserv3.ui.userdetail.UserDetailActivity

class MainActivity : AppCompatActivity(), ClickListener<UserResponse> {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // set action bar
        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // set recyclerview
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        // subscribe view model
        subscribe()

        // check if username field is not empty then remove the error
        binding.etUsername.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                binding.inputLayoutUsername.error = null
            }
        }

        // if search button clicked
        binding.inputLayoutUsername.setEndIconOnClickListener { view ->
            btnSearchCLicked(view)
        }

        binding.etUsername.setOnEditorActionListener { v, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    btnSearchCLicked(v)
                    true
                }
                else -> false
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemToolbarSetting -> {
                // to settings
                val toSettings = Intent(this, SettingsActivity::class.java)
                startActivity(toSettings)
                true
            }
            R.id.itemToolbarFavorite -> {
                // to favorite user
                val toFavoriteUser = Intent(this, FavoriteUserActivity::class.java)
                startActivity(toFavoriteUser)
                true
            }
            else ->  {
                true
            }
        }
    }

    private fun subscribe() {
        // observe searched users
        viewModel.searchedUsers.observe(this, { searchedUsers ->
            setSearchedUsers(searchedUsers)
        })

        // check whether user has searched or not
        viewModel.isSearched.observe(this, { isSearched ->
            if (isSearched) hideNotSearchedYetAlert()
        })

        // show alert if list is empty
        viewModel.isSearchedUsersEmpty.observe(this, { isEmpty ->
            showAlert(isEmpty, resources.getString(R.string.users_not_found))
        })

        // show alert if unable to retrieve data
        viewModel.isFailure.observe(this, { isFailure ->
            showAlert(isFailure, resources.getString(R.string.unable_to_retrieve_data))
        })

        // show progressbar
        viewModel.isLoading.observe(this, { isLoading ->
            Helper.showLoading(binding.progressBar, isLoading)
        })
    }

    private fun btnSearchCLicked(view: View) {
        // get query from edit text
        val searched = binding.etUsername.text.toString()
        if (searched.isEmpty()) {
            // set error to the input layout
            binding.inputLayoutUsername.error = resources.getString(R.string.empty_field_alert)
        } else {
            // set searched users
            viewModel.setSearchedUsers(searched)
        }

        // hide keyboard
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showAlert(isShow: Boolean, text: String) {
        with (binding) {
            // show alert and make recyclerview empty
            if (isShow) {
                tvAlert.text = text
                tvAlert.visibility = View.VISIBLE
                rvUser.adapter = null
            } else {
                tvAlert.visibility = View.INVISIBLE
            }
        }
    }

    private fun hideNotSearchedYetAlert() {
        binding.tvAlertNotSearchedYet.visibility = View.INVISIBLE
    }

    private fun setSearchedUsers(searchedUsers: List<UserResponse>) {
        val adapter = SearchedUserAdapter(searchedUsers, this)
        binding.rvUser.adapter = adapter
    }

    override fun onItemClicked(t: UserResponse) {
        val toUserDetail = Intent(this, UserDetailActivity::class.java)
        toUserDetail.putExtra(UserDetailActivity.EXTRA_USERNAME, t.username)
        startActivity(toUserDetail)
    }
}