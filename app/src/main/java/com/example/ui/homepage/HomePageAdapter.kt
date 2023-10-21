package com.example.ui.homepage

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ourstoryapps.data.detail.DetailActivity
import com.example.ourstoryapps.data.model.ListStoryItem
import com.example.ourstoryapps.databinding.CardForItemBinding
import com.example.ui.homepage.HomePageAdapter.TheViewHolder
import com.squareup.picasso.Picasso

class HomePageAdapter : PagingDataAdapter<ListStoryItem, TheViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class TheViewHolder(val binding: CardForItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ListStoryItem){
            val ctx = binding.root.context

            binding.tvItemName.text = "${review.name}"
            binding.deskCard.text = "${review.description}"
//            binding.itemText.text = "${review.login}"
            Picasso.get().load(review.photoUrl).into(binding.ivItemPhoto)

            binding.cardItemStory.setOnClickListener {

                val intent = Intent(ctx,DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID,review.id)
                ctx.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
        val binding = CardForItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

}