package com.example.zemoga.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zemoga.data.models.CommentItem
import com.example.zemoga.databinding.RecyclerCommentItemBinding

class CommentAdapter(
    private var items: List<CommentItem>
    ): RecyclerView.Adapter<CommentAdapter.MyViewHolder>(){

    class MyViewHolder(val binding: RecyclerCommentItemBinding): RecyclerView.ViewHolder(binding.root)

    //inflate the layout with view binding library
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecyclerCommentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false
        ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comment = items[position]

        holder.binding.name.text = comment.name
        holder.binding.email.text = comment.email
        holder.binding.body.text = comment.body

    }

    fun setData(commentItem: List<CommentItem>){
        this.items = commentItem
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}