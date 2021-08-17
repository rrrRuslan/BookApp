package com.books.app.ui.main_fragment

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import com.books.app.R
import com.books.app.databinding.BookItemBinding
import com.books.app.firebase.model.Book
import com.squareup.picasso.Picasso

//сорян за костиль з інтом в конструкторі, часу не хватило зробити по людськи
class SubAdapter(private var bookList: List<Book>,private var color: Int) :
    RecyclerView.Adapter<SubAdapter.BookViewHolder>() {

    private val placeholderImageUrl =
        "http://www.ll-mm.com/images/placeholders/image-placeholder.jpg"

    inner class BookViewHolder(val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.cardviewImage
        val title = binding.cardviewTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        setPropertiesForBookViewHolder(holder, bookList[position])
        holder.binding.root.setOnClickListener {
            Log.i("CARDVIEW LISTENER", "book clicked: ${bookList[position]}")
            Navigation.findNavController(it)
                .navigate(
                    R.id.action_mainFragment_to_detailsFragment,
                    bundleOf("selected_book_id" to bookList[position].id),
                    navOptions {
                        anim {
                            enter = android.R.animator.fade_in
                            exit = android.R.animator.fade_out
                            popEnter = android.R.animator.fade_in
                            popExit = android.R.animator.fade_out
                        }
                    }
                )
        }
    }



    override fun getItemCount(): Int = bookList.size

    private fun setPropertiesForBookViewHolder(
        bookViewHolder: BookViewHolder,
        book: Book
    ) {
        checkForUrlToImage(book, bookViewHolder)
        if (color==2){
            bookViewHolder.title.setTextColor(Color.parseColor("#000000"))
        }
        bookViewHolder.title.text = book.name
    }

    private fun checkForUrlToImage(book: Book, bookViewHolder: BookViewHolder) {
        if (book.cover_url == null || book.cover_url.isEmpty()) {
            Picasso.get()
                .load(placeholderImageUrl)
                .centerCrop()
                .fit()
                .into(bookViewHolder.image)
        } else {
            Picasso.get()
                .load(book.cover_url)
                .centerCrop()
                .fit()
                .into(bookViewHolder.image)
        }
    }


}