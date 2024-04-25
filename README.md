Ejemplo uso Mockito
===================

Supongamos que nos encontramos en la construcción mediante TDD de la aplicación especificada en el ejercicio [Cotxox](https://github.com/dfleta/cotxox "cotxox").

La clase `Carrera` depende de la lógica que por SRP corresponde a las clases que implementan las interfaces `Conductora`, `PoolConductora` y `Tarifa`.

Si las historias de usuario más valiosas suponen la implementación de la clase `Carrera` llegaremos el momento en el que necesitaremos la información proporcionada por los componentes de los que depende. Si la lógica o algoritmia y el diseño de estas clases supone cierta dificultad que no puede ser resuelta de manera ágil, podemos aplicar los principios SOLID para mantener las interfaces públicas de dichos componentes adheridas a dichos principios y "mockear" o crear métodos _stub_ que sustituyan dichas funcionalidades. 

Utilizamos [mockito](https://site.mockito.org/) como _framework_ en Junit para construir el comportamiento descrito.

Mockito, además, nos ofrece la posibilidad de realizar cierta reflexión sobre el comportamiento de las clases que "mockea", "espiar" en las tripas de los objetos mockeados, para comprobar que el comportamiento es como suponemos, al igual que hacía Slimer en cazafantasmas.

![slimer](slimer.webp "slimer ghost busters")

## Diagrama de clases UML

![Diagrama de clases UML](./diagrama_clases_UML.png "Diagrama de clases UML")

## Microservicios con moc(kit)os

Testear aplicaciones en el Mundo Real TM es un poco más complicado y, si a la dificultad añadida por la arquitectura cliente-servidor, le sumamos la de una arquitectura basada en microservicios, lejos de las placenteras aplicaciones monolíticas, entonces es necesario disponer de un conocimiento más profundo de la API del _framework_ de testig y mock.

En mi proyeto [grpc-tourism-receptive](https://github.com/dfleta/grpc-tourism-receptive) encontrarás a mockito y Slimer en acción en una aplicación basada en el framework gRPC de Google para la construcción de aplicaciones basadas en microservicios, implementando el patrón GoF _observer_ para implementar la lógica de un receptivo tutístico:

https://github.com/dfleta/grpc-tourism-receptive
