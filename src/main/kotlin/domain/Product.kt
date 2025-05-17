package domain

import java.time.LocalDate

data class Product(
    val name: String,
    var quantity: Int,
    val expiryDate: LocalDate,
    val temperatureMode: TemperatureMode,
    val minimumStock: Int,
    val optimalStock: Int,
    val criticalLevel: Int
) {
    fun reduceQuantity(amount: Int) {
        require(amount <= quantity) { "Not enough stock" }
        quantity -= amount
    }

    fun addQuantity(amount: Int) {
        quantity += amount
    }

    fun isExpired(): Boolean = expiryDate.isBefore(LocalDate.now())

    fun isCritical(): Boolean = quantity <= criticalLevel
}
