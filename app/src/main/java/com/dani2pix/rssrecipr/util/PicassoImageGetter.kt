package com.dani2pix.rssrecipr.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/**
 * Created by dandomnica on 2018-04-11.
 */
class PicassoImageGetter(private val resources: Resources, private val content: TextView) : Html.ImageGetter {

    override fun getDrawable(source: String?): Drawable {
        val drawable = BitmapDrawablePlaceHolder(resources, content)
        Picasso.get().load(source).into(drawable)

        return drawable
    }

    private class BitmapDrawablePlaceHolder(val resources: Resources, val content: TextView) : BitmapDrawable(), Target {

        var placeHolderDrawable: Drawable? = null

        override fun draw(canvas: Canvas?) {
            super.draw(canvas)
            placeHolderDrawable?.draw(canvas)
        }

        private fun setDrawable(drawable: Drawable) {
            this.placeHolderDrawable = drawable
            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            drawable.setBounds(0, 0, width, height)
            setBounds(0, 0, width, height)
            content.setText(content.getText())
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            // do nothing
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            // do nothing
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            setDrawable(BitmapDrawable(resources, bitmap))
        }
    }
}