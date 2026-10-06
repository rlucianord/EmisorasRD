package do.emisorasrd.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import android.util.Xml
import java.io.StringReader
import java.util.concurrent.TimeUnit

data class NewsItem(
    val title: String,
    val link: String,
    val description: String,
    val published: String,
    val source: String
)

class RssRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(12, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    private val sources = listOf(
        RssSource("Radio República · Deportes", "https://www.radiorepublica.do/rss/deportes/"),
        RssSource("Radio República · Economía", "https://www.radiorepublica.do/rss/economia/"),
        RssSource("Radio República · Nacionales", "https://www.radiorepublica.do/rss/nacional/"),
        RssSource("Radio República · Cultura", "https://www.radiorepublica.do/rss/cultura/"),
        RssSource("Radio República · Entretenimiento", "https://www.radiorepublica.do/rss/entretenimiento/")
    )

    suspend fun fetchNews(): List<NewsItem> = withContext(Dispatchers.IO) {
        sources.flatMap { source ->
            runCatching { fetchSource(source) }.getOrDefault(emptyList())
        }.sortedByDescending { it.published }.take(60)
    }

    private fun fetchSource(source: RssSource): List<NewsItem> {
        val request = Request.Builder()
            .url(source.url)
            .header("User-Agent", "EmisorasRD/1.0")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return emptyList()
            val xml = response.body.string()
            return parse(xml, source.name)
        }
    }

    private fun parse(xml: String, source: String): List<NewsItem> {
        val parser = Xml.newPullParser()
        parser.setInput(StringReader(xml))
        val result = mutableListOf<NewsItem>()

        var insideItem = false
        var currentTag = ""
        var title = ""
        var link = ""
        var description = ""
        var pubDate = ""

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            when (parser.eventType) {
                XmlPullParser.START_TAG -> {
                    currentTag = parser.name
                    if (parser.name.equals("item", true)) {
                        insideItem = true
                        title = ""; link = ""; description = ""; pubDate = ""
                    }
                }
                XmlPullParser.TEXT -> if (insideItem) {
                    when (currentTag.lowercase()) {
                        "title" -> title = parser.text
                        "link" -> link = parser.text
                        "description" -> description = parser.text
                        "pubdate", "published", "updated" -> pubDate = parser.text
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name.equals("item", true)) {
                        if (title.isNotBlank()) result += NewsItem(
                            title.trim(), link.trim(), description.trim(), pubDate.trim(), source
                        )
                        insideItem = false
                    }
                    currentTag = ""
                }
            }
        }
        return result
    }
}

private data class RssSource(val name: String, val url: String)
