package main.java.com.empresa.nomina.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EmpleadoAsalariado extends Empleado {

    private static final int ANTIGUEDAD_MINIMA = 5;

    private static final BigDecimal PORCENTAJE_BONO =
            new BigDecimal("0.10");

    public EmpleadoAsalariado(
            String identificacion,
            String nombre,
            int aniosAntiguedad,
            BigDecimal salarioBase) {

        super(
                identificacion,
                nombre,
                aniosAntiguedad,
                salarioBase
        );
    }

    @Override
    public BigDecimal calcularBeneficios() {
        if (getAniosAntiguedad() <= ANTIGUEDAD_MINIMA) {
            return BigDecimal.ZERO.setScale(2);
        }

        return getSalarioBase()
                .multiply(PORCENTAJE_BONO)
                .setScale(2, RoundingMode.HALF_UP);
    }
}