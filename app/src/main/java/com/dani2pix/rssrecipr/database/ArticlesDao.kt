package com.dani2pix.rssrecipr.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

/**
 * Created by dandomnica on 2018-04-22.
 */
@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles")
    fun getAllArticlesObservable(): LiveData<List<Articles>>

    @Query("SELECT * FROM articles")
    fun getAllArticles(): List<Articles>

    @Query("SELECT * FROM articles WHERE articleId IN (:articleIds)")
    fun getGroupedArticles(articleIds: List<String>): List<Articles>

    @Insert(onConflict = REPLACE)
    fun insert(entries: List<Articles>)
}