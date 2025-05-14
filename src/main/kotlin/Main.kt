import application.InventoryService
import domain.TemperatureMode
import infrastructure.ProductRepositoryImpl
import presentation.ConsoleUI
import java.time.LocalDate

fun main() {
    val repo = ProductRepositoryImpl()
    val service = InventoryService(repo)

    service.addProduct("Творог", 50, LocalDate.now().plusDays(7), TemperatureMode.Chilled, 20, 60, 10)
    service.addProduct("Молоко", 200, LocalDate.now().plusDays(14), TemperatureMode.Normal, 100, 300, 50)
    service.addProduct("Яйца", 30, LocalDate.now().plusDays(3), TemperatureMode.Frozen, 20, 50, 10)
    service.addProduct("Хлеб", 11, LocalDate.now().plusDays(10), TemperatureMode.Chilled, 20, 60, 15)
    service.addProduct("Пиво", 100, LocalDate.now().plusDays(20), TemperatureMode.Normal, 50, 150, 30)
    service.addProduct("Стейк из индейки", 80, LocalDate.now().minusDays(25), TemperatureMode.Normal, 40, 120, 25)
    service.addProduct("Рёбра Jack Daniels", 4, LocalDate.now().minusDays(60), TemperatureMode.Frozen, 10, 40, 5)

    val ui = ConsoleUI(service)
    ui.start()
}
