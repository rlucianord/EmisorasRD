package do.emisorasrd.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import do.emisorasrd.R
import do.emisorasrd.data.FavoritesStore
import do.emisorasrd.data.Station

class StationAdapter(
    private val favorites: FavoritesStore,
    private val onPlay: (Station) -> Unit,
    private val onFavorite: () -> Unit
) : RecyclerView.Adapter<StationAdapter.Holder>() {

    private var items: List<Station> = emptyList()

    fun submitList(newItems: List<Station>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.stationName)
        private val meta: TextView = view.findViewById(R.id.stationMeta)
        private val category: TextView = view.findViewById(R.id.stationCategory)
        private val favorite: ImageButton = view.findViewById(R.id.favoriteButton)
        private val play: ImageButton = view.findViewById(R.id.playButton)

        fun bind(station: Station) {
            name.text = station.name
            val location = listOf(station.state, station.language)
                .filter { it.isNotBlank() }
                .joinToString(" · ")
            val bitrate = if (station.bitrate > 0) "${station.bitrate} kbps" else station.codec
            meta.text = listOf(location, bitrate).filter { it.isNotBlank() }.joinToString(" · ")
            category.text = station.category()

            favorite.setImageResource(
                if (favorites.isFavorite(station.stationUuid))
                    android.R.drawable.btn_star_big_on
                else android.R.drawable.btn_star_big_off
            )

            favorite.setOnClickListener {
                favorites.toggle(station.stationUuid)
                notifyItemChanged(bindingAdapterPosition)
                onFavorite()
            }
            play.setOnClickListener { onPlay(station) }
            itemView.setOnClickListener { onPlay(station) }
        }
    }
}
