package Vue;

import Vue.Fenetre;

public class Memory {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Fenetre f = new Fenetre();
            }
        });

    }
}