package com.farhanharis.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farhanharis.githubuser.databinding.ItemRowUserBinding
import com.farhanharis.githubuser.remote.ItemsItem

class UserAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var itemRowUserBinding : ItemRowUserBinding //Convert dari layout activity item_row_user

    fun setOntItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(var itemRowUserBinding: ItemRowUserBinding) : RecyclerView.ViewHolder(itemRowUserBinding.root){
        val imgPhotoRv : ImageView = itemView.findViewById(R.id.img_item_photo)
        val nameRv : TextView = itemView.findViewById(R.id.tv_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user,parent,false)
        return ListViewHolder(itemRowUserBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listUser[position]
        Glide.with(holder.itemView.context).load(item.avatarUrl).into(holder.imgPhotoRv)
        holder.nameRv.text = item.login
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])}

//        val (username, photo) = listUser[position]
//        Glide.with(holder.itemView.context).load(photo).circleCrop().into(holder.itemRowUserBinding.imgItemPhoto)
//        holder.itemRowUserBinding.tvName.text = username
//        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: ItemsItem)
    }
}