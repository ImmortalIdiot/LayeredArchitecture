package domain

interface ProductRepository {
    fun addProduct(product: Product)
    fun getProduct(name: String): Product?
    fun getAllProducts(): List<Product>
    fun removeProduct(name: String, amount: Int)
    fun removeExpiredProducts()
    fun adjustInventory(name: String, amount: Int)
    fun takeInventory(name: String, amount: Int): Boolean
    fun findCriticalStockLevelProducts(): List<Product>
}
