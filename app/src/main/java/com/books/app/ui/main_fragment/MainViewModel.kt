package com.books.app.ui.main_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.books.app.firebase.FirebaseRemoteConfigRepository
import com.books.app.firebase.model.Book
import com.books.app.firebase.model.ConfigResponse
import com.books.app.firebase.model.TopBannerSlide
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val firebaseRepository: FirebaseRemoteConfigRepository) :
    ViewModel() {

    val sliderData = MutableLiveData<List<TopBannerSlide>>()
    val booksData = MutableLiveData<List<Book>>()
    val youWillLikeData = MutableLiveData<List<Int>>()

    val youWillLikeBooksList = MutableLiveData<List<Book>>()

    val selectedBook = MutableLiveData<Book>()

    init {
        parseResponseObject()
    }

    private fun parseResponseObject() {
        val configResponse = firebaseRepository.getResponseObject()
        if (configResponse != null) {
            sliderData.postValue(configResponse.top_banner_slides)
            booksData.postValue(configResponse.books)
            youWillLikeData.postValue(configResponse.you_will_like_section)
        }
    }

    fun setLikeBooks() {
        val resList = mutableListOf<Book>()
        if (youWillLikeData.value != null) {
            for (id in youWillLikeData.value!!) {
                booksData.value?.find { it.id == id }?.let { resList.add(it) }
            }
            youWillLikeBooksList.postValue(resList)
        }
    }

    fun getBookById(id: Int) {
        val book = booksData.value?.find { it.id == id }
        if (book != null) {
            selectedBook.postValue(book!!)
        }
    }

}