package com.example.textreader.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.textreader.R
import com.example.textreader.db.entities.AppInfo

class AppInfoAdapter : RecyclerView.Adapter<AppInfoAdapter.AppInfoViewHolder>() {

    inner class AppInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAppName: TextView = itemView.findViewById(R.id.tvAppName)
        val ivAppIcon: ImageView = itemView.findViewById(R.id.ivAppIcon)
    }

    val differCallback = object : DiffUtil.ItemCallback<AppInfo>() {
        override fun areItemsTheSame(oldItem: AppInfo, newItem: AppInfo): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: AppInfo, newItem: AppInfo): Boolean {
            return (oldItem.hashCode() == newItem.hashCode())
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_app_preview, parent, false)
        return AppInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppInfoViewHolder, position: Int) {
        val textInfo = differ.currentList[position]
        holder.tvAppName.text = textInfo.appName
        holder.ivAppIcon.setImageBitmap(textInfo.appImg)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(textInfo)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((AppInfo) -> Unit)? = null

    fun setOnItemClickListener(listener: (AppInfo) -> Unit) {
        onItemClickListener = listener
    }
}