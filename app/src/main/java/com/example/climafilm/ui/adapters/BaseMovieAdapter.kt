package com.example.climafilm.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.climafilm.databinding.MovieItemRicyclerViewBinding
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.util.Constants

abstract class BaseMovieAdapter(
    private val requestManager: RequestManager
) : RecyclerView.Adapter<BaseMovieAdapter.ViewHolder>() {

    private var movieList: List<Movie> = emptyList()
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = createBinding(inflater, parent)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        bind(holder, movie)
    }

    override fun getItemCount(): Int = movieList.size

    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding

    private fun bind(holder: ViewHolder, movie: Movie){
        val binding = holder.binding as MovieItemRicyclerViewBinding
        requestManager.load(Constants.BASE_IMAGE_URL + movie.poster_path).into(binding.imageMovie)
        binding.txtMovieName.text = movie.title
        binding.layoutMovieImage.setOnClickListener { listener?.onItemClick(movie.id) }
    }

    fun setList(list: List<Movie>) {
        movieList = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        requestManager.clear(holder.binding.root)
    }

    interface OnItemClickListener {
        fun onItemClick(movieId: Int)
    }
}