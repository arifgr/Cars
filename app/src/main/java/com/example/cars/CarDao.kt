package com.example.cars

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {

    @Upsert
    suspend fun upsertCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)

    @Query("SELECT * FROM car ORDER BY brand ASC")
    fun getCarsOrderedByBrand(): Flow<List<Car>>

    @Query("SELECT * FROM car ORDER BY model ASC")
    fun getCarsOrderedByModel(): Flow<List<Car>>

    @Query("SELECT * FROM car ORDER BY year ASC")
    fun getCarsOrderedByYear(): Flow<List<Car>>
}