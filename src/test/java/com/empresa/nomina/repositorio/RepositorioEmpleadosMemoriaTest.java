package test.java.com.empresa.nomina.repositorio;
import main.java.com.empresa.nomina.modelo.EmpleadoAsalariado;
import main.java.com.empresa.nomina.repositorio.RepositorioEmpleadosMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioEmpleadosMemoriaTest {

    private RepositorioEmpleadosMemoria repositorio;

    @BeforeEach
    void configurar() {
        repositorio = new RepositorioEmpleadosMemoria();
    }

    private EmpleadoAsalariado empleado(String id) {
        return new EmpleadoAsalariado(
                id,
                "Carlos",
                2,
                new BigDecimal("1000000")
        );
    }

    @Test
    void debeGuardarEmpleado() {
        repositorio.guardar(empleado("001"));

        assertTrue(repositorio.existe("001"));
    }

    @Test
    void noDebeGuardarIdentificacionDuplicada() {
        repositorio.guardar(empleado("001"));

        assertThrows(
                IllegalArgumentException.class,
                () -> repositorio.guardar(empleado("001"))
        );
    }

    @Test
    void debeListarTodosLosEmpleados() {
        repositorio.guardar(empleado("001"));
        repositorio.guardar(empleado("002"));

        assertEquals(2, repositorio.listarTodos().size());
    }

    @Test
    void debeBuscarPorIdentificacion() {
        repositorio.guardar(empleado("001"));

        assertTrue(
                repositorio.buscarPorIdentificacion("001").isPresent()
        );
    }

    @Test
    void debeRetornarVacioCuandoNoExiste() {
        assertTrue(
                repositorio.buscarPorIdentificacion("999").isEmpty()
        );
    }

    @Test
    void debeActualizarEmpleadoExistente() {
        repositorio.guardar(empleado("001"));

        EmpleadoAsalariado actualizado = new EmpleadoAsalariado(
                "001",
                "Pedro",
                6,
                new BigDecimal("2000000")
        );

        assertTrue(repositorio.actualizar("001", actualizado));

        assertEquals(
                "Pedro",
                repositorio.buscarPorIdentificacion("001")
                        .orElseThrow()
                        .getNombre()
        );
    }

    @Test
    void debeRetornarFalsoAlActualizarEmpleadoInexistente() {
        assertFalse(repositorio.actualizar("001", empleado("001")));
    }

    @Test
    void noDebePermitirCambiarLaIdentificacion() {
        repositorio.guardar(empleado("001"));

        assertThrows(
                IllegalArgumentException.class,
                () -> repositorio.actualizar("001", empleado("002"))
        );
    }

    @Test
    void debeEliminarEmpleadoExistente() {
        repositorio.guardar(empleado("001"));

        assertTrue(repositorio.eliminar("001"));
        assertFalse(repositorio.existe("001"));
    }

    @Test
    void debeRetornarFalsoAlEliminarEmpleadoInexistente() {
        assertFalse(repositorio.eliminar("999"));
    }
}
