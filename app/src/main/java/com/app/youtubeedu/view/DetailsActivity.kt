package com.app.youtubeedu.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.youtubeedu.R
import com.app.youtubeedu.contract.DetailContract
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.databinding.ActivityDetailsBinding
import com.app.youtubeedu.interactor.RelatedVideoLoaderInteractorImpl
import com.app.youtubeedu.presenter.DetailsPresenter
import com.app.youtubeedu.repository.VideoRepositoryImpl
import com.app.youtubeedu.router.DetailsRouter
import com.app.youtubeedu.util.StatsConverter.convertStatsToString
import com.app.youtubeedu.util.StringProviderImpl
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class DetailsActivity :
    BaseActivity<DetailsPresenter>(), DetailContract.View, YouTubePlayer.OnInitializedListener {

    private lateinit var uiBinding: ActivityDetailsBinding
    private lateinit var searchListAdapter: SearchListAdapter
    private lateinit var youTubePlayer: YouTubePlayer

    override fun showRelatedVideoList(videoList: List<Video>) {
        searchListAdapter.submitList(videoList)
    }

    override fun playVideo(video: Video) {
        youTubePlayer.cueVideo(video.videoId)
        youTubePlayer.play()
    }

    override fun createPresenter(): DetailsPresenter {
        return DetailsPresenter(
            DetailsRouter(this),
            RelatedVideoLoaderInteractorImpl(VideoRepositoryImpl()),
            StringProviderImpl(this)
        )
    }

    override fun updateVideoDetails(video: Video) {
        uiBinding.videoNameTextView.text = video.name
        uiBinding.videoDescriptionTextView.text = video.description
        uiBinding.videoViewsTextView.text = convertStatsToString(video.views, this)
        uiBinding.likesTextView.text = convertStatsToString(video.likes, this)
        uiBinding.dislikesTextView.text = convertStatsToString(video.dislikes, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(uiBinding.root)
        initializePlayer()
        searchListAdapter = SearchListAdapter()
        uiBinding.recyclerView.adapter = searchListAdapter
        val video: Video = intent.getParcelableExtra(VIDEO_INTENT_KEY)!!
        presenter.playVideo(video)
    }

    private fun initializePlayer() {
        TODO("not yet implemented , will be in task ~ 12")
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer,
        wasRestored: Boolean,
    ) {
        youTubePlayer = player
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        error: YouTubeInitializationResult?,
    ) {
        Log.e("Logs", "video player initialization error $error")
        Toast.makeText(this, getString(R.string.playerError), Toast.LENGTH_SHORT).show()
    }

    companion object {

        private const val VIDEO_INTENT_KEY = "video"

        fun newIntent(context: Context, video: Video): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(VIDEO_INTENT_KEY, video)
            return intent
        }
    }
}