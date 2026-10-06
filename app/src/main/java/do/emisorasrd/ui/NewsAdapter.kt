package do.emisorasrd.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import do.emisorasrd.R
import do.emisorasrd.data.NewsItem

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.Holder>() {
    private var items: List<NewsItem> = emptyList()

    fun submitList(value: List<NewsItem>) {
        items = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(items[position])
    override fun getItemCount() = items.size

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(android.R.id.text1)
        private val subtitle = view.findViewById<TextView>(android.R.id.text2)

        fun bind(item: NewsItem) {
            title.text = item.title
            subtitle.text = "${item.source} · ${item.published}"
            itemView.setOnClickListener {
                if (item.link.isNotBlank()) {
                    itemView.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.link)))
                }
            }
        }
    }
}
