
package main.java.com.empresa.nomina.servicio;

import main.java.com.empresa.nomina.modelo.Empleado;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class NominaService {

    private static final BigDecimal PORCENTAJE_DEDUCCIONES =
            new BigDecimal("0.08");

    public BigDecimal calcularDeducciones(Empleado empleado) {
        Objects.requireNonNull(
                empleado, "El empleado es obligatorio.");

        return empleado.getSalarioBase()
                .multiply(PORCENTAJE_DEDUCCIONES)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularSalarioNeto(Empleado empleado) {
        Objects.requireNonNull(
                empleado, "El empleado es obligatorio.");

        return empleado.getSalarioBase()
                .add(empleado.calcularBeneficios())
                .subtract(calcularDeducciones(empleado))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
