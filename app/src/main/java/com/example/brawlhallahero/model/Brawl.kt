package com.example.brawlhallahero.model

data class Brawl(
    val id: Long,
    val image: Int,
    val name: String,
    val deskripsi: String,
    val isFav: Boolean = false,
)