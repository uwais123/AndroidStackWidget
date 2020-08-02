package com.masuwes.stackwidget.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.masuwes.stackwidget.R

internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>() // declare widget item as Array Bitmap

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        //  useful for refreshing data when it changed
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.darth_vader))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.star_wars_logo))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.storm_trooper))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.starwars))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.falcon))
    }

    override fun onDestroy() {

    }

    // same like the recyclerView
    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item) // get the context in packageName, and display it to widget_item
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position]) // set image bitmap into the imageView

        val extras = bundleOf(
            ImageBannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras) // as parcelable to input on onClickFillIntent

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    // this guy need to declare greater then 0
    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}

















// end