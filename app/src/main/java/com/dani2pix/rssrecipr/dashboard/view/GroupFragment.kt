package com.dani2pix.rssrecipr.dashboard.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.dani2pix.rssrecipr.Injection
import com.dani2pix.rssrecipr.R
import com.dani2pix.rssrecipr.RssReciprActivity.Companion.EXTRA_ARGS
import com.dani2pix.rssrecipr.algorithm.ClusteringUtils
import com.dani2pix.rssrecipr.algorithm.ContentUtils
import com.dani2pix.rssrecipr.database.Articles
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

/**
 * Created by dandomnica on 2018-04-22.
 */
class GroupFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleId: String? = arguments?.getString(EXTRA_ARGS)
        articleId ?: return
        var articlesIds: MutableList<String> = mutableListOf()
        val articlesDataSource = Injection.provideArticlesDataSource(activity?.applicationContext)

        progress.setVisibility(View.VISIBLE)

        launch {
            val allArticles = articlesDataSource?.getAllArticles()

            if (allArticles != null) {
                val entriesToCluster = ContentUtils.prepareEntriesForClustering(allArticles)
                val dictionary = ContentUtils.generateDictionary(entriesToCluster)
                ClusteringUtils.generateSimilarityMatrix(entriesToCluster,  dictionary,15)
                val groups = ClusteringUtils.getClusteredGroups()
                for (group in groups) {
                    if (group.contains(articleId)) {
                        articlesIds = group.split("%%%".toRegex()).toMutableList()
                        break
                    }
                }
            }
            val articles = articlesDataSource?.getGroupArticles(articlesIds)
            withContext(UI) {
                if (articles != null && articles.isNotEmpty() && isAdded) {
                    progress.setVisibility(View.GONE)
                    val articlesAdapter = ArticlesAdapter(activity!!.applicationContext, articles, false)
                    articlesList.layoutManager = LinearLayoutManager(activity)
                    articlesList.adapter = articlesAdapter
                }
            }
        }
    }
}
