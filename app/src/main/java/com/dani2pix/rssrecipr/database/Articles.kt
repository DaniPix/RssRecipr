package com.dani2pix.rssrecipr.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by dandomnica on 2018-04-22.
 */
@Entity(tableName = "articles", indices = arrayOf(Index(value = "articleId", unique = true)))
data class Articles(
        val articleId: String,
        val articleTitle: String,
        val articleLink: String,
        val articleContent: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Ignore
    var contentToTransform: List<String> = listOf()
}

