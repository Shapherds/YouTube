package com.app.youtubeedu.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.databinding.VideoListItemBinding
import com.bumptech.glide.Glide

class SearchListAdapter(private val onClickListener: (Video) -> Unit) :
    ListAdapter<Video, SearchListAdapter.ViewHolder>(SearchListCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val layoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val uiBinding = VideoListItemBinding.inflate(layoutInflater)
        return ViewHolder(uiBinding, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.currentVideo = getItem(position)
        holder.bind()
    }

    class ViewHolder(private val uiBinding: VideoListItemBinding, handler: (Video) -> Unit) :
        RecyclerView.ViewHolder(uiBinding.root) {

        private val listener: View.OnClickListener = View.OnClickListener { handler(currentVideo) }
        lateinit var currentVideo: Video

        fun bind() {
            Glide.with(uiBinding.root.context)
                .load(currentVideo.iconUri)
                .into(uiBinding.videoIconImageView)
            uiBinding.videoNameTextView.text = currentVideo.name
            uiBinding.videoViewCountTextView.text = currentVideo.views.toString()
            uiBinding.root.setOnClickListener(listener)
        }
    }
}
