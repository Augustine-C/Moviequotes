package edu.rosehulman.cuiy1.moviequotes

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.add_dialog.view.*

class MovieQuoteAdapter(val context : Context): RecyclerView.Adapter<MovieQuoteViewHolder>() {
    // An adapter manages its own collection
    private val movieQuotes = ArrayList<MovieQuote>()
    private val movieQuotesRef = FirebaseFirestore
        .getInstance()
        .collection(Constants.QUOTES_COLLECTION)

    fun addSnapshotListener() {
        movieQuotesRef
            .orderBy("timestamp",Query.Direction.DESCENDING)
            .addSnapshotListener{snapshot : QuerySnapshot?, firebaseFirestoreException ->
            if(firebaseFirestoreException != null) {
                Log.d(Constants.TAG, "Firebase error: $firebaseFirestoreException")
                return@addSnapshotListener
            }

            populateLocalQuotes(snapshot!!)
        }
    }

    private fun populateLocalQuotes(snapshot : QuerySnapshot){
        movieQuotes.clear()
        for (document in snapshot.documents){
            movieQuotes.add(MovieQuote.fromSnapshot(document))
        }
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = movieQuotes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieQuoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rowview,parent, false)
        return MovieQuoteViewHolder(view,this,context)
    }


    override fun onBindViewHolder(holder: MovieQuoteViewHolder, position: Int) = holder.bind(movieQuotes[position])

    fun add(movieQuote: MovieQuote){
        movieQuotesRef.add(movieQuote)
//        movieQuotes.add(0,movieQuote)
//        notifyItemChanged(0)
    }

    fun showAddEditDialog(position: Int = -1){
        val builder = AlertDialog.Builder(context)
        // Configure builder: title, icon, message/custom view/list, buttons(pos, neg, neutral)
        builder.setTitle(R.string.add_dialog_title)

        val view = LayoutInflater.from(context).inflate(R.layout.add_dialog,null,false)
        builder.setView(view)
        //TODO: If editing, pre-populate the edit texts (like Jersey)

        if(position >= 0){
            //edit
            view.add_dialog_quote_quit_text.setText(movieQuotes[position].quote)
            view.add_dialog_movie_edit_text.setText(movieQuotes[position].movie)
        }

        builder.setPositiveButton(android.R.string.ok, {_, _ ->
            val quote = view.add_dialog_quote_quit_text.text.toString()
            val movie = view.add_dialog_movie_edit_text.text.toString()
            if(position == 0){
                edit(position, quote, movie)
            } else {
                add(MovieQuote(quote, movie))
            }
        })

        builder.setNegativeButton(android.R.string.cancel, null)

        builder.setNeutralButton("Remove"){_, _ ->
            remove(position)
        }

        builder.create().show()
    }

    private fun remove(position: Int){
//        movieQuotes.removeAt(position)
//        notifyItemRemoved(position)
        movieQuotesRef.document(movieQuotes[position].id).delete()
    }

    fun edit (position: Int,quote: String,movie: String){
        movieQuotes[position].movie = movie
        movieQuotes[position].quote = quote
        notifyItemChanged(position)
    }

    fun setSelected(position: Int){
        movieQuotes[position].isSelected = !movieQuotes[position].isSelected
        notifyItemChanged(position)
    }


}