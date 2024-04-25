package org.foobarspam.MockitoEjemplo;

public interface PoolConductoras {
	
	public Conductora asignarConductor();
	
	public void addConductor(Conductora conductor);
	
	public int numeroConductores();
	
	public Conductora getConductorAt(int index);

}
