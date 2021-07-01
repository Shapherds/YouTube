package com.app.youtubeedu.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.databinding.VideoListItemBinding
import com.bumptech.glide.Glide

class SearchListAdapter :
    ListAdapter<Video, SearchListAdapter.ViewHolder>(SearchListCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val uiBinding = VideoListItemBinding.inflate(layoutInflater)
        return ViewHolder(uiBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentVideo = getItem(position)
        holder.bind(currentVideo)
    }

    class ViewHolder(private val uiBinding: VideoListItemBinding) :
        RecyclerView.ViewHolder(uiBinding.root) {

        fun bind(currentVideo: Video) {
            Glide.with(uiBinding.root.context)
                .load(currentVideo.iconUri)
                .into(uiBinding.videoIconImageView)
            uiBinding.videoNameTextView.text = currentVideo.name
            uiBinding.videoViewCountTextView.text = currentVideo.views.toString()
        }
    }
}
