package com.dani2pix.rssrecipr

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dani2pix.rssrecipr.dashboard.view.ArticlesViewModel
import com.dani2pix.rssrecipr.database.ArticlesDataSource

/**
 * Created by dandomnica on 2018-04-22.
 */
class ViewModelFactory constructor(val articlesDataSource: ArticlesDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticlesViewModel::class.java)) {
            return modelClass.cast(ArticlesViewModel(articlesDataSource))
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}