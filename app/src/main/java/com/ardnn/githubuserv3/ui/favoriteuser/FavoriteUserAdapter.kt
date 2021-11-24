package com.ardnn.githubuserv3.ui.favoriteuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.githubuserv3.database.FavoriteUser
import com.ardnn.githubuserv3.databinding.ItemUserBinding
import com.ardnn.githubuserv3.helper.ClickListener
import com.ardnn.githubuserv3.helper.Helper

class FavoriteUserAdapter(
    private val favoriteUserList: List<FavoriteUser>,
    private val clickListener: ClickListener<FavoriteUser>
) : RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(favoriteUserList[position])
    }

    override fun getItemCount(): Int {
        return favoriteUserList.size
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun onBind(favoriteUser: FavoriteUser) {
            with (binding) {
                // set data to widgets
                Helper.setImageGlide(
                    binding.root.context,
                    favoriteUser.avatarUrl,
                    ivAva)
                binding.tvUsername.text = favoriteUser.username

                // click listener
                binding.root.setOnClickListener {
                    clickListener.onItemClicked(favoriteUser)
                }
            }
        }
    }
}