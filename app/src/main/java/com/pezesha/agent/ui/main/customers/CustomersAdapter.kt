package com.pezesha.agent.ui.main.customers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pezesha.agent.data.local.dummyData.Customer
import com.pezesha.agent.databinding.ListItemCustomerBinding

class CustomersAdapter(private val clickListener: (Customer) -> Unit) :
    ListAdapter<Customer, CustomerViewHolder>(CustomerDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = ListItemCustomerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class CustomerViewHolder(
    private val binding: ListItemCustomerBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(customer: Customer, clickListener: (Customer) -> Unit) {
        binding.apply {
            tvCustomerName.text = customer.customerName
            tvInitial.text = customer.customerName[0].toString()
            tvBusinessName.text = customer.businessName
            tvStatus.text = if (customer.isRegistrationComplete == 1) "Completed" else "Incomplete"
            root.setOnClickListener { clickListener(customer) }
        }
    }

}

object CustomerDiffUtil : DiffUtil.ItemCallback<Customer>() {
    override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
        return oldItem == newItem
    }

}


