package com.example.cars

sealed interface CarEvent {
    object SaveCar : CarEvent
    data class SetBrand(val brand: String) : CarEvent
    data class SetModel(val model: String) : CarEvent
    data class SetYear(val year: String) : CarEvent
    object ShowDialog : CarEvent
    object HideDialog : CarEvent
    data class SortCars(val sortType: SortType) : CarEvent
    data class DeleteCars(val car: Car) : CarEvent
}