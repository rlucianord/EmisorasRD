package do.emisorasrd.data

data class Station(
    val stationUuid: String,
    val name: String,
    val streamUrl: String,
    val homepage: String,
    val favicon: String,
    val country: String,
    val state: String,
    val language: String,
    val tags: List<String>,
    val codec: String,
    val bitrate: Int,
    val lastCheckOk: Boolean,
    val frequency: String = "",
    val band: String = "",
    val province: String = "",
    val municipality: String = "",
    val editorialCategory: String = "",
    val editorialSource: String = ""
) {
    fun category(): String = editorialCategory.ifBlank { CategoryClassifier.classify(tags, name) }

    fun searchableText(): String =
        listOf(name, frequency, band, province, municipality, state, language, tags.joinToString(" ")).joinToString(" ")
}

object CategoryClassifier {
    private val rules = linkedMapOf(
        "Noticias y opinión" to listOf("news", "news talk", "talk", "noticias", "politica", "politics", "opinion"),
        "Deportes" to listOf("sports", "sport", "deportes", "futbol", "baseball"),
        "Cristiana" to listOf("christian", "gospel", "religious", "cristiana", "cristiano", "religiosa"),
        "Bachata" to listOf("bachata"),
        "Merengue" to listOf("merengue"),
        "Salsa" to listOf("salsa"),
        "Urbana" to listOf("reggaeton", "urban", "urbana", "dembow"),
        "Rock" to listOf("rock", "alternative", "metal"),
        "Pop" to listOf("pop", "hits", "top 40"),
        "Romántica" to listOf("romantica", "romantic", "balada", "love"),
        "Jazz" to listOf("jazz", "blues"),
        "Electrónica" to listOf("electronic", "dance", "house", "techno"),
        "Variada" to emptyList()
    )

    fun classify(tags: List<String>, name: String): String {
        val haystack = (tags + name).joinToString(" ").lowercase()
        return rules.entries.firstOrNull { (_, words) ->
            words.any { haystack.contains(it) }
        }?.key ?: "Variada"
    }
}
