package edu.rosehulman.cuiy1.moviequotes

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.rowview.view.*

class MovieQuoteViewHolder(itemView: View, val adapter: MovieQuoteAdapter, val context: Context) : RecyclerView.ViewHolder(itemView) {
    //two ways to capture views from xml
    //1. Java way
    private val quoteTextView = itemView.findViewById<TextView>(R.id.quote_text_view)
    private val movieTextView = itemView.findViewById<TextView>(R.id.movie_text_view)
    //2. Kotlin way: use the id directly
    private val cardView = itemView.card_view

    init {
        itemView.setOnClickListener {
            // launch a dialog
            adapter.showAddEditDialog(adapterPosition) // love it!!!
        }

        itemView.setOnLongClickListener {

            adapter.setSelected(adapterPosition)
            true
        }
    }

    fun bind(movieQuote: MovieQuote){
        quoteTextView.text = movieQuote.quote
        movieTextView.text = movieQuote.movie

        if (movieQuote.isSelected){
            //set card color to the accent color
            val color = ContextCompat.getColor(context,R.color.colorAccent)
            cardView.setCardBackgroundColor(color)
        }else{
            // set the color to white
            cardView.setCardBackgroundColor(Color.WHITE)
        }
    }
}