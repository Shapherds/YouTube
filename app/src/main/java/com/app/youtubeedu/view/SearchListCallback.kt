package com.app.youtubeedu.view

import androidx.recyclerview.widget.DiffUtil
import com.app.youtubeedu.data.Video

object SearchListCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.videoId == newItem.videoId
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.name == newItem.name && oldItem.views == newItem.views && oldItem.iconUri == newItem.iconUri
    }
}