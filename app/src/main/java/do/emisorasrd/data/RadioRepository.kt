package do.emisorasrd.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.util.concurrent.TimeUnit

class RadioRepository(private val context: Context) {
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    suspend fun fetchStations(): List<Station> = withContext(Dispatchers.IO) {
        val url = "https://de1.api.radio-browser.info/json/stations/search" +
            "?countrycode=DO&countrycodeExact=true&hidebroken=true&order=name&limit=500"
        val request = Request.Builder().url(url)
            .header("User-Agent", "EmisorasRD/1.1 (Dominican Republic radio directory)")
            .header("Accept", "application/json").get().build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) error("Radio Browser HTTP ${response.code}")
            mergeEditorial(parseStations(response.body.string()), EditorialCatalog.load(context))
        }
    }

    private fun parseStations(body: String): List<Station> {
        val array = JSONArray(body); val result = mutableListOf<Station>()
        for (i in 0 until array.length()) {
            val o = array.getJSONObject(i)
            val stream = o.optString("url_resolved").ifBlank { o.optString("url") }
            if (stream.isBlank() || !o.optBoolean("lastcheckok", true)) continue
            val tags = o.optString("tags").split(",").map { it.trim() }.filter { it.isNotBlank() }
            result += Station(
                stationUuid = o.optString("stationuuid"), name = o.optString("name").ifBlank { "Emisora sin nombre" },
                streamUrl = stream, homepage = o.optString("homepage"), favicon = o.optString("favicon"),
                country = o.optString("country"), state = o.optString("state"), language = o.optString("language"),
                tags = tags, codec = o.optString("codec"), bitrate = o.optInt("bitrate"),
                lastCheckOk = o.optBoolean("lastcheckok", true)
            )
        }
        return result.distinctBy { it.stationUuid.ifBlank { it.streamUrl } }
    }

    private fun mergeEditorial(stations: List<Station>, editorial: List<EditorialStation>): List<Station> {
        val byName = editorial.associateBy { EditorialCatalog.normalize(it.name) }
        return stations.map { station ->
            val match = editorial.minByOrNull { distance(EditorialCatalog.normalize(it.name), EditorialCatalog.normalize(station.name)) }
            val e = match?.takeIf { distance(EditorialCatalog.normalize(it.name), EditorialCatalog.normalize(station.name)) <= 4 }
                ?: byName[EditorialCatalog.normalize(station.name)]
            if (e == null) station else station.copy(
                frequency = e.frequency, band = e.band, province = e.province,
                municipality = e.municipality, editorialCategory = e.category, editorialSource = e.officialSource
            )
        }
    }

    private fun distance(a: String, b: String): Int {
        if (a == b) return 0
        val prev = IntArray(b.length + 1) { it }
        for (i in a.indices) {
            val cur = IntArray(b.length + 1); cur[0] = i + 1
            for (j in b.indices) cur[j + 1] = minOf(cur[j] + 1, prev[j + 1] + 1, prev[j] + if (a[i] == b[j]) 0 else 1)
            for (j in cur.indices) prev[j] = cur[j]
        }
        return prev[b.length]
    }
}
