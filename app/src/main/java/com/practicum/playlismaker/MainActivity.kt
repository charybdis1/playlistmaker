package com.practicum.playlismaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val search = findViewById<Button>(R.id.main_search)

        val mediateka = findViewById<Button>(R.id.main_mediateka)

        val settings = findViewById<Button>(R.id.main_settings)


        search.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                // Toast.makeText(this@MainActivity, "Нажата кнопка 1", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }

        })

        mediateka.setOnClickListener {
            // Toast.makeText(this@MainActivity, "Нажата кнопка 2", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@MainActivity, MediatekaActivity::class.java)
            startActivity(intent)
        }

        settings.setOnClickListener {
            // Toast.makeText(this@MainActivity, "Нажата кнопка 3", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}