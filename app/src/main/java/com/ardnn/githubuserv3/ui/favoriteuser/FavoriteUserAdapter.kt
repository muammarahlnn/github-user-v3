package com.ardnn.githubuserv3.ui.favoriteuser

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.githubuserv3.database.FavoriteUser
import com.ardnn.githubuserv3.databinding.ItemUserBinding
import com.ardnn.githubuserv3.helper.FavoriteUserDiffCallback
import com.ardnn.githubuserv3.helper.Helper

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>(){

    private val favoriteUserList = ArrayList<FavoriteUser>()

    fun setFavoriteUserList(favoriteUserList: List<FavoriteUser>) {
        val diffCallback = FavoriteUserDiffCallback(this.favoriteUserList, favoriteUserList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.favoriteUserList.clear()
        this.favoriteUserList.addAll(favoriteUserList)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
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
                    Toast.makeText(it.context, favoriteUser.username, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}