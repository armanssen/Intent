package com.intent.initializer

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
//        val tree: Timber.Tree = if (BuildConfig.DEBUG) {
//            PlutoTimberTree()
//        } else {
//            TimberProductionTree()
//        }
//        Timber.plant(tree)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}

private class TimberProductionTree : Timber.Tree() {
    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?
    ) {
    }
}
