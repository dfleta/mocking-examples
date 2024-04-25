package org.foobarspam.MockitoEjemplo;

public interface PoolConductoras {
	
	public Conductor asignarConductor();
	
	public void addConductor(Conductor conductor);
	
	public int numeroConductores();
	
	public Conductor getConductorAt(int index);

}
