package com.practicum.playlismaker.search.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlismaker.R
import com.practicum.playlismaker.databinding.ActivitySearchBinding
import com.practicum.playlismaker.util.SearchDebounce
import com.practicum.playlismaker.player.ui.PlayerActivity
import com.practicum.playlismaker.search.domain.models.Track
import com.practicum.playlismaker.search.presentation.SearchState
import com.practicum.playlismaker.search.presentation.SearchState.Content.Type
import com.practicum.playlismaker.search.presentation.SearchViewModel
import com.practicum.playlismaker.search.presentation.ViewModelFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(applicationContext))[SearchViewModel::class.java]
    }

    private var searchString = ""
    private val historyAdapter = TracksAdapter {
        openPlayer(it.trackId)
    }
    private val tracksAdapter = TracksAdapter {
        viewModel.updateHistory(it)
        openPlayer(it.trackId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resetUi()
        binding.searchClear.visibility = View.GONE

        viewModel.observeState().observe(this) {
            resetUi()
            when (it) {
                is SearchState.Content -> {
                    when (it.type) {
                        Type.SEARCH -> showTracks(it.tracks)
                        Type.HISTORY -> showHistory(it.tracks)
                    }
                }

                is SearchState.Empty -> binding.run {
                    searchError.visibility = View.VISIBLE
                    searchErrorIv.setImageResource(R.drawable.ic_search_error_empty)
                    searchErrorTv.setText(R.string.search_error_empty)
                    searchErrorBtn.visibility = View.GONE
                }

                is SearchState.Error -> binding.run {
                    searchError.visibility = View.VISIBLE
                    searchErrorIv.setImageResource(R.drawable.ic_search_error_network)
                    searchErrorTv.setText(R.string.search_error_network)
                    searchErrorBtn.visibility = View.VISIBLE
                }

                SearchState.Loading -> {
                    showProgress()
                }
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.toolbar.setOnClickListener {
            finish()
        }

        binding.searchEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                viewModel.getHistory()
            }
        }

        binding.searchErrorBtn.setOnClickListener {
            search()
        }

        val searchDebounce = SearchDebounce {
            search()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchString = s.toString()
                if (s.isNullOrEmpty()) {
                    binding.searchClear.visibility = View.GONE
                    searchDebounce.clear()
                    viewModel.getHistory()
                } else {
                    binding.searchClear.visibility = View.VISIBLE
                    searchDebounce.run()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            Log.d("search", "action $actionId")
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                return@setOnEditorActionListener true
            }
            false
        }
        binding.searchClear.setOnClickListener {
            binding.searchEditText.text = null
            viewModel.getHistory()
            hideKeyboard()
        }
        binding.searchHistoryClear.setOnClickListener {
            viewModel.clearHistory()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_STRING_KEY, searchString)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchString = savedInstanceState.getString(SEARCH_STRING_KEY).orEmpty()
        binding.searchEditText.setText(searchString)
    }

    private fun search() {
        val search = binding.searchEditText.text?.toString().orEmpty().trim()
        if (search.isEmpty()) return
        viewModel.search(search)
    }

    private fun resetUi() {
        hideError()
        hideProgress()
        binding.hideHistoryUi()
    }

    private fun showTracks(tracks: List<Track>) {
        tracksAdapter.update(tracks)
        binding.run {
            recyclerView.adapter = tracksAdapter
            hideHistoryUi()
        }
    }

    private fun showHistory(tracks: List<Track>) {
        historyAdapter.update(tracks)
        binding.run {
            recyclerView.adapter = historyAdapter
            if (tracks.isNotEmpty()) {
                showHistoryUi()
            } else {
                hideHistoryUi()
            }
        }
    }

    private fun ActivitySearchBinding.showHistoryUi() {
        searchHistoryClear.visibility = View.VISIBLE
        searchHistoryTitle.visibility = View.VISIBLE
    }

    private fun ActivitySearchBinding.hideHistoryUi() {
        searchHistoryTitle.visibility = View.GONE
        searchHistoryClear.visibility = View.GONE
    }

    private fun hideError() {
        binding.run {
            searchError.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun showProgress() {
        binding.recyclerView.visibility = View.GONE
        binding.searchProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.searchProgress.visibility = View.GONE
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