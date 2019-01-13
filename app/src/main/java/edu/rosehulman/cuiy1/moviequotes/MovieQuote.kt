package edu.rosehulman.cuiy1.moviequotes

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ServerTimestamp


//Primary constructor arguments become public instance variables of the same name.

data class MovieQuote(var quote: String = " ", var movie: String = " ", var isSelected: Boolean = false){

    @ServerTimestamp var timestamp : Timestamp? = null
    @get:Exclude var id = ""


    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot) : MovieQuote{
            val movieQuote = snapshot.toObject(MovieQuote::class.java)!!
            //TODO
            movieQuote.id = snapshot.id
            return movieQuote
        }
    }
}