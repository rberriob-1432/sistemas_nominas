package test.java.com.empresa.nomina.modelo;
import main.java.com.empresa.nomina.modelo.EmpleadoAsalariado;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoAsalariadoTest {

    private EmpleadoAsalariado crearEmpleado(
            int antiguedad, String salario) {

        return new EmpleadoAsalariado(
                "123",
                "Carlos",
                antiguedad,
                new BigDecimal(salario)
        );
    }

    @Test
    void debeCalcularBonoCuandoAntiguedadEsMayorQueCinco() {
        EmpleadoAsalariado empleado = crearEmpleado(6, "3000000");

        assertEquals(
                new BigDecimal("300000.00"),
                empleado.calcularBeneficios()
        );
    }

    @Test
    void noDebeCalcularBonoCuandoAntiguedadEsCinco() {
        EmpleadoAsalariado empleado = crearEmpleado(5, "3000000");

        assertEquals(
                new BigDecimal("0.00"),
                empleado.calcularBeneficios()
        );
    }

    @Test
    void noDebeCalcularBonoCuandoAntiguedadEsMenorQueCinco() {
        EmpleadoAsalariado empleado = crearEmpleado(3, "3000000");

        assertEquals(
                new BigDecimal("0.00"),
                empleado.calcularBeneficios()
        );
    }

    @Test
    void debeRechazarIdentificacionVacia() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new EmpleadoAsalariado(
                        " ", "Carlos", 6, new BigDecimal("3000000")
                )
        );
    }

    @Test
    void debeRechazarNombreVacio() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new EmpleadoAsalariado(
                        "123", " ", 6, new BigDecimal("3000000")
                )
        );
    }

    @Test
    void debeRechazarAntiguedadNegativa() {
        assertThrows(
                IllegalArgumentException.class,
                () -> crearEmpleado(-1, "3000000")
        );
    }

    @Test
    void debeRechazarSalarioCero() {
        assertThrows(
                IllegalArgumentException.class,
                () -> crearEmpleado(6, "0")
        );
    }

    @Test
    void debeRechazarSalarioNegativo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> crearEmpleado(6, "-1000")
        );
    }

    @Test
    void debeEliminarEspaciosDeIdentificacionYNombre() {
        EmpleadoAsalariado empleado = new EmpleadoAsalariado(
                " 123 ",
                " Carlos ",
                6,
                new BigDecimal("3000000")
        );

        assertEquals("123", empleado.getIdentificacion());
        assertEquals("Carlos", empleado.getNombre());
    }
}
