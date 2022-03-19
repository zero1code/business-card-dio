package com.dio.businesscard.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dio.businesscard.data.BusinessCard
import com.dio.businesscard.databinding.ItemBusinessCardBinding

class BusinessCardAdapter : ListAdapter<BusinessCard, BusinessCardAdapter.ViewHolder>(DiffCallBack()) {
    var listenerShare: (View) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBusinessCardBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemBusinessCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BusinessCard) {
            binding.tvName.text = item.name
            binding.tvEmail.text = item.email
            binding.tvPhone.text = item.phone
            binding.tvSite.text = item.site
            binding.tvCompanyName.text = item.companyName
            binding.cvBusinessCard.setCardBackgroundColor(Color.parseColor(item.cardBackground))
            binding.cvBusinessCard.setOnClickListener {
                listenerShare(it)
            }
        }
    }
}

class DiffCallBack: DiffUtil.ItemCallback<BusinessCard>() {
    override fun areItemsTheSame(oldItem: BusinessCard, newItem: BusinessCard) = oldItem == newItem

    override fun areContentsTheSame(oldItem: BusinessCard, newItem: BusinessCard) = oldItem.id == newItem.id

}