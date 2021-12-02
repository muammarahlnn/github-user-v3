package com.ardnn.githubuserv3.ui.favoriteuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.githubuserv3.R
import com.ardnn.githubuserv3.database.FavoriteUser
import com.ardnn.githubuserv3.databinding.ActivityFavoriteUserBinding
import com.ardnn.githubuserv3.helper.ClickListener
import com.ardnn.githubuserv3.ui.userdetail.UserDetailActivity

class FavoriteUserActivity : AppCompatActivity(), ClickListener<FavoriteUser> {

    private lateinit var viewModel: FavoriteUserViewModel
    private lateinit var binding: ActivityFavoriteUserBinding

    private var isListEmpty = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set action bar
        setSupportActionBar(binding.toolbarFavorite)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // set recyclerview
        binding.rvUser.layoutManager = LinearLayoutManager(this)

        // observe favorite user list
        viewModel = ViewModelProvider(this, FavoriteUserViewModelFactory(application))
            .get(FavoriteUserViewModel::class.java)
        viewModel.getAllFavoriteUsers().observe(this, { favoriteUserList ->
            val adapter = FavoriteUserAdapter(favoriteUserList, this)
            binding.rvUser.adapter = adapter

            // show alert to the user if list is empty
            if (favoriteUserList.isNullOrEmpty()) {
                isListEmpty = true
                binding.tvAlert.visibility = View.VISIBLE
            } else {
                isListEmpty = false
                binding.tvAlert.visibility = View.INVISIBLE
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemToolbarDelete -> {
                deleteAllFavoriteUsers()
                true
            }
            else -> {
                true
            }
        }
    }

    override fun onItemClicked(t: FavoriteUser) {
        // to user detail
        val toUserDetail = Intent(this, UserDetailActivity::class.java)
        toUserDetail.putExtra(UserDetailActivity.EXTRA_USERNAME, t.username)
        startActivity(toUserDetail)
    }

    private fun deleteAllFavoriteUsers() {
        // check if list is already empty
        if (isListEmpty) {
            Toast.makeText(this, "Your favorite users is already empty", Toast.LENGTH_SHORT).show()
            return
        }

        // create an alert
        val alert = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        alert.setMessage("Are you sure want to clear your favorite users?")
        alert.setPositiveButton("Yes") {_, _ ->
            viewModel.deleteAllFavorites()
            Toast.makeText(this, "Your favorite users successfully cleared", Toast.LENGTH_SHORT)
                .show()
        }
        alert.setNegativeButton("No", null)
        alert.create().show()
    }
}