package com.books.app.ui.details_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.books.app.databinding.BookItemBinding
import com.books.app.databinding.DetailsSliderItemBinding
import com.books.app.firebase.model.Book
import com.books.app.ui.main_fragment.SubAdapter
import com.squareup.picasso.Picasso

class SliderAdapter(private var bookList: List<Book>) :
    RecyclerView.Adapter<SliderAdapter.CarouselViewHolder>() {

    private val placeholderImageUrl =
        "http://www.ll-mm.com/images/placeholders/image-placeholder.jpg"

    inner class CarouselViewHolder(val binding: DetailsSliderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.detailsSliderImage
        val title = binding.detailsSliderTitle
        val author = binding.detailsSliderAuthor
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding =
            DetailsSliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun getItemCount(): Int = bookList.size

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        checkForUrlToImage(bookList[position], holder)
        holder.title.text = bookList[position].name
        holder.author.text = bookList[position].author
    }

    private fun checkForUrlToImage(book: Book, bookViewHolder: SliderAdapter.CarouselViewHolder) {
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