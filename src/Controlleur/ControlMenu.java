package Controlleur;
import Modele.Chrono;
import Vue.Fenetre;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ControlMenu implements ActionListener{
    Fenetre fen;

    public ControlMenu() {}

    public ControlMenu(Fenetre fen) {
        this.fen = fen;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fen.menuItemRecommencer) {                 // recommence la partie avec un memory de même taille
            fen.chrono.terminate();
            fen.chrono.interrupt();
            fen.labelTempsInt.setText("1");
            fen.recommencer();
        } else if (e.getSource() == fen.menuItemScore) {                // ouvre un onglet avec les scores
            String pwd = System.getProperty("user.dir");
            File fichier = new File(pwd + "/src/Modele/score.txt");
            try {
                Scanner sc = new Scanner(fichier);
                fen.score[0] = Double.valueOf(sc.nextLine());
                fen.score[1] = Double.valueOf(sc.nextLine());
                fen.score[2] = Double.valueOf(sc.nextLine());
            } catch (FileNotFoundException exception) {
                System.out.println("Erreur dans l'affichage des scores");
            }
            JOptionPane.showConfirmDialog(fen,"Meilleurs scores:\n\n1:      " + fen.score[0] + "\n2:      " + fen.score[1] + "\n3:      " + fen.score[2] + "\n\n", "Meilleurs scores",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.OK_OPTION, fen.imageScore);

        } else if (e.getSource() == fen.menuTaille3Fois3) {            // change la taille du mémory en 3*3
            fen.changer(3);
        } else if (e.getSource() == fen.menuTaille4Fois4) {            // change la taille du mémory en 4*4
            fen.changer(4);
        } else if (e.getSource() == fen.menuTaille5Fois5) {            // change la taille du mémory en 5*5
            fen.changer(5);
        } else if (e.getSource() == fen.menuItemImpossible) {            // change la taille du mémory en 5*5
            fen.impossible = fen.menuItemImpossible.getState();
            fen.chrono.terminate();
            fen.chrono.interrupt();
            fen.labelTempsInt.setText("1");
            fen.recommencer();
        }
    }
}
