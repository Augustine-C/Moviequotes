package edu.rosehulman.cuiy1.moviequotes

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MovieQuoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        adapter = MovieQuoteAdapter(this)
        adapter.addSnapshotListener()
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter

        fab.setOnClickListener { view ->
            Log.d(Constants.TAG, "The floating action button was pressed")
            //updateQuote(MovieQuote("I am your father", "The Empire Strikes Back"))
            adapter.showAddEditDialog()
        }
    }



    private fun showClearDialog(){
        val builder = AlertDialog.Builder(this)

        builder.setTitle(R.string.clear_title)
        builder.setMessage(R.string.clear_message)
        builder.setPositiveButton(android.R.string.ok, {_, _ ->
            reset()
        })

        builder.setNegativeButton(android.R.string.cancel, null)
        builder.create().show()

    }

    private  fun updateQuote(movieQuote: MovieQuote){
//        quote_text_view.text = movieQuote.quote
//        movie_text_view.text = movieQuote.movieQuote

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                getWhichSettings()
                true
            }

            R.id.action_clear -> {
                showClearDialog()
                true
            }

            R.id.action_increase_font_size ->{
                changeFontSize(4)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun changeFontSize(delta: Int){
//        var currentSize = movie_text_view.textSize / resources.displayMetrics.scaledDensity
//        currentSize += delta
//        movie_text_view.textSize = currentSize
//        quote_text_view.textSize = currentSize
    }

    fun reset(){
//        movie_text_view.text = getString(R.string.movie)
//        quote_text_view.text = getString(R.string.quote)
    }

    private fun getWhichSettings(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.which_setting_title))
        builder.setItems(R.array.settings_options,{_, index ->
            val settingsType = when (index) {
                0 -> Settings.ACTION_SOUND_SETTINGS
                1 -> Settings.ACTION_BATTERY_SAVER_SETTINGS
                else->Settings.ACTION_SETTINGS
            }
            startActivity(Intent(settingsType))

        })
        builder.create().show()
    }



}
