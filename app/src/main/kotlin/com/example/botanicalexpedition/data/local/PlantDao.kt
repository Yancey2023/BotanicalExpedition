package com.example.botanicalexpedition.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlantDao {
    @Query("SELECT * FROM plants ORDER BY createdAt DESC")
    suspend fun getAllPlants(): List<PlantEntity>

    @Query("SELECT * FROM plants WHERE id = :id")
    suspend fun getPlantById(id: Int): PlantEntity?

    @Query("SELECT * FROM plants WHERE category = :category ORDER BY createdAt DESC")
    suspend fun getPlantsByCategory(category: String): List<PlantEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<PlantEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plant: PlantEntity)

    @Delete
    suspend fun delete(plant: PlantEntity)

    @Query("DELETE FROM plants")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM plants")
    suspend fun getCount(): Int
}
