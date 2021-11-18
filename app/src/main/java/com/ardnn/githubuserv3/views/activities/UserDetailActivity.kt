package com.ardnn.githubuserv3.views.activities

import android.content.Intent
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
import com.ardnn.githubuserv3.views.adapters.UserFollPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {
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

        // set view pager
        val userFollPagerAdapter = UserFollPagerAdapter(this, username)
        binding.vp2.adapter = userFollPagerAdapter

        // click listener
        binding.btnBack.setOnClickListener(this)
        binding.btnHome.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnBack -> {
                onBackPressed()
            }
            R.id.btnHome -> {
                val toMain = Intent(this, MainActivity::class.java)
                startActivity(toMain)
                finishAffinity()
            }
        }
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

            // set tab layout
            val countFolls = intArrayOf(
                user.followers ?: 0,
                user.following ?: 0
            )
            TabLayoutMediator(tabLayout, vp2) { tab, pos ->
                tab.text = String.format(
                    resources.getStringArray(R.array.user_foll_tab_text)[pos], countFolls[pos])
            }.attach()
            Helper.equalingEachTabWidth(tabLayout)
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