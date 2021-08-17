package com.books.app.firebase.model

data class ConfigResponse(
    val books: List<Book>,
    val top_banner_slides: List<TopBannerSlide>,
    val you_will_like_section: List<Int>
)