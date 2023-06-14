package com.example.cars

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Car::class],
    version = 1
)
abstract class CarDataBase : RoomDatabase() {

    abstract val dao: CarDao
}