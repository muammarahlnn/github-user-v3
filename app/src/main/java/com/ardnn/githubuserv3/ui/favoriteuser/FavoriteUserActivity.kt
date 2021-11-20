package com.ardnn.githubuserv3.ui.favoriteuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.githubuserv3.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {

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
        adapter = FavoriteUserAdapter()

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
}