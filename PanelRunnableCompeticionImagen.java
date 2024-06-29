package cuentoliebretortuga;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class PanelRunnableCompeticionImagen implements Runnable {

	Thread t;
	String nombre;
	JLabel imagen;
	JLabel posi;
	public static int lugar; 
	private boolean isTurtle; 

	public PanelRunnableCompeticionImagen(String nombre, JLabel imagen, JLabel posi, boolean isTurtle) {
		this.nombre = nombre;
		this.imagen = imagen;
		this.posi = posi;
		this.isTurtle = isTurtle;
		this.t = new Thread(this, nombre);
		t.start();
	}

	@Override
	public void run() {
		int retardo = isTurtle ? 15 : 10; 
		try {
			lugar = 1;
			posi.setVisible(false);
			imagen.setVisible(true);

			for (int i = 10; i <= 1450; i++) {
				if (!isTurtle && i == 725) { 
					Thread.sleep(8000); 
				}
				imagen.setLocation(i, imagen.getY());
				Thread.sleep(retardo);
			}

			posi.setVisible(true);
			imagen.setVisible(false);
			posi.setText("La " + nombre + " ha llegado en la posición " + lugar);
			posi.setForeground(Color.WHITE);
			posi.setFont(new Font("Tahoma", Font.BOLD, 15));
			lugar++;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
