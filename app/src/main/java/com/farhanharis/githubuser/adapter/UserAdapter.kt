package com.farhanharis.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farhanharis.githubuser.databinding.ItemRowUserBinding
import com.farhanharis.githubuser.remote.response.ItemsItem

class UserAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback{
        fun onItemClicked(data: ItemsItem)
    }

    fun setOntItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root){
//        val imgPhotoRv : ImageView = itemView.findViewById(R.id.img_item_photo)
//        val nameRv : TextView = itemView.findViewById(R.id.tv_name)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        //val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user,parent,false)
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, photo) = listUser[position]
        Glide.with(holder.itemView.context).load(photo).into(holder.binding.imgItemPhoto)
        holder.binding.tvName.text = name
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])}

       //val item = listUser[position]
//        Glide.with(holder.itemView.context).load(item.avatarUrl).into(holder.imgPhotoRv)
//        holder.nameRv.text = item.login
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

}