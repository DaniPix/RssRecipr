package com.dani2pix.rssrecipr

import android.content.Context
import com.dani2pix.rssrecipr.database.ArticlesDataSource
import com.dani2pix.rssrecipr.database.ArticlesDataSourceImpl
import com.dani2pix.rssrecipr.database.ReciprDatabase

/**
 * Created by dandomnica on 2018-04-22.
 */
class Injection {
    companion object {
        fun provideArticlesDataSource(context: Context?): ArticlesDataSource? {
            context ?: return null
            val db = ReciprDatabase.getInstance(context)
            db ?: return null
            return ArticlesDataSourceImpl(db.articlesDao())
        }

        fun provideViewModelFactory(context: Context?): ViewModelFactory? {
            context ?: return null
            val dataSource = provideArticlesDataSource(context)
            dataSource ?: return null
            return ViewModelFactory(dataSource)
        }
    }
}