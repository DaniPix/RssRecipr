package com.dani2pix.rssrecipr.dashboard.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dani2pix.rssrecipr.R
import com.dani2pix.rssrecipr.RssReciprActivity
import com.dani2pix.rssrecipr.database.Articles
import com.dani2pix.rssrecipr.util.PicassoImageGetter

/**
 * Created by dandomnica on 2018-04-11.
 */
class ArticlesAdapter(private val context: Context, private val items: List<Articles>, private val dashboard: Boolean) : RecyclerView.Adapter<ArticlesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.article_item_view, parent, false)

        return ArticlesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val feedEntry = items[position]

        holder.title?.setText(feedEntry.articleTitle)
        holder.description?.setText(Html.fromHtml(feedEntry.articleContent, Html.FROM_HTML_MODE_LEGACY, PicassoImageGetter(context.resources, holder.description), null))
        if (dashboard) {
            holder.read.setVisibility(View.VISIBLE)
        }
        holder.read?.setOnClickListener {
            RssReciprActivity.startActivityWithArgs(context, R.string.title_subscription, RssReciprActivity.FRAGMENT_GROUP, feedEntry.articleId)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}