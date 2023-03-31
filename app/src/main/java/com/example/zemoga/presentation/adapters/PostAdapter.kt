package com.example.zemoga.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.databinding.RecyclerPostItemBinding

class PostAdapter(
    private var items: List<PostListItem>,
    private val onItemClick: (PostListItem) -> Unit
): RecyclerView.Adapter<PostAdapter.MyViewHolder>(){

    class MyViewHolder(val binding: RecyclerPostItemBinding): RecyclerView.ViewHolder(binding.root)

    //inflate the layout with view binding library
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RecyclerPostItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false
        ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = items[position]

        holder.binding.title.text = post.title

        holder.itemView.setOnClickListener{
            onItemClick(post)
        }

    }

    fun setData(postListItem: List<PostListItem>){
        this.items = postListItem
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}