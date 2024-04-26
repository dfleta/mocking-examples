package org.foobarspam.MockitoEjemplo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class CarreraTest {
	
	Carrera carrera;

	@Before
	public void ConstructorTest(){
		
		// http://ensaimeitor.apsl.net/gen_visa/10/
		
		String[] tarjetasVisa = {"4929637175949220",
								"4024007198786385",
								"4417129513323481",
								"4532121888937144",
								"4916119711304546"
								};
		
		for(String tarjetaVisa : tarjetasVisa){
			carrera = new Carrera(tarjetaVisa);
			assertEquals(tarjetaVisa, carrera.getTarjetaCredito());
		}		
	}
	
	@Test
	public void setOrigenTest() {
		String origen = "Aeroport Son Sant Joan";
		carrera.setOrigen(origen);
		assertEquals(origen, carrera.getOrigen());
	}
	
	@Test
	public void setDestinoTest() {
		String destino= "Magaluf";
		carrera.setDestino(destino);
		assertEquals(destino, carrera.getDestino());
	}
	
	@Test
	public void setDistanciaTest() {
		double distancia = 7.75;
		double delta = 0.001;
		carrera.setDistancia(distancia);
		assertEquals(distancia, carrera.getDistancia(), delta);
	}
	
	@Test
	public void setTiempoEsperadoTest() {
		int minutos = 10;
		double delta = 0;
		carrera.setTiempoEsperado(minutos);
		assertEquals(minutos, carrera.getTiempoEsperado(), delta);
	}
	
	@Test
	public void setTiempoCarreraTest(){
		int minutos = 10;
		double delta = 0;
		carrera.setTiempoCarrera(minutos);
		assertEquals(minutos, carrera.getTiempoCarrera(), delta);	
	}
	
	/**
	 * Casos test con dependencia a las interfaces:
	 * Conductor, PoolConductores y Tarifa.
	 * Mockeamos la lógica de las interfaces de las
	 * que dependemos.
	 * Al usar interfaces evitamos la necesidad de
	 * la implementación de las clases y sus constructores
	 * y una implementación básica de la rutina que
	 * proveerá del comportamiento que necesitamos.
	 */

	@Test
	public void setConductorTest(){
		// Conductor conductor = new Conductor(nombre);
		// Utilizamos las interfaces para crear los mocks
		// de los objetos, pues en ellas disponemos de los
		// métodos abstractos sin implementación.
		Conductora mockConductor = mock(Conductora.class);
		
		String test="Samantha"; 
		when(mockConductor.getNombre()).thenReturn(test);

		carrera.setConductor(mockConductor);
		assertEquals("Samantha", carrera.getConductor().getNombre());

		test="Prius";
		when(mockConductor.getModelo()).thenReturn(test); 
		assertEquals(test, carrera.getModeloVehiculo());
		// verificamos que carrera.getModeloVehiculo() ha invocado
		// a la rurina getModelo() de conductor
		verify(mockConductor).getModelo();
		// expresado de otra manera:
		verify(mockConductor, times(1)).getModelo();

		test="JFK123"; 
		when(mockConductor.getMatricula()).thenReturn(test);
		assertEquals(test, carrera.getMatricula());
		verify(mockConductor).getMatricula();
	}
	
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
		// asignarConductor() de PoolConductora ha sido invocado:
		verify(mockPool).asignarConductor();

		assert(carrera.getConductor()!=null);
		assertEquals("Samantha", carrera.getConductor().getNombre());
	}

	/**
	 * Eliminamos este test de la clase
	 * CarreraTest porque la responsabilidad
	 * (la logica) de calcular el coste de la 
	 * carrera se encuentra en la clase Tarifa.
	 * Se testeara alli.
	 */

	// @Test public void getCosteEsperadoTest() {}
	
	@Test
	public void realizarPagoTest(){
		Tarifa mockTarifa = mock(Tarifa.class);
		when(mockTarifa.getCosteTotalEsperado(carrera)).thenReturn(10d);

		carrera.realizarPago(mockTarifa.getCosteTotalEsperado(carrera));
		assertEquals(mockTarifa.getCosteTotalEsperado(carrera), carrera.getCosteTotal() , 0d);
	}
	
	@Test
	public void liberarConductor(){
		Conductora mockConductor = mock(Conductora.class);
		when(mockConductor.isOcupado()).thenReturn(false);
		carrera.setConductor(mockConductor);
		assertNotNull(carrera.getConductor());
		doNothing().when(mockConductor).setOcupado(false);
		carrera.liberarConductor(); // ejem, no testea la logica de carrera
		verify(mockConductor).setOcupado(false);
		assert(!carrera.getConductor().isOcupado());
	}
	
	@Test
	public void setValoracion(){

		Conductora mockConductor = mock(Conductora.class);
		carrera.setConductor(mockConductor);
		
		Double valoracion = 5d;
		when(mockConductor.getValoracion()).thenReturn(valoracion);
		// verify(mockConductor).getValoracion();
		// Esta primera llamada lanza una runtime exception.
		// there were zero interactions with this mock.
		assertEquals(valoracion, carrera.getValoracionConductor());
		// Chequeamos que desde carrera hemos invocado 
		// getValoracion() del objeto mockConductor
 		verify(mockConductor).getValoracion();
		
		carrera.getConductor().setValoracion((byte) 5);
		assertEquals(5, carrera.getConductor().getValoracion() , 0);
	}
}
