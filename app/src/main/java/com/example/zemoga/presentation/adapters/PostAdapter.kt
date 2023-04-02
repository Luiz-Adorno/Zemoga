package com.example.zemoga.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zemoga.R
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.databinding.RecyclerPostItemBinding

class PostAdapter(
    private var items: List<PostListItem>,
    private val onItemClick: (PostListItem) -> Unit,
    private val starItemClick: (PostListItem) -> Unit
) : RecyclerView.Adapter<PostAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RecyclerPostItemBinding) : RecyclerView.ViewHolder(binding.root)

    //inflate the layout with view binding library
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecyclerPostItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = items[position]
        holder.binding.title.text = post.title
        post.isFavorite?.let {
            if (it) {
                holder.binding.star.setImageResource(R.drawable.star_favorite)
            } else {
                holder.binding.star.setImageResource(R.drawable.star_border)
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick(post)
        }

        holder.binding.star.setOnClickListener {
            starItemClick(post)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(postListItem: List<PostListItem>) {
        val list = postListItem.sortedBy { it.isFavorite == false }
        this.items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}