package main.java.com.empresa.nomina.repositorio;
import main.java.com.empresa.nomina.modelo.Empleado;

import java.util.List;
import java.util.Optional;

public interface RepositorioEmpleados {

    void guardar(Empleado empleado);

    List<Empleado> listarTodos();

    Optional<Empleado> buscarPorIdentificacion(String identificacion);

    boolean existe(String identificacion);

    boolean actualizar(String identificacion, Empleado empleado);

    boolean eliminar(String identificacion);
}
