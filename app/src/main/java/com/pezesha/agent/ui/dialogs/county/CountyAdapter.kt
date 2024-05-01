package com.pezesha.agent.ui.dialogs.county

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pezesha.agent.data.models.County
import com.pezesha.agent.databinding.AdapterCountyBinding

class CountyAdapter(
    private val clickListener: (county: County) -> Unit
) :
    ListAdapter<County, CountyViewHolder>(CountyDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountyViewHolder {
        val binding = AdapterCountyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CountyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

}

class CountyViewHolder(
    private val binding: AdapterCountyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(county: County, clickListener: (county: County) -> Unit) {
        binding.apply {
            tvCode.text = county.code
            tvCounty.text = county.name
            clHolder.setOnClickListener { clickListener(county) }
        }
    }

}

object CountyDiffUtil : DiffUtil.ItemCallback<County>() {
    override fun areItemsTheSame(oldItem: County, newItem: County): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: County, newItem: County): Boolean {
        return oldItem == newItem
    }

}
