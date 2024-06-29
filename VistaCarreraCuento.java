package cuentoliebretortuga;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;

public class VistaCarreraCuento extends JFrame {

	private JPanel contentPane;
	private JLabel conejo;
	private JLabel tortuga;
	private JLabel posi_conejo;
	private JLabel posi_tortuga;
	private Image backgroundImage;

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(() -> {
			new VistaCarreraCuento().setVisible(true);
		});
	}

	public VistaCarreraCuento() {
		setTitle("Tortuga y conejo");
		setSize(1600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (backgroundImage != null) {
					g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
				}
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(1600, 400);
			}
		};
		contentPane.setLayout(null);
		setContentPane(contentPane);

		backgroundImage = new ImageIcon("D:\\Archivos\\PROGRAMAS\\Eclipse\\App_MultiHiluu\\sources\\textura-de-pasto-2946.jpg").getImage();

		Image imagen_conejo = new ImageIcon("D:\\Archivos\\PROGRAMAS\\Eclipse\\App_MultiHiluu\\sources\\Conejo-unscreen.gif").getImage();
		ImageIcon iconConejo = new ImageIcon(imagen_conejo.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		conejo = new JLabel();
		conejo.setIcon(iconConejo);
		conejo.setBounds(10, 75, 100, 100);

		Image imagen_tortuga = new ImageIcon("D:\\Archivos\\PROGRAMAS\\Eclipse\\App_MultiHiluu\\sources\\tortuga-unscreen.gif").getImage();
		ImageIcon icontortuga = new ImageIcon(imagen_tortuga.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		tortuga = new JLabel();
		tortuga.setIcon(icontortuga);
		tortuga.setBounds(10, 150, 100, 100);

		posi_tortuga = new JLabel();
		posi_tortuga.setFont(new Font("Tahoma", Font.BOLD, 15));
		posi_tortuga.setBounds(50, 150, 350, 50);
		posi_tortuga.setForeground(Color.WHITE);

		posi_conejo = new JLabel();
		posi_conejo.setFont(new Font("Tahoma", Font.BOLD, 15));
		posi_conejo.setBounds(50, 100, 350, 50);
		posi_conejo.setForeground(Color.WHITE);

		PanelRunnableCompeticionImagen conejoH = new PanelRunnableCompeticionImagen("Conejo", conejo, posi_conejo, false);
		PanelRunnableCompeticionImagen tortugaH = new PanelRunnableCompeticionImagen("Tortuga", tortuga, posi_tortuga, true);
		

		contentPane.add(conejo);
		contentPane.add(posi_conejo);
		contentPane.add(tortuga);
		contentPane.add(posi_tortuga);
	}
}
