package com.crux.initializer

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.crux.data.database.AppDatabase
import com.pluto.Pluto
import com.pluto.plugins.datastore.pref.PlutoDatastorePreferencesPlugin
import com.pluto.plugins.datastore.pref.PlutoDatastoreWatcher
import com.pluto.plugins.exceptions.PlutoExceptionsPlugin
import com.pluto.plugins.layoutinspector.PlutoLayoutInspectorPlugin
import com.pluto.plugins.logger.PlutoLoggerPlugin
import com.pluto.plugins.network.PlutoNetworkPlugin
import com.pluto.plugins.rooms.db.PlutoRoomsDBWatcher
import com.pluto.plugins.rooms.db.PlutoRoomsDatabasePlugin

class PlutoInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Pluto.Installer(context as Application)
            .addPlugin(PlutoNetworkPlugin())
            .addPlugin(PlutoExceptionsPlugin())
            .addPlugin(PlutoLoggerPlugin())
            .addPlugin(PlutoRoomsDatabasePlugin())
            .addPlugin(PlutoDatastorePreferencesPlugin())
            .addPlugin(PlutoLayoutInspectorPlugin())
            .install()

        PlutoRoomsDBWatcher.watch(
            name = AppDatabase.DATABASE_NAME,
            dbClass = AppDatabase::class.java
        )
//        PlutoDatastoreWatcher.watch(
//            APP_PREFERENCES_NAME,
//            context.appPreferences
//        )
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
