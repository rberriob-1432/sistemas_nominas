package test.java.com.empresa.nomina.servicio;

import main.java.com.empresa.nomina.modelo.EmpleadoAsalariado;
import main.java.com.empresa.nomina.servicio.NominaService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class NominaServiceTest {

    private final NominaService nominaService = new NominaService();

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
    void debeCalcularDeduccionesAutomaticasDelOchoPorCiento() {
        EmpleadoAsalariado empleado = crearEmpleado(6, "3000000");

        assertEquals(
                new BigDecimal("240000.00"),
                nominaService.calcularDeducciones(empleado)
        );
    }

    @Test
    void debeCalcularSalarioNetoConBonoYDeducciones() {
        EmpleadoAsalariado empleado = crearEmpleado(6, "3000000");

        // Base: 3.000.000 + bono: 300.000 - deducciones: 240.000
        assertEquals(
                new BigDecimal("3060000.00"),
                nominaService.calcularSalarioNeto(empleado)
        );
    }

    @Test
    void debeCalcularSalarioNetoSinBono() {
        EmpleadoAsalariado empleado = crearEmpleado(5, "3000000");

        // Base: 3.000.000 - deducciones: 240.000
        assertEquals(
                new BigDecimal("2760000.00"),
                nominaService.calcularSalarioNeto(empleado)
        );
    }

    @Test
    void debeRechazarEmpleadoNuloAlCalcularDeducciones() {
        assertThrows(
                NullPointerException.class,
                () -> nominaService.calcularDeducciones(null)
        );
    }

    @Test
    void debeRechazarEmpleadoNuloAlCalcularSalarioNeto() {
        assertThrows(
                NullPointerException.class,
                () -> nominaService.calcularSalarioNeto(null)
        );
    }
}
