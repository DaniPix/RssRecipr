package com.dani2pix.rssrecipr.dashboard.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dani2pix.rssrecipr.Injection

import com.dani2pix.rssrecipr.R
import com.dani2pix.rssrecipr.database.Articles
import com.dani2pix.rssrecipr.util.XmlParser
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.experimental.launch

class DashboardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = Injection.provideViewModelFactory(activity?.applicationContext)
        ViewModelProviders.of(this, viewModelFactory)
                .get(ArticlesViewModel::class.java)
                .getAllArticles()
                .observe(this, Observer<List<Articles>> { items ->
                    val articles = items ?: listOf()
                    val articlesAdapter = ArticlesAdapter(activity!!.applicationContext, articles, true)
                    articlesList.layoutManager = LinearLayoutManager(activity)
                    articlesList.adapter = articlesAdapter
                })

        launch {
            val sources = listOf("https://www.theverge.com/apple/rss/index.xml",
                    "https://www.theverge.com/google/rss/index.xml",
                    "https://www.theverge.com/mobile/rss/index.xml",
                    "https://www.theverge.com/culture/rss/index.xml",
                    "https://www.theverge.com/apps/rss/index.xml")
            XmlParser.parseRssEntries(sources, activity?.applicationContext).await()
        }
    }
}
