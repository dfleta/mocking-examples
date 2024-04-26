Ejemplo uso Mockito
===================

Supongamos que nos encontramos en la construcción mediante TDD de la aplicación especificada en el ejercicio [Cotxox](https://github.com/dfleta/cotxox "cotxox").

La clase `Carrera` depende de la lógica que por SRP corresponde a las clases que implementan las interfaces `Conductora`, `PoolConductora` y `Tarifa`.

Si las historias de usuario más valiosas suponen la implementación de la clase `Carrera` llegaremos el momento en el que necesitaremos la información proporcionada por los componentes de los que depende. Si la lógica o algoritmia y el diseño de estas clases supone cierta dificultad que no puede ser resuelta de manera ágil, podemos aplicar los principios SOLID para mantener las interfaces públicas de dichos componentes adheridas a dichos principios y "mockear" o crear métodos _stub_ que sustituyan dichas funcionalidades. 

Utilizamos [mockito](https://site.mockito.org/) como _framework_ en Junit para construir el comportamiento descrito.

Mockito, además, nos ofrece la posibilidad de realizar cierta reflexión sobre el comportamiento de las clases que "mockea", "espiar" en las tripas de los objetos mockeados, para comprobar que el comportamiento es como suponemos, al igual que hacía [Slimer](slimer.webp "Slimer ghost busters")
 en cazafantasmas.


```java
@Test
public void asignarConductor(){
	// Utilizamos las interfaces para crear los mocks
	// de Conductora y PoolConductoras
	// pues en ellas disponemos de los
	// métodos abstractos sin implementación.
	Conductora mockConductor = mock(Conductora.class);
	when(mockConductor.getNombre()).thenReturn("Samantha");

	carrera.setConductor(null);
	assertNull(carrera.getConductor());

	PoolConductoras mockPool = mock(PoolConductoras.class);
	when(mockPool.asignarConductor()).thenReturn(mockConductor);

	carrera.asignarConductor(mockPool);
	// verificamos que asignarConductor() de PoolConductora
    // ha sido invocado a través de carrera.asignarConductor()
	verify(mockPool).asignarConductor();

	assert(carrera.getConductor()!=null);
	assertEquals("Samantha", carrera.getConductor().getNombre());
}
```

## Diagrama de clases UML

![Diagrama de clases UML](./diagrama_clases_UML.png "Diagrama de clases UML")

## Referencia de Mockito

Aquí un [tutorial](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html "tutorial mockito") con ejemplos básicos de uso. Conviene que lo leas para usar los mocks de manera eficiente:

https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html

No está de más leerse esta [RefCard](https://dzone.com/refcardz/mockito "refcard mockito"):

https://dzone.com/refcardz/mockito

## Microservicios con moc(kit)os

Testear aplicaciones en el Mundo Real TM es un poco más complicado y, si a la dificultad añadida por la arquitectura cliente-servidor, le sumamos la de una arquitectura basada en microservicios, lejos de las placenteras aplicaciones monolíticas, entonces es necesario disponer de un conocimiento más profundo de la API del _framework_ de testig y mock.

En mi proyeto [grpc-tourism-receptive](https://github.com/dfleta/grpc-tourism-receptive) encontrarás a mockito y Slimer en acción en una aplicación basada en el framework gRPC de Google para la construcción de aplicaciones basadas en microservicios, implementando el patrón GoF _observer_ para implementar la lógica de un receptivo turístico al estilo mallorquín ;)

https://github.com/dfleta/grpc-tourism-receptive

Aquí los casos test: [test grpc](https://github.com/dfleta/grpc-tourism-receptive/tree/master/src/test/java/org/elsmancs/grpc)

En los test de los componentes "clientes" de los servidores se concentra el uso de mockito, para simular que disponemos de servidores de pago, crisal y UFOs que proveen de los objetos que necesitamos para completar el desarrollo de los componentes cliente indendientemente de su estado de desarrollo y funcionamiento:

* [PaymentClientTest](https://github.com/dfleta/grpc-tourism-receptive/blob/master/src/test/java/org/elsmancs/grpc/payment/PaymentClientTest.java)
* [CrystalClientTest](https://github.com/dfleta/grpc-tourism-receptive/blob/master/src/test/java/org/elsmancs/grpc/crystal/CrystalClientTest.java)
* [UfosParkClientTest](https://github.com/dfleta/grpc-tourism-receptive/blob/master/src/test/java/org/elsmancs/grpc/ufos/UfosParkClientTest.java)

```java
@Test
public void UfoOf_test() {

    // Mensaje CreditCard al servicio
    CreditCard card = CreditCard.newBuilder()
                                    .setOwner("Rick")
                                    .setNumber("111111111111")
                                    .build();

    AtomicReference<CreditCard> cardDelivered = new AtomicReference<CreditCard>();

    // Mock de la respuesta /mensaje Processed del servicio
    Ufo responseUfo = Ufo.newBuilder()
                            .setId("unox")
                            .setCardNumbe("111111111111")
                            .setFee(500)
                            .build();

    // Fake service
    UfosParkImplBase assignImpl = new UfosParkImplBase() {
        @Override
        public void ufoOf(CreditCard request, StreamObserver<org.elsmancs.grpc.Ufo> response) {
            // para chequear que la construccion del Ufo en el client se realiza OK
            cardDelivered.set(request);
            // return the Ufo
            response.onNext(responseUfo);
            // Specify that we’ve finished dealing with the RPC.
            response.onCompleted();
        }
    };

    serviceRegistry.addService(assignImpl);

    String ufoID = client.UfoOf("Rick", "111111111111");

    assertEquals(card, cardDelivered.get());
    assertEquals(responseUfo.getId(), ufoID);
}    
```

## Frontend testing & mocks

En mi proyecto ejemplo sobre el patrón _proxy_ (+ _singleton_), patrón _flyweight_, herencia por prototipos en JS, prototipos delegados, extensión dinámica de objetos, _constructor functions_ [proxy pattern mr meeseeks](https://github.com/dfleta/proxy-pattern-mrMeeseks-js) encontrarás ejemplos de uso de mocking en front, utilizando la librería [Jest](https://jestjs.io/es-ES/ "jest") para JavaScript /EmacScript.

Es conveniente leer el manual de la librería sobre [funciones mock](https://jestjs.io/es-ES/docs/mock-functions)

En estos casos test se hace uso de las funcionalidades de Jest para mockear componentes JavaScript:

* [box.test](https://github.com/dfleta/proxy-pattern-mrMeeseks-js/blob/master/src/box/test/box.test.js)

* [mrmeeseeks.test](https://github.com/dfleta/proxy-pattern-mrMeeseks-js/blob/master/src/mrmeeseeks/test/mrmeeseeks.test.js)

```js
test('fullfillRequest ejecuta this.accion()', () => {

        // MOCK FUNCTIONS con IMPLEMENTATIONS

        /**
         * La funcion accion que necesita fulfillRequest
         * ha de ser creada previamente por makeRequest()
         * e inyectada en el objeto meeseeks.
         * No podemos depender de la implementacion de makeRequest()
         * para pasar este test => mockear la funcion accion() que
         * inyecta makeRequest() e inyectarla a mano en el objeto meeseeks 
         */

        const accionMock = jest
                            .fn()
                            .mockImplementation( () => "open" + " " + "Jerry's head")
                            .mockName('accionMock') // mensajes especificos en test errors outputs

        // inyecto en el objeto la funcion mock
        meeseeks.accion = accionMock;
        expect(meeseeks).toHaveProperty('accion'); // primera invocacion de la funcion accionMock
        
        // el objeto meeseeks invoca a la funcion mock == segunda invocacion de la función mock
        expect(meeseeks.fulfillRequest()).toEqual(expect.stringMatching("open" + " " + "Jerry's head" + " All done!!"))

        // accionMock ha debido ser llamada desde fulfillRequest()
        expect(accionMock).toHaveBeenCalled();
        // La funcion ha sido llamada exactamente dos veces: toHaveProperty + fullfillRequest
        expect(accionMock.mock.calls.length).toBe(2);
        // El valor devuelto en la segunda llamada a la funcion ha sido "open Jerry's head"
        expect(accionMock.mock.results[1].value).toBe("open Jerry's head");
        // La funcion ha sido llamada con un cierto contexto `this`: el objeto `meeseeks`
        expect(accionMock.mock.contexts[0]).toBe(meeseeks);
        // Ek primer argumento de la ultima llamada a la funcion ha sido 'undefined', accion()
        expect(accionMock.mock.lastCall[0]).toBe(undefined);
    });
```