package org.foobarspam.MockitoEjemplo;

public interface Conductora {
	
	public String getNombre();

	public String getModelo();

	public void setModelo(String modelo);

	public String getMatricula();

	public void setMatricula(String matricula);

	public double getValoracion();
	
	public int getNumeroValoraciones();

	public void setValoracion(byte valoracion);
	
	// private double calcularValoracionMedia();
	
	public void setOcupado(boolean ocupado);
	
	public boolean isOcupado();

}
