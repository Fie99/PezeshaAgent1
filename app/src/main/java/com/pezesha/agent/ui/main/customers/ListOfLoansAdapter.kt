package com.pezesha.agent.ui.main.customers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pezesha.agent.data.local.dummyData.CustomerList
import com.pezesha.agent.data.local.dummyData.LoanList
import com.pezesha.agent.databinding.ListItemCustomerListBinding
import com.pezesha.agent.databinding.ListItemLoanBinding

class ListOfLoansAdapter (
    private val loanList:List<LoanList>,
) : RecyclerView.Adapter<ListOfLoansAdapter.ListOfLoansViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ListOfLoansAdapter.ListOfLoansViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ListItemLoanBinding = ListItemLoanBinding.inflate(inflater)
        return ListOfLoansViewHolder(binding)
    }
    override fun onBindViewHolder(holder:ListOfLoansViewHolder, position: Int) {
        holder.bind(loanList[position])
    }
    override fun getItemCount(): Int {
        return loanList.size
    }
    class ListOfLoansViewHolder(private val binding: ListItemLoanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loanList: LoanList) {
            binding.apply {
               tvCustomerName.text = loanList.customerName
                tvLoanAmount.text = loanList.loanAmount
                tvLoanStatus.text=loanList.loanStatus
            }
        }
    }
}
