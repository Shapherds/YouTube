package com.app.youtubeedu.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.app.youtubeedu.R
import com.app.youtubeedu.contract.SearchContract
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.databinding.ActivitySearchBinding
import com.app.youtubeedu.presenter.SearchPresenter
import dagger.android.AndroidInjection
import javax.inject.Inject

class SearchActivity : BaseActivity<SearchPresenter>(), SearchContract.View {

    private lateinit var uiBinding: ActivitySearchBinding
    private lateinit var searchListAdapter: SearchListAdapter

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