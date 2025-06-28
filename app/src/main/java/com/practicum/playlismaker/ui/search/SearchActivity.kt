package com.practicum.playlismaker.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlismaker.R
import com.practicum.playlismaker.data.network.NetworkManager
import com.practicum.playlismaker.data.SearchHistory
import com.practicum.playlismaker.ui.player.PlayerActivity
import com.practicum.playlismaker.ui.SearchDebounce
import com.practicum.playlismaker.ui.getPrefs

class SearchActivity : AppCompatActivity() {

    private var searchString = ""
    private var searchEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchHistory = SearchHistory(getPrefs())
        val tracksAdapter = TracksAdapter {
            searchHistory.update(it)
            openPlayer(it.trackId)
        }
        val historyAdapter = TracksAdapter {
            openPlayer(it.trackId)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val errorLayout = findViewById<View>(R.id.search_error)
        val historyClear = findViewById<View>(R.id.search_history_clear)
        val historyTitle = findViewById<View>(R.id.search_history_title)

        fun hideError() {
            errorLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        hideError()
        recyclerView.layoutManager = LinearLayoutManager(this)

        fun showTracks() {
            recyclerView.adapter = tracksAdapter
            historyClear.visibility = View.GONE
            historyTitle.visibility = View.GONE
        }

        fun showHistory() {
            val tracks = searchHistory.getHistory()
            historyAdapter.update(tracks)
            recyclerView.adapter = historyAdapter
            if (tracks.isNotEmpty()) {
                historyClear.visibility = View.VISIBLE
                historyTitle.visibility = View.VISIBLE
            } else {
                historyTitle.visibility = View.GONE
                historyClear.visibility = View.GONE
            }
        }

        showTracks()

        val networkManager = NetworkManager()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setOnClickListener {
            finish()
        }

        searchEditText = findViewById(R.id.search_edit_text)
        val searchClear = findViewById<View>(R.id.search_clear)

        searchEditText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideError()
                historyAdapter.update(searchHistory.getHistory())
                showHistory()
            } else {
                showTracks()
            }
        }

        val errorText = findViewById<TextView>(R.id.search_error_tv)
        val errorImage = findViewById<ImageView>(R.id.search_error_iv)
        val errorRefreshButton = findViewById<Button>(R.id.search_error_btn)
        val progress = findViewById<ProgressBar>(R.id.search_progress)

        fun showProgress() {
            progress.visibility = View.VISIBLE
        }

        fun hideProgress() {
            progress.visibility = View.GONE
        }

        fun showRecycler() {
            recyclerView.visibility = View.VISIBLE
        }

        fun hideRecycler() {
            recyclerView.visibility = View.GONE
        }

        hideProgress()

        fun search() {
            val search = searchEditText?.text?.toString().orEmpty().trim()
            if (search.isEmpty()) return
            showProgress()
            hideRecycler()
            networkManager.getTracks(
                search,
                action = {
                    hideProgress()
                    hideError()
                    showRecycler()
                    tracksAdapter.update(it.results)
                },
                errorAction = {
                    hideProgress()
                    hideRecycler()
                    errorLayout.visibility = View.VISIBLE
                    when (it) {
                        NetworkManager.ErrorType.EMPTY_RESPONCE -> {
                            errorImage.setImageResource(R.drawable.ic_search_error_empty)
                            errorText.setText(R.string.search_error_empty)
                            errorRefreshButton.visibility = View.GONE
                        }

                        NetworkManager.ErrorType.RESPONCE_ERROR -> {
                            errorImage.setImageResource(R.drawable.ic_search_error_network)
                            errorText.setText(R.string.search_error_network)
                            errorRefreshButton.visibility = View.VISIBLE
                        }
                    }
                })
        }
        errorRefreshButton.setOnClickListener {
            hideError()
            search()
        }

        val searchDebounce = SearchDebounce {
            hideError()
            showTracks()
            search()
        }

        searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchString = s.toString()
                if (s.isNullOrEmpty()) {
                    searchClear.visibility = View.GONE
                    hideError()
                    showHistory()
                    searchDebounce.clear()
                } else {
                    searchClear.visibility = View.VISIBLE
                    searchDebounce.run()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        searchEditText?.setOnEditorActionListener { _, actionId, _ ->
            Log.d("search", "action $actionId")
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                showTracks()
                search()
                return@setOnEditorActionListener true
            }
            false
        }
        searchClear.visibility = View.GONE
        searchClear.setOnClickListener {
            searchEditText?.text = null
            tracksAdapter.update(emptyList())
            showHistory()
            hideKeyboard()
            hideError()
        }
        historyClear.setOnClickListener {
            searchHistory.clear()
            showHistory()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_STRING_KEY, searchString)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchString = savedInstanceState.getString(SEARCH_STRING_KEY).orEmpty()
        searchEditText?.setText(searchString)
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun openPlayer(trackId: Long) {
        startActivity(PlayerActivity.newInstance(this, trackId))
    }

    companion object {
        const val SEARCH_STRING_KEY = "SEARCH_STRING_KEY"
    }
}