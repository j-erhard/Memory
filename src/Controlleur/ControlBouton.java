package Controlleur;
import Modele.Chrono;
import Vue.Fenetre;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class ControlBouton implements ActionListener{
    Fenetre fen;

    public ControlBouton() {}

    public ControlBouton(Fenetre fen) {
        this.fen = fen;
    }

    public void actionPerformed(ActionEvent e) {
        if (!fen.chrono.demarre) {                                        // Si chronomètre n'est pas démarré
            fen.chrono = new Chrono(fen.labelTempsInt);
            fen.chrono.start();
            for (int i=0; i<fen.nbCaseMemory; i++) {
                if (e.getSource() == fen.boutons[i]) {
                    fen.boutons[i].setEnabled(false);
                    fen.nbCarteRetournee ++;
                    fen.numBoutonRetournee1 = i;
                }
            }
        } else if (fen.nbCarteRetournee == 0) {                           // Si nbcarteRetournee actuel est égale à 0
            for (int i=0; i<fen.nbCaseMemory; i++) {
                if (e.getSource() == fen.boutons[i]) {
                    fen.boutons[i].setEnabled(false);
                    fen.nbCarteRetournee ++;
                    fen.numBoutonRetournee1 = i;
                }
            }
        }else if (fen.nbCarteRetournee == 1) {                             // Si nbcarteRetournee actuel est égale à 1
            for (int i=0; i< fen.nbCaseMemory; i++) {
                if (e.getSource() == fen.boutons[i]) {
                    fen.boutons[i].setEnabled(false);
                    fen.nbCarteRetournee ++;
                    fen.numBoutonRetournee2 = i;
                }
            }
            //compare les deux cartes retournées
            if (fen.boutons[fen.numBoutonRetournee1].getDisabledIcon() == fen.boutons[fen.numBoutonRetournee2].getDisabledIcon()) {
                // si elle sont égale, il faut les laisser désactiver: donc nbCarteRetournee=0
                fen.nbCarteRetournee = 0;
            } else {
                //sinon enlever des vies
                fen.nbVie --;
                fen.labelVie.setText("Nombre de vies restantes: " + fen.nbVie);
            }
        } else if (fen.nbCarteRetournee == 2) {                          // Si nbcarteRetournee actuel est égale à 2 (donc les deux cartes déjà retournées ne sont pas identiques)
            fen.nbCarteRetournee = 0;
            // il faut mettre les 2 cartes faces caché car elles sont différentes
            fen.boutons[fen.numBoutonRetournee1].setEnabled(true);
            fen.boutons[fen.numBoutonRetournee2].setEnabled(true);
            // retourne la carte cliqué
            for (int i=0; i< fen.nbCaseMemory; i++) {
                if (e.getSource() == fen.boutons[i]) {
                    fen.boutons[i].setEnabled(false);
                    fen.nbCarteRetournee ++;
                    fen.numBoutonRetournee1 = i;
                }
            }
            fen.nbCarteRetournee = 1;
        }
        if (fen.nbVie < 0) {                    //si on a plus de vie
            //mettre nbVie à 0 pour ne pas avoir un nobre négatif
            fen.nbVie = 0;
            fen.labelVie.setText("Nombre de vies restantes: " + fen.nbVie);
            fen.chrono.stop();
            fen.nbCarteRetournee = 3;
            // afficher un message de défaite
            JOptionPane.showConfirmDialog(fen,"Dommage, tu as perdu", "Perdu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.OK_OPTION, fen.imagePerdu);
        }else if (aGagne()) {
            fen.chrono.stop();
            JOptionPane.showConfirmDialog(fen,"Tu réussi ce memory en " + fen.labelTempsInt.getText() + " secondes !", "Felicitation",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.OK_OPTION, fen.imageScore);
            miseAJourScore();
        }
    }

    /**
     * Prend le score du joueur lorsqu'il a gagné et l'inscrit dans le fichier texte
     */
    private void miseAJourScore() {
        String pwd = System.getProperty("user.dir");
        File fichier = new File(pwd + "/src/Modele/score.txt");
        try {
            Scanner sc = new Scanner(fichier);
            fen.score[0] = Double.valueOf(sc.nextLine());
            fen.score[1] = Double.valueOf(sc.nextLine());
            fen.score[2] = Double.valueOf(sc.nextLine());
        } catch (FileNotFoundException exception) {
            System.out.println("Erreur dans la prise des scores");
        }
        fen.score[3] = Double.valueOf(fen.labelTempsInt.getText().replace(',', '.'));
        // methode de tri pour le score
        if (fen.score[3] < fen.score[0]) {
            fen.score[2] = fen.score[1];
            fen.score[1] = fen.score[0];
            fen.score[0] = fen.score[3];
        } else if (fen.score[3] < fen.score[1]) {
            fen.score[2] = fen.score[1];
            fen.score[1] = fen.score[3];
        } else if (fen.score[3] < fen.score[2]) {
            fen.score[2] = fen.score[3];
        }
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichier));
            bw.write(""+fen.score[0]);
            bw.newLine();
            bw.write(""+fen.score[1]);
            bw.newLine();
            bw.write(""+fen.score[2]);
            bw.close();
        } catch(Exception e) {
            System.out.println("Erreur lors de l'écriture des scrores");
        }
    }

    /**
     * Renvoi true si le joueur a gagné la partie
     * @return
     */
    private boolean aGagne() {
        for (int i=0; i< fen.nbCaseMemory; i++) {
            if (fen.boutons[i].isEnabled() && !fen.boutons[i].getDisabledIcon().equals("src/Modele/imageRouge.jpg")) {
                return false;
            }
        }
        return true;
    }
}