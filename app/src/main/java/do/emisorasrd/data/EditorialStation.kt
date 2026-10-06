package do.emisorasrd.data

data class EditorialStation(
    val name: String,
    val frequency: String,
    val band: String,
    val province: String,
    val municipality: String,
    val category: String,
    val officialSource: String,
    val notes: String
)
