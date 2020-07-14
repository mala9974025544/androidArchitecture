package com.precticle.androidarchitecture.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.precticle.androidarchitecture.R
import com.precticle.androidarchitecture.data.model.User
import kotlinx.android.synthetic.main.item_layout.view.*

class MainAdapterWIthPageList() : PagedListAdapter<User,MainAdapterWIthPageList.UserViewHolder>(USER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return UserViewHolder(view)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.bind(it) }
    }
    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.imageViewAvatar
        private val userName = view.textViewUserName
        private val userEmail = view.textViewUserEmail
        fun bind(user: User) {
            userName.text = user.userName
            userEmail.text = user.userEmail
            Glide.with(imageView.context)
                .load(user.image)
                //.placeholder(R.drawable.loading)
                .into(imageView);
        }
    }
    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.userId == newItem.userId
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                newItem == oldItem
        }
    }
}