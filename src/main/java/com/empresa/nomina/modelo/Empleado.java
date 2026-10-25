package main.java.com.empresa.nomina.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public abstract class Empleado {

    private final String identificacion;
    private final String nombre;
    private final int aniosAntiguedad;
    private final BigDecimal salarioBase;

    protected Empleado(
            String identificacion,
            String nombre,
            int aniosAntiguedad,
            BigDecimal salarioBase) {

        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException(
                    "La identificación es obligatoria.");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio.");
        }

        if (aniosAntiguedad < 0) {
            throw new IllegalArgumentException(
                    "La antigüedad no puede ser negativa.");
        }

        this.salarioBase = validarMonto(salarioBase, "salario base");

        if (this.salarioBase.signum() <= 0) {
            throw new IllegalArgumentException(
                    "El salario base debe ser mayor que cero.");
        }

        this.identificacion = identificacion.trim();
        this.nombre = nombre.trim();
        this.aniosAntiguedad = aniosAntiguedad;
    }

    private static BigDecimal validarMonto(
            BigDecimal monto, String campo) {

        Objects.requireNonNull(
                monto, campo + " no puede ser nulo.");

        if (monto.signum() < 0) {
            throw new IllegalArgumentException(
                    campo + " no puede ser negativo.");
        }

        return monto.setScale(2, RoundingMode.HALF_UP);
    }

    public abstract BigDecimal calcularBeneficios();

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAniosAntiguedad() {
        return aniosAntiguedad;
    }

    public BigDecimal getSalarioBase() {
        return salarioBase;
    }
}