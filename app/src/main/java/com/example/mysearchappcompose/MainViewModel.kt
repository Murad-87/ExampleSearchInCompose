package com.example.mysearchappcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysearchappcompose.data.Person
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class MainViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _persons = MutableStateFlow(allPersons)
    val persons = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_persons) {text, persons ->
            if (text.isBlank()) {
                persons
            } else {
                delay(2000L)
                persons.filter {
                    it.doesMathSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _persons.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}

private val allPersons = listOf(
    Person(
        firstName = "Jony",
        lastName = "Montana"
    ),

    Person(
        firstName = "Sony",
        lastName = "Pontana"
    ),

    Person(
        firstName = "Kony",
        lastName = "Tontana"
    ),

    Person(
        firstName = "Mony",
        lastName = "Fontana"
    )
)