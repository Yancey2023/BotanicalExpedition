package com.example.botanicalexpedition.data.repository

import com.example.botanicalexpedition.data.api.PlantApiService
import com.example.botanicalexpedition.data.local.PlantDao
import com.example.botanicalexpedition.data.local.toEntity
import com.example.botanicalexpedition.data.model.Plant

class PlantRepository(
    private val apiService: PlantApiService,
    private val plantDao: PlantDao
) {
    suspend fun getPlants(): List<Plant> {
        val cached = plantDao.getAllPlants()
        if (cached.isNotEmpty()) {
            return cached.map { it.toPlant() }
        }
        return try {
            val response = apiService.getPlants()
            val entities = response.map { it.toEntity() }
            plantDao.insertAll(entities)
            entities.map { it.toPlant() }
        } catch (e: Exception) {
            cached.map { it.toPlant() }
        }
    }

    suspend fun getPlantById(id: Int): Plant? {
        val cached = plantDao.getPlantById(id)
        if (cached != null) {
            return cached.toPlant()
        }
        return try {
            val response = apiService.getPlant(id)
            val entity = response.toEntity()
            plantDao.insert(entity)
            entity.toPlant()
        } catch (e: Exception) {
            cached?.toPlant()
        }
    }

    suspend fun refreshPlants(): List<Plant> {
        val response = apiService.getPlants()
        val entities = response.map { it.toEntity() }
        plantDao.deleteAll()
        plantDao.insertAll(entities)
        return entities.map { it.toPlant() }
    }
}
