package org.foobarspam.MockitoEjemplo;

import java.util.ArrayList;
import java.util.Random;

public class PoolConductores implements PoolConductoras {
	
	private ArrayList<Conductor> poolConductores = new ArrayList<>();
	
	public PoolConductores(ArrayList<Conductor> poolConductores){
		this.poolConductores = poolConductores;
	}
	
	public ArrayList<Conductor> getPoolConductores(){
		return this.poolConductores;
	}
	
	public Conductor asignarConductor(){
		Conductor conductor = new Conductor();
		Random aleatorio = new Random();
		boolean asignado = false;
		while(!asignado){
			int index = aleatorio.nextInt(getPoolConductores().size());
			conductor = getPoolConductores().get(index);
			if(!conductor.isOcupado()){
				conductor.setOcupado(true);
				asignado = true;
			}
		}
		return conductor;
	}
	
	public void addConductor(Conductor conductor){
		this.poolConductores.add(conductor);
	}
	
	public int numeroConductores(){
		return this.poolConductores.size();
	}
	
	public Conductor getConductorAt(int index){
		return this.poolConductores.get(index);
	}
	
}
