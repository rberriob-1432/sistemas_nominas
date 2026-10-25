package main.java.com.empresa.nomina.servicio;
import main.java.com.empresa.nomina.modelo.Empleado;
import main.java.com.empresa.nomina.repositorio.RepositorioEmpleados;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EmpleadoService {

    private final RepositorioEmpleados repositorio;

    public EmpleadoService(RepositorioEmpleados repositorio) {
        this.repositorio = Objects.requireNonNull(
                repositorio, "El repositorio es obligatorio.");
    }

    public void registrar(Empleado empleado) {
        Objects.requireNonNull(
                empleado, "El empleado es obligatorio.");

        repositorio.guardar(empleado);
    }

    public List<Empleado> listarTodos() {
        return repositorio.listarTodos();
    }

    public Optional<Empleado> buscar(String identificacion) {
        return repositorio.buscarPorIdentificacion(identificacion);
    }

    public void actualizar(
            String identificacion, Empleado empleadoActualizado) {

        Objects.requireNonNull(empleadoActualizado);

        boolean actualizado = repositorio.actualizar(
                identificacion, empleadoActualizado);

        if (!actualizado) {
            throw new IllegalArgumentException(
                    "No existe el empleado que desea actualizar.");
        }
    }

    public void eliminar(String identificacion) {
        boolean eliminado = repositorio.eliminar(identificacion);

        if (!eliminado) {
            throw new IllegalArgumentException(
                    "No existe el empleado que desea eliminar.");
        }
    }
}
