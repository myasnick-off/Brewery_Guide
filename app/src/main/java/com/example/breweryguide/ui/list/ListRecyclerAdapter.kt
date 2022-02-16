package com.example.breweryguide.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.breweryguide.databinding.FragmentListItemBinding
import com.example.breweryguide.domain.model.BreweryBasic

class ListRecyclerAdapter(private val listener: ListFragment.ItemClickListener) :
    PagedListAdapter<BreweryBasic, ListRecyclerAdapter.BreweryViewHolder>(BreweryItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryViewHolder {
        val binding =
            FragmentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreweryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreweryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class BreweryViewHolder(private val binding: FragmentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(breweryBasic: BreweryBasic) = with(binding) {
            nameTextView.text = breweryBasic.name
            typeTextView.text = breweryBasic.breweryType
            countryTextView.text = breweryBasic.country
            root.setOnClickListener { listener.onItemClicked(breweryBasic.id) }
        }
    }
}

object BreweryItemCallback : DiffUtil.ItemCallback<BreweryBasic>() {

    override fun areItemsTheSame(oldItem: BreweryBasic, newItem: BreweryBasic): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BreweryBasic, newItem: BreweryBasic): Boolean {
        return oldItem == newItem
    }

}