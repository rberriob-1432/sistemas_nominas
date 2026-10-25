package test.java.com.empresa.nomina.presentacion;
import main.java.com.empresa.nomina.presentacion.MenuConsola;
import main.java.com.empresa.nomina.repositorio.RepositorioEmpleadosMemoria;
import main.java.com.empresa.nomina.servicio.EmpleadoService;
import main.java.com.empresa.nomina.servicio.NominaService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MenuConsolaTest {

    @Test
    void debeMostrarMenuYSalir() {
        String salida = ejecutar("0\n");

        assertTrue(salida.contains("SISTEMA DE NÓMINA"));
        assertTrue(salida.contains("Saliendo del sistema"));
    }

    @Test
    void debeMostrarErrorParaOpcionInvalida() {
        String salida = ejecutar("99\n0\n");

        assertTrue(salida.contains("Opción inválida"));
    }



    @Test
    void debeRegistrarYListarEmpleado() {
        String salida = ejecutar(
                "1\n001\nCarlos\n2\n1000000\n" +
                        "2\n0\n"
        );

        System.out.println("SALIDA CAPTURADA:\n" + salida);

        assertTrue(
                salida.contains("Empleado registrado correctamente"),
                "No se encontró el mensaje de registro. Salida:\n" + salida
        );

        assertTrue(
                salida.contains("Carlos"),
                "No se encontró el nombre Carlos en la salida. Salida:\n" + salida
        );
    }


    private String ejecutar(String entradas) {
        var salida = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try (PrintStream captura =
                     new PrintStream(salida, true, StandardCharsets.UTF_8);
             Scanner scanner = new Scanner(new StringReader(entradas))) {

            System.setOut(captura);

            var repositorio = new RepositorioEmpleadosMemoria();
            var empleadoService = new EmpleadoService(repositorio);
            var nominaService = new NominaService();

            var menu = new MenuConsola(
                    scanner, empleadoService, nominaService
            );

            menu.iniciar();

        } finally {
            System.setOut(original);
        }

        return salida.toString(StandardCharsets.UTF_8);
    }
}
