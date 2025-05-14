package infrastructure

import domain.ProductRepository
import domain.Product

class ProductRepositoryImpl : ProductRepository {
    private val products = mutableMapOf<String, Product>()

    override fun addProduct(product: Product) {
        products[product.name] = product
    }

    override fun getProduct(name: String): Product? = products[name]

    override fun takeInventory(name: String, amount: Int): Boolean {
        val product = products[name] ?: throw IllegalArgumentException("Такого продукта нет.")
        return product.quantity == amount
    }

    override fun adjustInventory(name: String, amount: Int) {
        val product = products[name] ?: throw IllegalArgumentException("Такого продукта нет.")
        product.addQuantity(amount)
    }

    override fun findCriticalStockLevelProducts(): List<Product> =
        products.values.filter { it.isCritical() }

    override fun removeProduct(name: String, amount: Int) {
        val product = products[name] ?: throw IllegalArgumentException("Такого продукта нет.")
        product.reduceQuantity(amount)
    }

    override fun removeExpiredProducts() {
        products.entries.removeIf { it.value.isExpired() }
    }

    override fun getAllProducts(): List<Product> = products.values.toList()
}
