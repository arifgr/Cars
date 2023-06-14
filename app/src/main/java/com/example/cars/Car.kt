package com.example.cars

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Car(
    val brand: String,
    val model: String,
    val year: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
