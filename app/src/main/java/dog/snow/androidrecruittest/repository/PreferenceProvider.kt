package dog.snow.androidrecruittest.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dog.snow.androidrecruittest.R


class PreferenceProvider(
    context: Context
) {
    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = appContext.getSharedPreferences(
            appContext.getString(R.string.preference_last_update_key),
            Context.MODE_PRIVATE
        )


    fun setLastUpdate(lastUpdate: Long) {
        with(preference.edit()) {
            putLong(
                appContext.getString(R.string.last_update_key),
                lastUpdate
            )
            commit()
        }
    }

    fun getLastUpdate(): Long {
        return preference.getLong(appContext.getString(R.string.last_update_key), 0L)
    }

}