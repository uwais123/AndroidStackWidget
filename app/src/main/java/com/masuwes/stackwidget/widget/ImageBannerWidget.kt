package com.masuwes.stackwidget.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.masuwes.stackwidget.R

/**
 * Implementation of App Widget functionality.
 */
class ImageBannerWidget : AppWidgetProvider() {

    companion object {

        private const val TOAST_ACTION = "com.dicoding.picodiploma.TOAST_ACTION"
        const val EXTRA_ITEM = "com.dicoding.picodiploma.EXTRA_ITEM"

        private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val intent = Intent(context, StackWidgetService::class.java) // intent data to layout
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            // set view on widget, if there is data, this guy will call context.packageName and put it on stackView
            val views = RemoteViews(context.packageName, R.layout.image_banner_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            // else if there is no data, this guy will show empty view layout
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            // intent data to toast
            val toastIntent = Intent(context, ImageBannerWidget::class.java)
            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()
            val toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    // catch intent and display it on toast with index number
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != null) {
            if (intent.action == TOAST_ACTION) {
                val viewIndex = intent.getIntExtra(EXTRA_ITEM, 0)
                Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

