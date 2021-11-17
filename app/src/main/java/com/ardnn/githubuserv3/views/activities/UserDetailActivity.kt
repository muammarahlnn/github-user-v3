package com.ardnn.githubuserv3.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ardnn.githubuserv3.R
import com.ardnn.githubuserv3.api.responses.UserDetailResponse
import com.ardnn.githubuserv3.databinding.ActivityUserDetailBinding
import com.ardnn.githubuserv3.helper.Helper
import com.ardnn.githubuserv3.viewmodels.UserDetailViewModel
import com.ardnn.githubuserv3.viewmodels.UserDetailViewModelFactory

class UserDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var viewModel: UserDetailViewModel
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get username and set it as parameter on view model
        val username = intent.getStringExtra(EXTRA_USERNAME)
        viewModel = ViewModelProvider(this, UserDetailViewModelFactory(username as String))
            .get(UserDetailViewModel::class.java)


        // subscribe view model
        subscribe()

    }

    private fun subscribe() {
        // fetch user detail
        viewModel.user.observe(this, { user ->
            setUserDetail(user)
        })

        // show progressbar
        viewModel.isLoading.observe(this, { isLoading ->
            showLoading(isLoading)
        })

        // show alert if unable to retrieve data
        viewModel.isFailure.observe(this, { isFailure ->
            showAlert(isFailure)
        })
    }


    private fun setUserDetail(user: UserDetailResponse) {
        with (binding) {
            Helper.setImageGlide(
                this@UserDetailActivity,
                user.avatarUrl,
                ivAva)
            tvUsername.text = user.username
            tvName.text = user.name ?: "-"
            tvLocation.text  = user.location ?: "-"
            tvCompany.text = user.company ?: "-"
            tvRepositories.text = (user.repositories ?: 0).toString()

        }
    }

    private fun showLoading(isLoading: Boolean) {
        with (binding) {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                ivLocation.visibility = View.INVISIBLE
                ivCompany.visibility = View.INVISIBLE
                ivRepositories.visibility = View.INVISIBLE
            } else {
                progressBar.visibility = View.GONE
                ivLocation.visibility = View.VISIBLE
                ivCompany.visibility = View.VISIBLE
                ivRepositories.visibility = View.VISIBLE
            }
        }
    }

    private fun showAlert(isFailure: Boolean) {
        binding.clAlert.visibility = if (isFailure) View.VISIBLE else View.GONE
    }

}