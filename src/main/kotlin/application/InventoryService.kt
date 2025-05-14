package application

import domain.ProductRepository
import domain.Product
import domain.TemperatureMode
import java.time.LocalDate

class InventoryService(private val inventory: ProductRepository) {
    fun addProduct(
        name: String, quantity: Int, expiry: LocalDate,
        temperatureMode: TemperatureMode,
        minimumStock: Int, optimalStock: Int, criticalLevel: Int
    ) {
        val product = Product(name, quantity, expiry, temperatureMode, minimumStock, optimalStock, criticalLevel)
        inventory.addProduct(product)
    }

    fun getProduct(name: String): Product? = inventory.getProduct(name)

    fun checkStock(name: String, amount: Int): Boolean = inventory.takeInventory(name, amount)

    fun adjustInventory(name: String, newQuantity: Int) {
        inventory.adjustInventory(name, newQuantity)
    }

    fun viewCritical(): List<Product> = inventory.findCriticalStockLevelProducts()

    fun useProduct(name: String, amount: Int) {
        inventory.removeProduct(name, amount)
    }

    fun removeExpired() {
        inventory.removeExpiredProducts()
    }

    fun viewAll(): List<Product> = inventory.getAllProducts()
}
