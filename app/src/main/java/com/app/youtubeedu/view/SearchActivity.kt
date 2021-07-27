package com.app.youtubeedu.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.app.youtubeedu.R
import com.app.youtubeedu.contract.SearchContract
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.databinding.ActivitySearchBinding
import com.app.youtubeedu.presenter.SearchPresenter

class SearchActivity : BaseActivity<SearchPresenter>(), SearchContract.View {

    private var savedQuery: String? = null
    private lateinit var uiBinding: ActivitySearchBinding
    private lateinit var searchListAdapter: SearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(uiBinding.root)
        searchListAdapter = SearchListAdapter(presenter::onItemClick)
        uiBinding.recyclerView.adapter = searchListAdapter
        presenter.loadVideoList()
        uiBinding.swipeRefreshLayout.setOnRefreshListener {
                savedQuery?.also { presenter::searchVideoByName } ?: presenter.loadVideoList()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchViewItem = menu?.findItem(R.id.search)
        val searchView = searchViewItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                savedQuery = query
                presenter.searchVideoByName(searchView.query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun showVideoList(videoList: List<Video>) {
        searchListAdapter.submitList(videoList)
        uiBinding.swipeRefreshLayout.isRefreshing = false
    }
}