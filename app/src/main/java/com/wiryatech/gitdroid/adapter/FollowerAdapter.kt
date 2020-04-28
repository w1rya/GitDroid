package com.wiryatech.gitdroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.model.Follower
import kotlinx.android.synthetic.main.item_detail.view.*

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.ListViewHolder>() {

    private val mData = ArrayList<Follower>()

    fun setData(items: ArrayList<Follower>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(follower: Follower) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(follower.avatar)
                    .apply(RequestOptions().override(64,64))
                    .into(iv_avatar)

                tv_username.text = follower.username
            }
        }
    }
}