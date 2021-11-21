package com.ardnn.githubuserv3.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.githubuserv3.api.responses.UserResponse
import com.ardnn.githubuserv3.databinding.ItemUserBinding
import com.ardnn.githubuserv3.helper.ClickListener
import com.ardnn.githubuserv3.helper.Helper

class SearchedUserAdapter(
    private val userList: List<UserResponse>,
    private val clickListener: ClickListener<UserResponse>
) : RecyclerView.Adapter<SearchedUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun onBind(user: UserResponse) {
            with(binding) {
                // set data to widgets
                Helper.setImageGlide(binding.root.context, user.avatarUrl, ivAva)
                tvUsername.text = user.username

                // set click listener
                binding.root.setOnClickListener {
                    clickListener.onItemClicked(user)
                }
            }
        }
    }
}