
package main.java.com.empresa.nomina.presentacion;

import main.java.com.empresa.nomina.modelo.Empleado;
import main.java.com.empresa.nomina.modelo.EmpleadoAsalariado;
import main.java.com.empresa.nomina.servicio.EmpleadoService;
import main.java.com.empresa.nomina.servicio.NominaService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class MenuConsola {

    private final Scanner scanner;
    private final EmpleadoService empleadoService;
    private final NominaService nominaService;
    private final Map<Integer, Runnable> opciones = new HashMap<>();

    public MenuConsola(
            Scanner scanner,
            EmpleadoService empleadoService,
            NominaService nominaService) {

        this.scanner = Objects.requireNonNull(scanner);
        this.empleadoService = Objects.requireNonNull(empleadoService);
        this.nominaService = Objects.requireNonNull(nominaService);

        configurarOpciones();
    }

    private void configurarOpciones() {
        opciones.put(1, this::registrar);
        opciones.put(2, this::listar);
        opciones.put(3, this::buscar);
        opciones.put(4, this::actualizar);
        opciones.put(5, this::eliminar);
        opciones.put(6, this::calcularNomina);
    }

    public void iniciar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");

            if (opcion == 0) {
                System.out.println("Saliendo del sistema...");
            } else {
                ejecutarOpcion(opcion);
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("""
                
                ===== SISTEMA DE NÓMINA =====
                1. Registrar empleado
                2. Listar empleados
                3. Buscar empleado
                4. Actualizar empleado
                5. Eliminar empleado
                6. Calcular nómina
                0. Salir
                """);
    }

    private void ejecutarOpcion(int opcion) {
        Runnable accion = opciones.get(opcion);

        if (accion == null) {
            System.out.println("Opción inválida.");
            return;
        }

        try {
            accion.run();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrar() {
        empleadoService.registrar(solicitarEmpleado(null));
        System.out.println("Empleado registrado correctamente.");
    }

    private void listar() {
        var empleados = empleadoService.listarTodos();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        empleados.forEach(this::mostrarEmpleado);
    }

    private void buscar() {
        empleadoService.buscar(leerTexto("Identificación: "))
                .ifPresentOrElse(
                        this::mostrarEmpleado,
                        () -> System.out.println("Empleado no encontrado.")
                );
    }

    private void actualizar() {
        String id = leerTexto("Identificación del empleado: ");

        Optional<Empleado> encontrado = empleadoService.buscar(id);

        if (encontrado.isEmpty()) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        Empleado actualizado = solicitarEmpleado(
                encontrado.get().getIdentificacion()
        );

        empleadoService.actualizar(id, actualizado);
        System.out.println("Empleado actualizado correctamente.");
    }

    private void eliminar() {
        String id = leerTexto("Identificación del empleado: ");

        if (empleadoService.buscar(id).isEmpty()) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        String confirmacion = leerTexto("¿Eliminar empleado? (s/n): ");

        if (confirmacion.equalsIgnoreCase("s")) {
            empleadoService.eliminar(id);
            System.out.println("Empleado eliminado correctamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void calcularNomina() {
        Optional<Empleado> encontrado = empleadoService.buscar(
                leerTexto("Identificación del empleado: ")
        );

        if (encontrado.isEmpty()) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        Empleado empleado = encontrado.get();

        BigDecimal beneficios = empleado.calcularBeneficios();
        BigDecimal deducciones =
                nominaService.calcularDeducciones(empleado);
        BigDecimal salarioNeto =
                nominaService.calcularSalarioNeto(empleado);

        System.out.println("\n===== DESPRENDIBLE DE NÓMINA =====");
        System.out.println("Empleado: " + empleado.getNombre());
        System.out.println("Identificación: "
                + empleado.getIdentificacion());
        System.out.println("Salario base: " + empleado.getSalarioBase());
        System.out.println("Beneficios: " + beneficios);
        System.out.println("Deducciones (8 %): " + deducciones);
        System.out.println("Salario neto: " + salarioNeto);
        System.out.println("==================================");
    }

    private Empleado solicitarEmpleado(String idExistente) {
        String id = idExistente == null
                ? leerTexto("Identificación: ")
                : idExistente;

        String nombre = leerTexto("Nombre: ");
        int antiguedad = leerEntero("Años de antigüedad: ");
        BigDecimal salario = leerMonto("Salario base: ");

        return new EmpleadoAsalariado(
                id, nombre, antiguedad, salario
        );
    }

    private void mostrarEmpleado(Empleado empleado) {
        System.out.printf(
                "ID: %s | Nombre: %s | Antigüedad: %d años | "
                        + "Salario: %s | Beneficios: %s%n",
                empleado.getIdentificacion(),
                empleado.getNombre(),
                empleado.getAniosAntiguedad(),
                empleado.getSalarioBase(),
                empleado.calcularBeneficios()
        );
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private BigDecimal leerMonto(String mensaje) {
        while (true) {
            try {
                BigDecimal monto = new BigDecimal(leerTexto(mensaje));

                if (monto.signum() <= 0) {
                    System.out.println(
                            "El salario debe ser mayor que cero.");
                    continue;
                }

                return monto;
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un monto válido.");
            }
        }
    }
}
