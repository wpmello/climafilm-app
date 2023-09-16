package com.example.climafilm.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseMovieAdapter<T> : RecyclerView.Adapter<BaseMovieAdapter<T>.ViewHolder>() {

    protected var movieList: List<T> = emptyList()

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

    abstract fun bind(holder: ViewHolder, movie: T)

    fun setList(list: List<T>) {
        movieList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)
}