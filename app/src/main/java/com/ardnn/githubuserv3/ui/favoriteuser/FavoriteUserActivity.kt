package com.ardnn.githubuserv3.ui.favoriteuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.githubuserv3.database.FavoriteUser
import com.ardnn.githubuserv3.databinding.ActivityFavoriteUserBinding
import com.ardnn.githubuserv3.helper.ClickListener

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
        Toast.makeText(this, t.username, Toast.LENGTH_SHORT).show()
    }
}