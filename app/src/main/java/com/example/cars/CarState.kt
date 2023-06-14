package com.example.cars

data class CarState(
    val cars: List<Car> = emptyList(),
    val brand: String = "",
    val model: String = "",
    val year: String = "",
    val isAddingCar: Boolean = false,
    val sortType: SortType = SortType.BRAND
)
