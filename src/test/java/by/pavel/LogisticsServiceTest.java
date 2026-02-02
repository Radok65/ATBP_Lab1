package by.pavel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class LogisticsServiceTest {
    private final LogisticsService service = new LogisticsService();

    @Test
    @DisplayName("Проверка расчета: 120км / 60км/ч на трассе = 2ч")
    void testHighway() {
        assertEquals(2.0, service.calculateDeliveryTime(120, 60, "трасса"));
    }

    @Test
    @DisplayName("Проверка коэффициента города (+20%)")
    void testCity() {
        assertEquals(1.2, service.calculateDeliveryTime(60, 60, "город"));
    }

    @Test
    @DisplayName("Проверка исключения при нулевой скорости")
    void testZeroSpeed() {
        assertThrows(IllegalArgumentException.class, () ->
                service.calculateDeliveryTime(100, 0, "трасса"));
    }
    @Test
    @DisplayName("Позитивный: Расчет для трассы (100км, 50км/ч = 2ч)")
    void testPositiveHighway() {
        double result = service.calculateDeliveryTime(100, 50, "трасса");
        assertEquals(2.0, result, "Время на трассе рассчитано неверно");
    }

    @Test
    @DisplayName("Позитивный: Расчет для города (60км, 60км/ч + 20% = 1.2ч)")
    void testPositiveCity() {
        double result = service.calculateDeliveryTime(60, 60, "город");
        assertEquals(1.2, result, "Городской коэффициент применен неверно");
    }

    @Test
    @DisplayName("Негативный: Отрицательное расстояние")
    void testNegativeDistance() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.calculateDeliveryTime(-50, 40, "трасса"));
        assertTrue(exception.getMessage().contains("Расстояние"));
    }

    @Test
    @DisplayName("Негативный: Скорость выше максимально допустимой (151 км/ч)")
    void testNegativeMaxSpeed() {
        assertThrows(IllegalArgumentException.class, () ->
                service.calculateDeliveryTime(100, 151, "трасса"));
    }

    @Test
    @DisplayName("Негативный: Превышение скорости в городе (> 60 км/ч)")
    void testNegativeCitySpeed() {
        assertThrows(IllegalArgumentException.class, () ->
                service.calculateDeliveryTime(10, 65, "город"));
    }

    @Test
    @DisplayName("Граница: Минимально допустимая скорость (1 км/ч)")
    void testBoundaryMinSpeed() {
        assertEquals(10.0, service.calculateDeliveryTime(10, 1, "трасса"));
    }

    @Test
    @DisplayName("Граница: Максимально допустимая скорость (150 км/ч)")
    void testBoundaryMaxSpeed() {
        assertEquals(2.0, service.calculateDeliveryTime(300, 150, "трасса"));
    }

    @Test
    @DisplayName("Граница: Скорость ровно 60 км/ч в городе (предел)")
    void testBoundaryCityLimit() {
        assertDoesNotThrow(() -> service.calculateDeliveryTime(60, 60, "город"));
    }

    @Test
    @DisplayName("Граница: Обработка нулевой скорости (деление на ноль)")
    void testBoundaryZeroSpeed() {
        assertThrows(IllegalArgumentException.class, () ->
                        service.calculateDeliveryTime(100, 0, "трасса"),
                "Функция должна запрещать нулевую скорость во избежание деления на 0");
    }

    @Test
    @DisplayName("Граница: Пустая строка в типе местности")
    void testBoundaryEmptyTerrain() {
        assertEquals(2.0, service.calculateDeliveryTime(100, 50, ""));
    }

    @Test
    @DisplayName("Граница: Очень маленькое положительное расстояние")
    void testBoundarySmallDistance() {
        assertEquals(0.01, service.calculateDeliveryTime(0.1, 10, "трасса"));
    }
}