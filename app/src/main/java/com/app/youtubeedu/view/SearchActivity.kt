package com.app.youtubeedu.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.app.youtubeedu.R
import com.app.youtubeedu.contract.SearchContract
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.databinding.ActivitySearchBinding
import com.app.youtubeedu.interactor.PopularVideoLoaderInteractorImpl
import com.app.youtubeedu.interactor.VideoByNameLoaderInteractorImpl
import com.app.youtubeedu.presenter.SearchPresenter
import com.app.youtubeedu.repository.VideoRepositoryImpl
import com.app.youtubeedu.router.SearchRouter
import com.app.youtubeedu.util.StringProviderImpl

class SearchActivity : BaseActivity<SearchPresenter>(), SearchContract.View {

    private lateinit var uiBinding: ActivitySearchBinding
    private lateinit var searchListAdapter: SearchListAdapter

    override fun createPresenter(): SearchPresenter {
        return SearchPresenter(
            SearchRouter(this),
            VideoByNameLoaderInteractorImpl(VideoRepositoryImpl()),
            PopularVideoLoaderInteractorImpl(VideoRepositoryImpl()),
            StringProviderImpl(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(uiBinding.root)
        searchListAdapter = SearchListAdapter()
        uiBinding.recyclerView.adapter = searchListAdapter
        presenter.loadVideoList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchViewItem = menu?.findItem(R.id.search)
        val searchView = searchViewItem?.actionView as SearchView
        searchView.setOnSearchClickListener { presenter.searchVideoByName(searchView.query.toString()) }
        return super.onCreateOptionsMenu(menu)
    }

    override fun showVideoList(videoList: List<Video>) {
        searchListAdapter.submitList(videoList)
    }
}