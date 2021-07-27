package com.app.youtubeedu.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.commit
import com.app.youtubeedu.R
import com.app.youtubeedu.contract.DetailContract
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.databinding.ActivityDetailsBinding
import com.app.youtubeedu.presenter.DetailsPresenter
import com.app.youtubeedu.util.StatsConverter.convertStatsToString
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment

class DetailsActivity :
    BaseActivity<DetailsPresenter>(), DetailContract.View, YouTubePlayer.OnInitializedListener {

    private lateinit var uiBinding: ActivityDetailsBinding
    private lateinit var searchListAdapter: SearchListAdapter
    private lateinit var youTubePlayer: YouTubePlayer
    private val youTubePlayerFragment = YouTubePlayerSupportFragment()
    private lateinit var currentVideo: Video

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(uiBinding.root)
        youTubePlayerFragment.initialize(API_KEY, this)
        supportFragmentManager.commit {
            replace(uiBinding.youTubePlayerFrameLayout.id, youTubePlayerFragment)
        }
        searchListAdapter = SearchListAdapter(presenter::onItemClick)
        uiBinding.recyclerView.adapter = searchListAdapter
        uiBinding.swipeRefreshLayout.setOnRefreshListener {
            presenter.loadRelatedVideoList(currentVideo)
        }
    }

    override fun showRelatedVideoList(videoList: List<Video>) {
        searchListAdapter.submitList(videoList)
        uiBinding.swipeRefreshLayout.isRefreshing = false
    }

    override fun playVideo(video: Video) {
        youTubePlayer.cueVideo(video.videoId)
        youTubePlayer.play()
    }

    override fun showVideoData(video: Video) {
        uiBinding.detailsScrollView.smoothScrollTo(0, 0)
        uiBinding.videoNameTextView.text = video.name
        uiBinding.videoDescriptionTextView.text = video.description
        uiBinding.videoViewsTextView.text = convertStatsToString(video.views, this)
        uiBinding.likesTextView.text = convertStatsToString(video.likes, this)
        uiBinding.dislikesTextView.text = convertStatsToString(video.dislikes, this)
        presenter.loadRelatedVideoList(video)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer,
        wasRestored: Boolean,
    ) {
        youTubePlayer = player
        currentVideo = intent.getParcelableExtra(VIDEO_INTENT_KEY)!!
        presenter.playVideo(currentVideo)
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        error: YouTubeInitializationResult?,
    ) {
        Log.e("Logs", "video player initialization error $error")
        Toast.makeText(this, getString(R.string.playerError), Toast.LENGTH_SHORT).show()
    }

    companion object {

        private const val API_KEY = "AIzaSyByTm6Rjp-rGZ4wG9nu6o98y3ZSFbN1S8A"
        private const val VIDEO_INTENT_KEY = "video"

        fun newIntent(context: Context, video: Video): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(VIDEO_INTENT_KEY, video)
            return intent
        }
    }
}