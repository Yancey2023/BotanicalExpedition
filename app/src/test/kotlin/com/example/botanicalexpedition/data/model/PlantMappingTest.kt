package com.example.botanicalexpedition.data.model

import com.example.botanicalexpedition.data.api.PlantResponse
import com.example.botanicalexpedition.data.local.PlantEntity
import com.example.botanicalexpedition.data.local.toEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class PlantMappingTest {

    @Test
    fun testPlantEntityToPlantMapping() {
        val entity = PlantEntity(
            id = 1,
            name = "Sunflower",
            description = "A tall yellow flower",
            imageUrl = "img.jpg",
            category = "Flower",
            createdAt = 12345L
        )
        val plant = entity.toPlant()

        assertEquals(1, plant.id)
        assertEquals("Sunflower", plant.name)
        assertEquals("A tall yellow flower", plant.description)
        assertEquals("img.jpg", plant.imageUrl)
        assertEquals("Flower", plant.category)
        assertEquals(12345L, plant.createdAt)
    }

    @Test
    fun testPlantResponseToEntityMapping() {
        val response = PlantResponse(id = 42, title = "Fern", body = "A leafy green plant")
        val entity = response.toEntity()

        assertEquals(42, entity.id)
        assertEquals("Fern", entity.name)
        assertEquals("A leafy green plant", entity.description)
        assertEquals("General", entity.category)
    }
}
