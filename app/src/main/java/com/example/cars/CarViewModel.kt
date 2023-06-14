package com.example.cars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class CarViewModel(
    private val dao: CarDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.BRAND)
    private val _state = MutableStateFlow(CarState())
    private val _cars = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.BRAND -> dao.getCarsOrderedByBrand()
            SortType.MODEL -> dao.getCarsOrderedByModel()
            SortType.YEAR -> dao.getCarsOrderedByYear()
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _sortType, _cars) { state, sortType, cars ->
        state.copy(
            cars = cars,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CarState())

    fun onEvent(event: CarEvent) {
        when (event) {
            is CarEvent.DeleteCars -> {
                viewModelScope.launch {
                    dao.deleteCar(event.car)
                }
            }
            CarEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingCar = false
                    )
                }
            }
            CarEvent.SaveCar -> {
                val brand = state.value.brand
                val model = state.value.model
                val year = state.value.year

                if (brand.isBlank() || model.isBlank()) {
                    return
                }

                val car = Car(
                    brand = brand,
                    model = model,
                    year = year
                )
                viewModelScope.launch {
                    dao.upsertCar(car)
                }
                _state.update {
                    it.copy(
                        isAddingCar = false,
                        brand = "",
                        model = "",
                        year = ""
                    )
                }
            }
            is CarEvent.SetBrand -> {
                _state.update {
                    it.copy(
                        brand = event.brand
                    )
                }
            }
            is CarEvent.SetModel -> {
                _state.update {
                    it.copy(
                        model = event.model
                    )
                }
            }
            is CarEvent.SetYear -> {
                _state.update {
                    it.copy(
                        year = event.year
                    )
                }
            }
            CarEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingCar = true
                    )
                }
            }
            is CarEvent.SortCars -> {
                _sortType.value = event.sortType
            }
        }
    }
}