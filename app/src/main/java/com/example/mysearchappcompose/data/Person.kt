package com.example.mysearchappcompose.data

data class Person(
    val firstName: String,
    val lastName: String
) {
    fun doesMathSearchQuery(query : String) : Boolean {
        val matchingCombinations = listOf(
            "$firstName$lastName",
            "$firstName $lastName",
            "${firstName.first()} ${lastName.first()}"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
