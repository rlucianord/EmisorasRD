package do.emisorasrd.data

import android.content.Context

class FavoritesStore(context: Context) {
    private val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    fun isFavorite(uuid: String): Boolean = prefs.getBoolean(uuid, false)

    fun toggle(uuid: String): Boolean {
        val newValue = !isFavorite(uuid)
        prefs.edit().putBoolean(uuid, newValue).apply()
        return newValue
    }

    fun all(): Set<String> = prefs.all.keys
}
