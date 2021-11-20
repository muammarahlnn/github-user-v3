package com.ardnn.githubuserv3.helper

import androidx.recyclerview.widget.DiffUtil
import com.ardnn.githubuserv3.database.FavoriteUser

class FavoriteUserDiffCallback(
    private val oldList: List<FavoriteUser>,
    private val newList: List<FavoriteUser>,
) : DiffUtil.Callback(){

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

}