package test.java.com.empresa.nomina.servicio;
import main.java.com.empresa.nomina.modelo.EmpleadoAsalariado;
import main.java.com.empresa.nomina.repositorio.RepositorioEmpleadosMemoria;
import main.java.com.empresa.nomina.servicio.EmpleadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoServiceTest {

    private RepositorioEmpleadosMemoria repositorio;
    private EmpleadoService servicio;

    @BeforeEach
    void configurar() {
        repositorio = new RepositorioEmpleadosMemoria();
        servicio = new EmpleadoService(repositorio);
    }

    private EmpleadoAsalariado empleado(String id, String nombre) {
        return new EmpleadoAsalariado(
                id,
                nombre,
                2,
                new BigDecimal("1000000")
        );
    }

    @Test
    void debeRegistrarEmpleado() {
        servicio.registrar(empleado("001", "Carlos"));

        assertTrue(servicio.buscar("001").isPresent());
    }

    @Test
    void debeListarEmpleados() {
        servicio.registrar(empleado("001", "Carlos"));
        servicio.registrar(empleado("002", "Ana"));

        assertEquals(2, servicio.listarTodos().size());
    }

    @Test
    void debeBuscarEmpleado() {
        servicio.registrar(empleado("001", "Carlos"));

        assertEquals(
                "Carlos",
                servicio.buscar("001").orElseThrow().getNombre()
        );
    }

    @Test
    void debeActualizarEmpleado() {
        servicio.registrar(empleado("001", "Carlos"));

        servicio.actualizar("001", empleado("001", "Pedro"));

        assertEquals(
                "Pedro",
                servicio.buscar("001").orElseThrow().getNombre()
        );
    }

    @Test
    void debeRechazarActualizacionInexistente() {
        assertThrows(
                IllegalArgumentException.class,
                () -> servicio.actualizar(
                        "999",
                        empleado("999", "Ana")
                )
        );
    }

    @Test
    void debeEliminarEmpleado() {
        servicio.registrar(empleado("001", "Carlos"));

        servicio.eliminar("001");

        assertTrue(servicio.buscar("001").isEmpty());
    }

    @Test
    void debeRechazarEliminacionInexistente() {
        assertThrows(
                IllegalArgumentException.class,
                () -> servicio.eliminar("999")
        );
    }

    @Test
    void debeRechazarEmpleadoNulo() {
        assertThrows(
                NullPointerException.class,
                () -> servicio.registrar(null)
        );
    }
}