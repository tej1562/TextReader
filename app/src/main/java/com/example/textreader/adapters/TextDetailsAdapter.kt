package com.example.textreader.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.textreader.R
import com.example.textreader.db.entities.TextDetails

class TextDetailsAdapter : RecyclerView.Adapter<TextDetailsAdapter.TextDetailsViewHolder>() {

    inner class TextDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDetails: TextView = itemView.findViewById(R.id.tvDetails)
    }

    val differCallback = object : DiffUtil.ItemCallback<TextDetails>() {
        override fun areItemsTheSame(oldItem: TextDetails, newItem: TextDetails): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: TextDetails, newItem: TextDetails): Boolean {
            return (oldItem.hashCode() == newItem.hashCode())
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextDetailsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_text_details, parent, false)
        return TextDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextDetailsViewHolder, position: Int) {
        val textDetails = differ.currentList[position]
        holder.tvDetails.text = textDetails.text
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}