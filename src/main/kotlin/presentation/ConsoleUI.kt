package presentation

import application.InventoryService
import java.util.*

class ConsoleUI(private val inventoryService: InventoryService) {
    private val scanner = Scanner(System.`in`)

    fun start() {
        var choice: Int
        do {
            showMenu()
            choice = scanner.nextLine().toIntOrNull() ?: -1
            handleMenuChoice(choice)
        } while (choice != 0)
    }

    private fun showMenu() {
        println(
            """
            ===== Система инвентаризации ресторана =====
            1. Пополнить запасы продукта
            2. Посмотреть продукт
            3. Сопоставить запасы
            4. Посмотреть критические продукты
            5. Использовать продукт
            6. Убрать просрочку
            7. Посмотреть все продукты
            0. Выход
            Выберите действие: 
            """.trimIndent()
        )
    }

    private fun handleMenuChoice(choice: Int) {
        when (choice) {
            1 -> replenishProductStock()
            2 -> showProductByName()
            3 -> viewStock()
            4 -> checkCriticalProducts()
            5 -> useProduct()
            6 -> removeExpiredProducts()
            7 -> showAllProducts()
            0 -> println("Выход из программы...")
            else -> println("Неверный выбор. Попробуйте снова.")
        }
    }

    private fun readInt(prompt: String): Int {
        print(prompt)
        return scanner.nextLine().toIntOrNull()?.takeIf { it >= 0 } ?: run {
            println("Введено некорректное значение. Установлено значение 0.")
            0
        }
    }

    fun replenishProductStock() {
        print("Введите название продукта: ")
        val name = scanner.nextLine()
        val amount = readInt("Введите количество продукта: ")

        println("Имитация заказа доставки продукта...\n.\n..\n... Доставка выполнена.")

        runCatching {
            inventoryService.adjustInventory(name, amount)
            println("Количество продукта успешно пополнено")
        }.onFailure {
            println("Ошибка: ${it.message}")
        }
    }

    fun showProductByName() {
        print("Введите название продукта: ")
        val name = scanner.nextLine()
        val product = inventoryService.getProduct(name)

        product?.let { println(it) } ?: println("Продукт не найден")
    }

    fun viewStock() {
        print("Введите название продукта: ")
        val name = scanner.nextLine()
        val amount = readInt("Введите ожидаемое количество продукта: ")

        runCatching {
            val matches = inventoryService.checkStock(name, amount)
            println("Совпадает ли: $matches")

            if (!matches) {
                val product = inventoryService.getProduct(name)
                product?.let {
                    inventoryService.adjustInventory(name, amount - it.quantity)
                    println("Запасы скорректированы")
                }
            }
        }.onFailure {
            println("Ошибка: ${it.message}")
        }
    }

    fun checkCriticalProducts() {
        val critical = inventoryService.viewCritical()
        if (critical.isEmpty()) {
            println("Критических продуктов нет.")
        } else {
            println("Продукты с критическим количеством:")
            critical.forEach { println(it) }
        }
    }

    fun useProduct() {
        print("Введите название продукта: ")
        val name = scanner.nextLine()
        val amount = readInt("Введите количество: ")

        runCatching {
            inventoryService.useProduct(name, amount)
            println("Продукт успешно использован.")
        }.onFailure {
            println("Ошибка: ${it.message}")
        }
    }

    fun removeExpiredProducts() {
        runCatching {
            inventoryService.removeExpired()
            println("Просроченные продукты успешно убраны.")
        }.onFailure {
            println("Ошибка: ${it.message}")
        }
    }

    fun showAllProducts() {
        val products = inventoryService.viewAll()
        if (products.isEmpty()) {
            println("Нет продуктов в инвентаре.")
        } else {
            println("=== Все продукты ===")
            products.forEach { println(it) }
        }
    }
}
