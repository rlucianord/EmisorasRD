package do.emisorasrd

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startForegroundService
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import do.emisorasrd.data.FavoritesStore
import do.emisorasrd.data.NewsItem
import do.emisorasrd.data.RadioRepository
import do.emisorasrd.data.RssRepository
import do.emisorasrd.data.Station
import do.emisorasrd.playback.RadioPlaybackService
import do.emisorasrd.ui.NewsAdapter
import do.emisorasrd.ui.StationAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var radioRepository: RadioRepository
    private val rssRepository = RssRepository()
    private lateinit var favorites: FavoritesStore
    private lateinit var adapter: StationAdapter
    private lateinit var newsAdapter: NewsAdapter

    private var allStations: List<Station> = emptyList()
    private var selectedCategory = "Todas"
    private var selectedProvince = "Todas"
    private var searchJob: Job? = null

    private lateinit var progress: ProgressBar
    private lateinit var emptyText: TextView
    private lateinit var stationList: RecyclerView
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var playerBar: View
    private lateinit var playerStation: TextView
    private lateinit var playerButton: ImageButton

    private val notificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        favorites = FavoritesStore(this)
        radioRepository = RadioRepository(this)
        bindViews()
        configureStationList()
        configureSearch()
        configureNavigation()
        requestNotificationsIfNeeded()
        loadStations()
    }

    private fun bindViews() {
        progress = findViewById(R.id.progress)
        emptyText = findViewById(R.id.emptyText)
        stationList = findViewById(R.id.stationList)
        bottomNav = findViewById(R.id.bottomNavigation)
        playerBar = findViewById(R.id.playerBar)
        playerStation = findViewById(R.id.playerStation)
        playerButton = findViewById(R.id.playerButton)
    }

    private fun configureStationList() {
        adapter = StationAdapter(
            favorites = favorites,
            onPlay = ::playStation,
            onFavorite = { renderStations() }
        )
        stationList.layoutManager = LinearLayoutManager(this)
        stationList.adapter = adapter
    }

    private fun configureSearch() {
        val input = findViewById<TextInputEditText>(R.id.searchInput)
        input.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    delay(180)
                    renderStations(s?.toString().orEmpty())
                }
            }
            override fun afterTextChanged(s: android.text.Editable?) = Unit
        })
    }

    private fun configureNavigation() {
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_stations -> {
                    findViewById<RecyclerView>(R.id.stationList).visibility = View.VISIBLE
                    renderStations()
                    true
                }
                R.id.nav_favorites -> {
                    renderStations(onlyFavorites = true)
                    true
                }
                R.id.nav_news -> {
                    loadNews()
                    true
                }
                else -> false
            }
        }
    }

    private fun loadStations() {
        progress.visibility = View.VISIBLE
        lifecycleScope.launch {
            runCatching { radioRepository.fetchStations() }
                .onSuccess {
                    allStations = it
                    buildCategoryChips()
                    buildProvinceChips()
                    renderStations()
                }
                .onFailure {
                    emptyText.text = getString(R.string.error_loading)
                    emptyText.visibility = View.VISIBLE
                }
            progress.visibility = View.GONE
        }
    }

    private fun buildCategoryChips() {
        val group = findViewById<ChipGroup>(R.id.categoryGroup)
        group.removeAllViews()

        val categories = listOf("Todas") +
            allStations.map { it.category() }.distinct().sorted()

        categories.forEach { category ->
            val chip = Chip(this).apply {
                text = category
                isCheckable = true
                isChecked = category == selectedCategory
                setOnClickListener {
                    selectedCategory = category
                    renderStations()
                }
            }
            group.addView(chip)
        }
    }


    private fun buildProvinceChips() {
        val group = findViewById<ChipGroup>(R.id.provinceGroup)
        group.removeAllViews()
        val provinces = listOf("Todas") + allStations.map { it.province.ifBlank { it.state } }
            .filter { it.isNotBlank() }.distinct().sorted()
        provinces.forEach { province ->
            val chip = Chip(this).apply {
                text = province
                isCheckable = true
                isChecked = province == selectedProvince
                setOnClickListener {
                    selectedProvince = province
                    renderStations()
                }
            }
            group.addView(chip)
        }
    }

    private fun renderStations(query: String = "", onlyFavorites: Boolean = false) {
        val normalized = query.trim().lowercase()

        val filtered = allStations.filter { station ->
            val matchesCategory = selectedCategory == "Todas" || station.category() == selectedCategory
            val province = station.province.ifBlank { station.state }
            val matchesProvince = selectedProvince == "Todas" || province == selectedProvince
            val matchesQuery = normalized.isBlank() || station.searchableText().lowercase().contains(normalized)
            val matchesFavorite = !onlyFavorites || favorites.isFavorite(station.stationUuid)
            matchesCategory && matchesProvince && matchesQuery && matchesFavorite
        }

        stationList.visibility = View.VISIBLE
        emptyText.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
        adapter.submitList(filtered)
    }

    private fun loadNews() {
        progress.visibility = View.VISIBLE
        lifecycleScope.launch {
            val news = runCatching { rssRepository.fetchNews() }.getOrDefault(emptyList())
            showNews(news)
            progress.visibility = View.GONE
        }
    }

    private fun showNews(items: List<NewsItem>) {
        stationList.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapter()
        stationList.adapter = newsAdapter
        newsAdapter.submitList(items)
        emptyText.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun playStation(station: Station) {
        val intent = RadioPlaybackService.intent(this, station)
        startForegroundService(this, intent)
        playerBar.visibility = View.VISIBLE
        playerStation.text = station.name
        playerButton.setImageResource(android.R.drawable.ic_media_pause)
    }

    private fun requestNotificationsIfNeeded() {
        if (android.os.Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
