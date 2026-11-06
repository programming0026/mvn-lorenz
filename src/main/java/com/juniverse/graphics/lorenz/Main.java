package com.juniverse.graphics.lorenz;

import javax.swing.SwingUtilities;

/**
 * Abre la ventana con el atractor de Lorenz.
 *
 * @author Java Universe
 */
public class Main {

    /**
     * Punto de entrada.
     * @param args no se usan
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> Lorenz.createWin("Lorenz", 640, 480));
    }
}
