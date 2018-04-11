package com.dani2pix.rssrecipr.dashboard.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dani2pix.rssrecipr.R
import com.dani2pix.rssrecipr.util.XmlParser
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext


class DashboardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            val items = XmlParser.parseRssEntries("https://www.theverge.com/apple/rss/index.xml").await()
            withContext(UI) {
                val articlesAdapter = ArticlesAdapter(activity!!.applicationContext, items)

                articlesList.layoutManager = LinearLayoutManager(activity)
                articlesList.adapter = articlesAdapter
            }
        }

    }
}
