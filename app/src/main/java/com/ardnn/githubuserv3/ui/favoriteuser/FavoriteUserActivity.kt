package com.ardnn.githubuserv3.ui.favoriteuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.githubuserv3.database.FavoriteUser
import com.ardnn.githubuserv3.databinding.ActivityFavoriteUserBinding
import com.ardnn.githubuserv3.helper.ClickListener
import com.ardnn.githubuserv3.ui.userdetail.UserDetailActivity

class FavoriteUserActivity : AppCompatActivity(), ClickListener<FavoriteUser> {

    private lateinit var viewModel: FavoriteUserViewModel
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialization
        viewModel = ViewModelProvider(this, FavoriteUserViewModelFactory(application))
            .get(FavoriteUserViewModel::class.java)
        adapter = FavoriteUserAdapter(this)

        // observe favorite user list
        viewModel.getAllFavoriteUsers().observe(this, { favoriteUserList ->
            if  (favoriteUserList != null) {
                adapter.setFavoriteUserList(favoriteUserList)
            }
        })

        // set recyclerview
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapter

    }

    override fun onItemClicked(t: FavoriteUser) {
        // to user detail
        val toUserDetail = Intent(this, UserDetailActivity::class.java)
        toUserDetail.putExtra(UserDetailActivity.EXTRA_USERNAME, t.username)
        startActivity(toUserDetail)
    }
}