package by.pavel;

public class LogisticsService {
    public double calculateDeliveryTime(double distance, double speed, String terrainType) {
        if (distance <= 0) throw new IllegalArgumentException("Расстояние должно быть > 0");
        if (speed < 1 || speed > 150) throw new IllegalArgumentException("Скорость вне диапазона [1-150]");

        boolean isCity = "город".equalsIgnoreCase(terrainType);
        if (isCity && speed > 60) throw new IllegalArgumentException("В городе нельзя быстрее 60 км/ч");

        double time = distance / speed;
        if (isCity) time *= 1.2;

        return Math.round(time * 100.0) / 100.0;
    }
}