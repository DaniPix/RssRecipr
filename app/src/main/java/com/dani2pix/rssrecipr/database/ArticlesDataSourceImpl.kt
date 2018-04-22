package com.dani2pix.rssrecipr.database

import android.arch.lifecycle.LiveData

/**
 * Created by dandomnica on 2018-04-22.
 */
class ArticlesDataSourceImpl constructor(private val articlesDao: ArticlesDao) : ArticlesDataSource {
    override fun getAllArticlesObservable(): LiveData<List<Articles>> {
        return articlesDao.getAllArticlesObservable()
    }

    override fun getAllArticles(): List<Articles> {
        return articlesDao.getAllArticles()
    }

    override fun getGroupArticles(articleIds: List<String>): List<Articles> {
        return articlesDao.getGroupedArticles(articleIds)
    }


    override fun insert(entries: List<Articles>) {
        articlesDao.insert(entries)
    }
}