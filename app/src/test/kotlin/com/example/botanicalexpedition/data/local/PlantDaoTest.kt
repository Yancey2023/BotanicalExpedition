package com.example.botanicalexpedition.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class PlantDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: PlantDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.plantDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testInsertAndQuery() = runTest {
        val entity = PlantEntity(1, "Rose", "A red rose", "", "Flower", 1000L)
        dao.insert(entity)
        val result = dao.getPlantById(1)
        assertEquals("Rose", result?.name)
    }

    @Test
    fun testDelete() = runTest {
        val entity = PlantEntity(1, "Rose", "A red rose", "", "Flower", 1000L)
        dao.insert(entity)
        dao.delete(entity)
        val result = dao.getPlantById(1)
        assertNull(result)
    }

    @Test
    fun testInsertAllAndQuery() = runTest {
        val plants = listOf(
            PlantEntity(1, "Rose", "Red flower", "", "Flower", 1000L),
            PlantEntity(2, "Fern", "Green plant", "", "Fern", 2000L)
        )
        dao.insertAll(plants)
        val all = dao.getAllPlants()
        assertEquals(2, all.size)
    }
}
