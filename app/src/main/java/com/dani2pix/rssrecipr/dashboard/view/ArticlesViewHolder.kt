package com.dani2pix.rssrecipr.dashboard.view

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.article_item_view.view.*

/**
 * Created by dandomnica on 2018-04-11.
 */
class ArticlesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val title = view.articleTitle
    val description = view.articleDescription
    val read = view.readSimilarArticles

}