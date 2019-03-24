package com.bookitng.bookitng.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bookitng.bookitng.R
import com.bookitng.bookitng.activities.MainMenuActivity
import com.bookitng.bookitng.models.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class TodayMovieAdapter (private val activity: MainMenuActivity,
                         private val moviePayload: List<Movie>) :
    RecyclerView.Adapter<TodayMovieAdapter.TodayMovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayMovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movie_item, parent, false)
        return TodayMovieViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TodayMovieViewHolder, position: Int) {
        viewHolder.setUpMovie(moviePayload[position])
    }

    override fun getItemCount(): Int = moviePayload.size

    inner class TodayMovieViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun setUpMovie(currentMovie: Movie) {

            currentMovie?.let {
                itemView.movieTitleTextView.text = currentMovie.title
                itemView.starringTextView.text = currentMovie.starring
                itemView.synopsisTextView.text = currentMovie.synopsis
            }
        }
    }
}