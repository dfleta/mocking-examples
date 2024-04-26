package org.foobarspam.MockitoEjemplo;

public interface Conductora {
	
	public String getNombre();

	public String getModelo();

	public String getMatricula();

	public double getValoracion();
	
	public void setValoracion(byte valoracion);
		
	public void setOcupado(boolean ocupado);
	
	public boolean isOcupado();

}
