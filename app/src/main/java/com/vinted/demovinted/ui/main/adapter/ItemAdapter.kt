package com.vinted.demovinted.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vinted.demovinted.R
import com.vinted.demovinted.databinding.AdapterItemBinding
import com.vinted.demovinted.data.models.Item
import com.vinted.demovinted.utils.setSafeOnClickListener

class ItemAdapter(
    private val onItemClicked: (item: Item) -> Unit
) : PagingDataAdapter<Item, ItemAdapter.ItemViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            AdapterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)

            holder.binding.root.setSafeOnClickListener { onItemClicked(item) }
        }
    }

    class ItemViewHolder(
        val binding: AdapterItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {



        fun bind(item: Item) = with(binding) {
            tvTitle.text = item.brand
            Glide.with(imageView)
                .load(item.fullPhotoURL)
                .centerCrop()
                .placeholder(R.drawable.bg_image_place_holder)
                .into(imageView);
        }
    }

    companion object {

        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item) =
                oldItem == newItem
        }
    }
}