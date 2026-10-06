package do.emisorasrd.data

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object EditorialCatalog {
    private const val FILE = "catalog/stations_editorial.csv"

    fun load(context: Context): List<EditorialStation> {
        return runCatching {
            context.assets.open(FILE).use { input ->
                BufferedReader(InputStreamReader(input, Charsets.UTF_8)).useLines { lines ->
                    lines.drop(1).mapNotNull { line -> parse(line) }.toList()
                }
            }
        }.getOrDefault(emptyList())
    }

    private fun parse(line: String): EditorialStation? {
        val fields = parseCsvLine(line)
        if (fields.size < 8) return null
        return EditorialStation(
            name = fields[0], frequency = fields[1], band = fields[2], province = fields[3],
            municipality = fields[4], category = fields[5], officialSource = fields[6], notes = fields[7]
        )
    }

    private fun parseCsvLine(line: String): List<String> {
        val out = mutableListOf<String>(); val current = StringBuilder(); var quoted = false; var i = 0
        while (i < line.length) {
            val c = line[i]
            when {
                c == '"' && i + 1 < line.length && line[i + 1] == '"' -> { current.append('"'); i++ }
                c == '"' -> quoted = !quoted
                c == ',' && !quoted -> { out += current.toString(); current.setLength(0) }
                else -> current.append(c)
            }
            i++
        }
        out += current.toString()
        return out
    }

    fun normalize(value: String): String = value.lowercase()
        .replace("á", "a").replace("é", "e").replace("í", "i")
        .replace("ó", "o").replace("ú", "u").replace("ü", "u").replace("ñ", "n")
        .replace(Regex("[^a-z0-9]"), "")
}
