# Atractor de Lorenz

Dibuja el atractor de Lorenz, el sistema caótico de la "mariposa", con tres trayectorias que parten de puntos casi iguales y acaban separándose.

## Características

- Integración numérica con el método de Euler (paso 0.001, 50 000 iteraciones).
- Tres trayectorias de colores (amarillo, azul y rojo) con una diferencia inicial de 0.01.
- Proyección en el plano x-z, que se ajusta al tamaño de la ventana.

## Requisitos

- JDK 21 y Maven 3.9 o superior.

## Compilar y ejecutar

```bash
mvn compile exec:java
```

## Notas

- Ilustra la sensibilidad a las condiciones iniciales: una diferencia de 0.01 acaba produciendo trayectorias totalmente distintas.

## JAR ejecutable (fat jar)

```bash
mvn clean package
java -jar target/mvn-lorenz-1.0.0-SNAPSHOT-launcher.jar
```

El archivo `-launcher.jar` incluye todas las dependencias.

## Licencia

Código de ejemplo de uso libre con fines educativos.
