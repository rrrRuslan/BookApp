package com.books.app.firebase.model

data class BookWithGenre(
    val genre: String,
    val books: List<Book>
)
