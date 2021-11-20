package com.ardnn.githubuserv3.ui.userdetail.userfoll

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardnn.githubuserv3.R

class UserFollPagerAdapter(
    private val activity: AppCompatActivity,
    private val username: String
) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return UserFollFragment.newInstance(position, username)
    }

    override fun getItemCount(): Int {
        return activity.resources.getStringArray(R.array.user_foll_tab_text).size
    }

}