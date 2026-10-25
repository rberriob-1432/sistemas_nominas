package main.java.com.empresa.nomina.repositorio;
import main.java.com.empresa.nomina.modelo.Empleado;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Objects;

public class RepositorioEmpleadosMemoria
        implements RepositorioEmpleados {

    private final Map<String, Empleado> empleados =
            new LinkedHashMap<>();

    @Override
    public void guardar(Empleado empleado) {
        Objects.requireNonNull(empleado);

        if (existe(empleado.getIdentificacion())) {
            throw new IllegalArgumentException(
                    "La identificación ya está registrada.");
        }

        empleados.put(empleado.getIdentificacion(), empleado);
    }

    @Override
    public List<Empleado> listarTodos() {
        return new ArrayList<>(empleados.values());
    }

    @Override
    public Optional<Empleado> buscarPorIdentificacion(
            String identificacion) {

        if (identificacion == null || identificacion.isBlank()) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                empleados.get(identificacion.trim()));
    }

    @Override
    public boolean existe(String identificacion) {
        return identificacion != null
                && empleados.containsKey(identificacion.trim());
    }

    @Override
    public boolean actualizar(
            String identificacion, Empleado empleado) {

        Objects.requireNonNull(empleado);

        if (identificacion == null
                || !identificacion.equals(
                empleado.getIdentificacion())) {
            throw new IllegalArgumentException(
                    "La identificación no puede cambiar.");
        }

        if (!existe(identificacion)) {
            return false;
        }

        empleados.put(identificacion, empleado);
        return true;
    }

    @Override
    public boolean eliminar(String identificacion) {
        if (identificacion == null) {
            return false;
        }

        return empleados.remove(identificacion.trim()) != null;
    }
}
