# Ejemplo uso Mockito

Supongamos que nos encontramos en la construcción mediante TDD de la aplicación especificada en el ejercicio [Cotxox](https://github.com/dfleta/cotxox "cotxox").

La clase `Carrera` depende de la lógica que por SRP corresponde a las clases que implementan las interfaces `Conductora`, `PoolConductora` y `Tarifa`.

"Mockeamos" la lógica de los métodos que necesitamos de esas clases surgidos a lo largo del proceso TDD.

## Diagrama de clases UML

![Diagrama de clases UML](./diagrama_clases_UML.png "Diagrama de clases UML")