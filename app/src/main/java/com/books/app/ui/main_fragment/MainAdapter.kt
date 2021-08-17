package com.books.app.ui.main_fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.books.app.databinding.RowItemBinding
import com.books.app.firebase.model.BookWithGenre

class MainAdapter(val context: Context,private var bookGenresList: List<BookWithGenre>) : RecyclerView.Adapter<MainAdapter.BookRowViewHolder>() {

    inner class BookRowViewHolder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val genre = binding.rowGenreTextview
        val recycler = binding.rowRecycler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookRowViewHolder {
        val binding = RowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BookRowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookRowViewHolder, position: Int) {
        holder.genre.text = bookGenresList[position].genre
        holder.recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.recycler.adapter = SubAdapter(bookGenresList[position].books,1)
    }

    override fun getItemCount(): Int = bookGenresList.size

}