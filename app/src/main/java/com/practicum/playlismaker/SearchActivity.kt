package com.practicum.playlismaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class SearchActivity : AppCompatActivity() {

    private var searchString = ""
    private var searchEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setOnClickListener {
            finish()
        }

        searchEditText = findViewById<EditText>(R.id.search_edit_text)
        val searchClear = findViewById<View>(R.id.search_clear)
        searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchString = s.toString()
                if (s.isNullOrEmpty()) searchClear.visibility = View.GONE
                else searchClear.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        searchClear.visibility = View.GONE
        searchClear.setOnClickListener {
            searchEditText?.text = null
            hideKeyboard()
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

    companion object {
        const val SEARCH_STRING_KEY = "SEARCH_STRING_KEY"
    }
}