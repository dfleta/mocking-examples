package org.foobarspam.MockitoEjemplo;

import java.util.ArrayList;
import java.util.Random;

public class PoolConductores implements PoolConductoras {
	
	private ArrayList<Conductora> poolConductores = new ArrayList<>();
	
	public PoolConductores(ArrayList<Conductora> poolConductores){
		this.poolConductores = poolConductores;
	}
	
	public ArrayList<Conductora> getPoolConductores(){
		return this.poolConductores;
	}
	
	public Conductora asignarConductor(){
		Conductora conductor = new Conductor();
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
	
	public void addConductor(Conductora conductor){
		this.poolConductores.add(conductor);
	}
	
	public int numeroConductores(){
		return this.poolConductores.size();
	}
	
	public Conductora getConductorAt(int index){
		return this.poolConductores.get(index);
	}
	
}
