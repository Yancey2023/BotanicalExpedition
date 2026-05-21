package com.example.botanicalexpedition.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.botanicalexpedition.data.api.PlantResponse
import com.example.botanicalexpedition.data.model.Plant

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val category: String,
    val createdAt: Long
) {
    fun toPlant(): Plant {
        return Plant(
            id = id,
            name = name,
            description = description,
            imageUrl = imageUrl,
            category = category,
            createdAt = createdAt
        )
    }
}

fun PlantResponse.toEntity(): PlantEntity {
    return PlantEntity(
        id = id,
        name = title,
        description = body,
        imageUrl = "",
        category = "General",
        createdAt = System.currentTimeMillis()
    )
}
