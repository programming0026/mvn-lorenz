package com.juniverse.graphics.lorenz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Dibuja el atractor de Lorenz (σ = 10, ρ = 28, β = 8/3) integrando sus ecuaciones con el método de Euler.
 * Se trazan tres trayectorias que empiezan casi en el mismo punto y terminan separándose (efecto mariposa).
 *
 * @author linux
 */
public class Lorenz extends JPanel {

    private static final int MAX_ITER = 50000;
    private final double xmin;
    private final double xmax;
    private final double ymin;
    private final double ymax;

    private double x, y, z;
    private int width;
    private int height;

    /** Define la región del plano (x en [-25, 25], z en [0, 50]) que se mostrará. */
    public Lorenz() {
        this.xmin = -25;
        this.xmax = 25;
        this.ymin = 0;
        this.ymax = 50;
        this.width = getWidth();
        this.height = getHeight();
    }

    /**
     * Avanza la posición un paso de tiempo con el método de Euler.
     * @param dt tamaño del paso
     */
    private void update(double dt) {
        double xnew = x + dx(x, y, z) * dt;
        double ynew = y + dy(x, y, z) * dt;
        double znew = z + dz(x, y, z) * dt;
        x = xnew;
        y = ynew;
        z = znew;
    }

    /** Limpia el panel y dibuja las tres trayectorias. */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        clearDevice(g2d);
        draw(g2d);
    }

    /**
     * Dibuja tres trayectorias con condiciones iniciales casi idénticas (y = 20.00, 20.01 y 20.02).
     * @param g2d contexto gráfico
     */
    public void draw(Graphics2D g2d) {
        lorenz(g2d, 0.0, 20.00, 25.0, Color.YELLOW);
        lorenz(g2d, 0.0, 20.01, 25.0, Color.BLUE);
        lorenz(g2d, 0.0, 20.02, 25.0, Color.RED);

    }

    /**
     * Calcula y pinta una trayectoria de {@code MAX_ITER} pasos.
     * @param g2d contexto gráfico
     * @param x1 x inicial
     * @param y1 y inicial
     * @param z1 z inicial
     * @param color color de la trayectoria
     */
    private void lorenz(Graphics2D g2d, double x1, double y1, double z1, Color color) {
        this.width = getWidth();
        this.height = getHeight();

        double dt = 0.001;
        this.x = x1;
        this.y = y1;
        this.z = z1;
       
        for (int i = 0; i < MAX_ITER; i++) {
            update(dt);
            setPixel(g2d, x, z, color);
        }
    }

    /** dx/dt = σ·(y − x), con σ = 10. */
    private double dx(double x, double y, double z) {
        return -10 * (x - y);
    }

    /** dy/dt = x·(ρ − z) − y, con ρ = 28. */
    private double dy(double x, double y, double z) {
        return -x * z + 28 * x - y;
    }

    /** dz/dt = x·y − β·z, con β = 8/3. */
    private double dz(double x, double y, double z) {
        return x * y - 8 * z / 3;
    }

    /**
     * Pinta un punto convirtiendo las coordenadas del modelo a las del panel.
     * @param g2d contexto gráfico
     * @param x coordenada horizontal del modelo
     * @param y coordenada vertical del modelo
     * @param color color del punto
     */
    private void setPixel(Graphics2D g2d, double x, double y, Color color) {
        double xs = width * (x - xmin) / (xmax - xmin);
        double ys = height * (ymax - y) / (ymax - ymin);
        float scaledPenRadius = (float) (0.003 * 512);
        g2d.setColor(color);
        g2d.fill(new Ellipse2D.Double(xs - scaledPenRadius / 2, ys - scaledPenRadius / 2,
                scaledPenRadius, scaledPenRadius));
    }

    /** Rellena el panel de negro. */
    private void clearDevice(Graphics2D g2d) {
        g2d.setBackground(Color.BLACK);
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Crea y muestra la ventana.
     * @param title título
     * @param width ancho en píxeles
     * @param height alto en píxeles
     */
    public static void createWin(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(width, height);
        frame.setResizable(true);
        frame.add(new Lorenz());
        frame.setVisible(true);
        setLocationCenter(frame);
    }

    /** Centra la ventana en la pantalla. */
    private static void setLocationCenter(JFrame frame) {
        setLocationMove(frame, 0, 0);
    }

    /**
     * Centra la ventana y la desplaza el valor indicado.
     * @param frame ventana
     * @param moveWidth desplazamiento horizontal
     * @param moveHeight desplazamiento vertical
     */
    private static void setLocationMove(JFrame frame, int moveWidth, int moveHeight) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        frameSize.width = frameSize.width > screenSize.width ? screenSize.width : frameSize.width;
        frameSize.height = frameSize.height > screenSize.height ? screenSize.height : frameSize.height;
        frame.setLocation((screenSize.width - frameSize.width) / 2 + moveWidth, (screenSize.height - frameSize.height) / 2 + moveHeight);
    }
}
