package com.dani2pix.rssrecipr.dashboard.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.dani2pix.rssrecipr.database.Articles
import com.dani2pix.rssrecipr.database.ArticlesDataSource
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async

/**
 * Created by dandomnica on 2018-04-22.
 */
class ArticlesViewModel constructor(val articlesDataSource: ArticlesDataSource) : ViewModel() {


    fun getAllArticles(): LiveData<List<Articles>> {
        return articlesDataSource.getAllArticlesObservable()
    }

    fun insertArticles(entries: List<Articles>) {
        async(CommonPool) {
            articlesDataSource.insert(entries)
        }
    }
}