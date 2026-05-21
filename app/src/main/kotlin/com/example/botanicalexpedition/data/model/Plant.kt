package com.example.botanicalexpedition.data.model

data class Plant(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val category: String,
    val createdAt: Long
)
