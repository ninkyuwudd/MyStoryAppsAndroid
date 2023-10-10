package com.example.ourstoryapps.homepage

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ourstoryapps.data.model.ListStoryItem
import com.example.ourstoryapps.databinding.CardForItemBinding
import com.example.ourstoryapps.homepage.HomePageAdapter.TheViewHolder
import com.squareup.picasso.Picasso

class HomePageAdapter : ListAdapter<ListStoryItem, TheViewHolder>(DIFF_CALLBACK) {
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

            binding.titleCard.text = "${review.name}"
            binding.deskCard.text = "${review.description}"
//            binding.itemText.text = "${review.login}"
            Picasso.get().load(review.photoUrl).into(binding.imageCard)
//
//            binding.itemCard.setOnClickListener{
//                val intent = Intent(ctx,DetailAccount::class.java)
//                intent.putExtra(DetailAccount.EXTRA_TITLE,review.login)
//                intent.putExtra(DetailAccount.EXTRA_IMG_ACCOUNT,review.avatarUrl)
//                ctx.startActivity(intent)
//
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
        val binding = CardForItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}