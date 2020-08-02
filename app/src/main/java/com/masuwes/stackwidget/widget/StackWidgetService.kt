package com.masuwes.stackwidget.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetService: RemoteViewsService() {

    // helper(?) to get StackViewsRemote
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory = // return
        StackRemoteViewsFactory(this.applicationContext)


}