package com.intent.initializer

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.intent.core.data.database.AppDatabase
import com.intent.core.data.datastore.APP_PREFERENCES_NAME
import com.intent.core.data.datastore.dataStorePreferences
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
        PlutoDatastoreWatcher.watch(
            APP_PREFERENCES_NAME,
            context.dataStorePreferences
        )
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
