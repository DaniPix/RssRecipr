package com.dani2pix.rssrecipr.database

import android.arch.lifecycle.LiveData

/**
 * Created by dandomnica on 2018-04-22.
 */
interface ArticlesDataSource {
    fun getAllArticlesObservable(): LiveData<List<Articles>>

    fun getAllArticles(): List<Articles>

    fun getGroupArticles(articleIds: List<String>): List<Articles>

    fun insert(entries: List<Articles>)
}