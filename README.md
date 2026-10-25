# Sistema de Nómina en Java

## Descripción

Aplicación de consola desarrollada en Java para gestionar empleados y calcular su nómina. El proyecto aplica principios de programación orientada a objetos y separación de responsabilidades.

## Funcionalidades

- Registrar empleados.
- Listar empleados registrados.
- Buscar empleados por identificador.
- Actualizar información de empleados.
- Eliminar empleados.
- Calcular la nómina de cada empleado.
- Calcular deducciones, bonificaciones por antigüedad y salario neto.
- Ejecutar pruebas automatizadas con JUnit y Maven.

## Tecnologías utilizadas

- **Java:** lenguaje de programación.
- **Maven:** gestión de dependencias y ejecución de pruebas.
- **JUnit:** pruebas automatizadas.
- **Git y GitHub:** control de versiones y alojamiento del código.

## Reglas de cálculo

El proyecto utiliza las siguientes reglas académicas de ejemplo:

- **Deducciones:** 8 % del salario base.
- **Bonificación por antigüedad:** 10 % del salario base cuando el empleado tiene más de 5 años de antigüedad.
- **Salario neto:** salario base + bonificación − deducciones.

Estas reglas son las definidas para el ejercicio y no representan necesariamente las normas laborales vigentes en Colombia.

## Estructura general

```text
sistema-nominas/
├── src/
│   ├── main/
│   └── test/
├── pom.xml
└── README.md
```

La carpeta `src/main` contiene el código de la aplicación y `src/test` contiene las pruebas automatizadas. El archivo `pom.xml` define la configuración de Maven y las dependencias del proyecto.

## Ejecución

### Requisitos previos

- JDK compatible con la versión configurada en `pom.xml`.
- Maven instalado.

### Ejecutar las pruebas

Desde la raíz del proyecto:

```bash
mvn clean test
```

### Ejecutar la aplicación

Abre el proyecto en IntelliJ IDEA y ejecuta la clase `Main` que contiene el método `main`.

## Pruebas automatizadas

Las pruebas verifican el comportamiento del repositorio de empleados, los servicios de aplicación, los cálculos de nómina y el menú de consola.

Para comprobar que las pruebas pasan, ejecuta:

```bash
mvn clean test
```

## Autor

Raul Alonso Berrio Blanquicett
