package cuentoliebretortuga;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class CarreraTortugaConejo extends JFrame {

    private CarreraPanel carreraPanel;

    public CarreraTortugaConejo() {
        carreraPanel = new CarreraPanel();
        getContentPane().add(carreraPanel);
        
        JLabel lblNewLabel = new JLabel("New label");
        carreraPanel.add(lblNewLabel);
        setTitle("Carrera de la Tortuga y el Conejo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500); 
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CarreraTortugaConejo().iniciarCarrera();
        });
    }

    public void iniciarCarrera() {
        Thread hareThread = new Thread(carreraPanel.hare);
        Thread tortoiseThread = new Thread(carreraPanel.tortoise);

        hareThread.start();
        tortoiseThread.start();
    }
}

class CarreraPanel extends JPanel implements Runnable {

    private static final long serialVersionUID = 1L;
    protected Hare hare;
    protected Tortoise tortoise;
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private String ganador;

    public CarreraPanel() {
        PANEL_WIDTH = 1000; 
        PANEL_HEIGHT = 500; 
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        hare = new Hare();
        tortoise = new Tortoise();
        ganador = "";
    }

    @Override
    public void run() {
        Thread hareThread = new Thread(hare);
        Thread tortoiseThread = new Thread(tortoise);
        hareThread.start();
        tortoiseThread.start();
        try {
            hareThread.join();
            tortoiseThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        hare.draw(g, this); 
        tortoise.draw(g, this); 
        if (!ganador.isEmpty()) {
            g.drawString(ganador, PANEL_WIDTH / 2, PANEL_HEIGHT / 2);
        }
    }

    private abstract class Racer implements Runnable {
        protected int x;
        protected int y;
        protected int speed;
        protected boolean running;
        protected Image image;
        protected int width;
        protected int height;

        public Racer(String imagePath, int initialY, int speed, int width, int height) {
            this.x = 0;
            this.y = initialY;
            this.speed = speed;
            ImageIcon icon = new ImageIcon(imagePath);
            this.image = icon.getImage(); 
            this.width = width; 
            this.height = height; 
            this.running = true;
        }

        public void stopRunning() {
            running = false;
        }

        protected abstract void raceBehavior();

        @Override
        public void run() {
            while (running) {
                raceBehavior();
                move();
                repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            verificarGanador();
        }

        protected void move() {
            x += speed;
            if (x >= PANEL_WIDTH - width) {
                x = PANEL_WIDTH - width;
                stopRunning();
            }
        }

        public void draw(Graphics g, CarreraPanel panel) {
            g.drawImage(image, x, y, width, height, panel);
        }

        protected synchronized void verificarGanador() {
            if (ganador.isEmpty()) {
                if (x >= PANEL_WIDTH - width) {
                    if (this instanceof Hare) {
                        ganador = "¡La liebre ha ganado!";
                    } else if (this instanceof Tortoise) {
                        ganador = "¡La tortuga ha ganado!";
                    }
                    repaint();
                }
            }
        }
    }

    private class Hare extends Racer {
        private boolean distracted;

        public Hare() {
            super("D:\\Archivos\\Descargas\\Conejo-unscreen.gif", PANEL_HEIGHT / 2 - 50, 10, 100, 100); 
            distracted = false;
        }

        @Override
        protected void raceBehavior() {
            if (x > PANEL_WIDTH / 2 && !distracted) {
                distracted = true;
                try {
                    Thread.sleep(6000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                speed = 2;
            }
        }
    }

    private class Tortoise extends Racer {

        public Tortoise() {
            super("D:\\Archivos\\Descargas\\tortuga-unscreen.gif", PANEL_HEIGHT / 2 + 20, 10, 100, 100); 
        }

        @Override
        protected void raceBehavior() {
            // No behavior change needed for tortoise
        }

        @Override
        protected void move() {
            if (!hare.running || x < PANEL_WIDTH / 2) { 
                speed = 3; 
            } else {
                speed = 1; 
            }
            super.move();
        }
    }
}
