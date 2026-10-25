package main.java.com.empresa.nomina;
import main.java.com.empresa.nomina.presentacion.MenuConsola;
import main.java.com.empresa.nomina.repositorio.RepositorioEmpleados;
import main.java.com.empresa.nomina.repositorio.RepositorioEmpleadosMemoria;
import main.java.com.empresa.nomina.servicio.EmpleadoService;
import main.java.com.empresa.nomina.servicio.NominaService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        RepositorioEmpleados repositorio =
                new RepositorioEmpleadosMemoria();

        EmpleadoService empleadoService =
                new EmpleadoService(repositorio);

        NominaService nominaService = new NominaService();

        try (Scanner scanner = new Scanner(System.in)) {
            MenuConsola menu = new MenuConsola(
                    scanner,
                    empleadoService,
                    nominaService
            );

            menu.iniciar();
        }
    }
}
