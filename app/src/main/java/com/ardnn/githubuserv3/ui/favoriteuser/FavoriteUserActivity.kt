package com.ardnn.githubuserv3.ui.favoriteuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.githubuserv3.database.FavoriteUser
import com.ardnn.githubuserv3.databinding.ActivityFavoriteUserBinding
import com.ardnn.githubuserv3.helper.ClickListener
import com.ardnn.githubuserv3.ui.userdetail.UserDetailActivity

class FavoriteUserActivity : AppCompatActivity(), ClickListener<FavoriteUser> {

    private lateinit var viewModel: FavoriteUserViewModel
    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set recyclerview
        binding.rvUser.layoutManager = LinearLayoutManager(this)

        // observe favorite user list
        viewModel = ViewModelProvider(this, FavoriteUserViewModelFactory(application))
            .get(FavoriteUserViewModel::class.java)
        viewModel.getAllFavoriteUsers().observe(this, { favoriteUserList ->
            val adapter = FavoriteUserAdapter(favoriteUserList, this)
            binding.rvUser.adapter = adapter

            // show alert to the user if list is empty
            binding.tvAlert.visibility =
                if (favoriteUserList.isNullOrEmpty()) View.VISIBLE
                else View.INVISIBLE
        })

    }

    override fun onItemClicked(t: FavoriteUser) {
        // to user detail
        val toUserDetail = Intent(this, UserDetailActivity::class.java)
        toUserDetail.putExtra(UserDetailActivity.EXTRA_USERNAME, t.username)
        startActivity(toUserDetail)
    }
}