package Vue;

import Controlleur.ControlBouton;
import Controlleur.ControlMenu;
import Modele.Chrono;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {
    // Création des variables:
    //int
    public int tailleMemory;
    public int nbCaseMemory;
    public int nbVie;
    public int nbCarteRetournee;
    public int numBoutonRetournee1;
    public int numBoutonRetournee2;
    //String
    public Double[] score;
    //Panel
    JPanel panelTotal;
    JPanel panelTemps;
    JPanel panelPlateau;
    public JPanel panelVie;
    JPanel[] panelBouton;
    //Button
    public JButton[] boutons;
    //ImageIcon
    ImageIcon imageCarteDos;
    ImageIcon imagePiege;
    ImageIcon[] images;
    public ImageIcon imageScore;
    public ImageIcon imagePerdu;
    //Menu et menuItem
    JMenuBar menuBar;
    JMenu menuOptions;
    JMenu menuTaille;
    public JMenuItem menuTaille3Fois3;
    public JMenuItem menuTaille4Fois4;
    public JMenuItem menuTaille5Fois5;
    public JMenuItem menuItemRecommencer;
    public JMenuItem menuItemScore;
    //Label
    JLabel labelTemps;
    public JLabel labelTempsInt;
    public JLabel  labelVie;
    //Controller
    public ControlBouton controlBouton;
    public ControlMenu controlMenu;
    //Chrono
    public Chrono chrono;

    public Fenetre() {
        // initialise les attributs
        initAtribut();
        //créer un mémory de taille 4
        nFoisN(4);

        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initialise la plupart des attributs
     */
    private void initAtribut() {
        //int
        nbCarteRetournee = 0;
        //String
        score = new Double[4];
        //Panel
        panelTotal = new JPanel();
        panelTotal.setLayout(new BoxLayout(panelTotal, BoxLayout.Y_AXIS));
        panelTemps = new JPanel();
        panelPlateau = new JPanel();
        panelVie = new JPanel();
        panelBouton = new JPanel[25];
        //Button
        boutons = new JButton[25];
        //ImageIcon
        imageCarteDos = new ImageIcon("src/Modele/imageDos.jpg");
        imagePiege = new ImageIcon("src/Modele/imageRouge.jpg");
        imageScore = new ImageIcon("src/Modele/score.jpg");
        imagePerdu = new ImageIcon("src/Modele/perdu.jpg");
        images = new ImageIcon[13];
        images[0] = new ImageIcon("src/Modele/image1.jpg");
        images[1] = new ImageIcon("src/Modele/image2.jpg");
        images[2] = new ImageIcon("src/Modele/image3.jpg");
        images[3] = new ImageIcon("src/Modele/image4.jpg");
        images[4] = new ImageIcon("src/Modele/image5.jpg");
        images[5] = new ImageIcon("src/Modele/image6.jpg");
        images[6] = new ImageIcon("src/Modele/image7.jpg");
        images[7] = new ImageIcon("src/Modele/image8.jpg");
        images[8] = new ImageIcon("src/Modele/image9.jpg");
        images[9] = new ImageIcon("src/Modele/image10.jpg");
        images[10] = new ImageIcon("src/Modele/image11.jpg");
        images[11] = new ImageIcon("src/Modele/image12.jpg");
        images[12] = new ImageIcon("src/Modele/image13.jpg");
        //Menu et MenuItem
        menuBar = new JMenuBar();
        menuOptions = new JMenu("Options");
        menuTaille = new JMenu("Taille");
        menuTaille3Fois3 = new JMenuItem("3 * 3");
        menuTaille4Fois4 = new JMenuItem("4 * 4");
        menuTaille5Fois5 = new JMenuItem("5 * 5");
        menuItemRecommencer = new JMenuItem("Recommencer");
        menuItemScore = new JMenuItem("Meilleur score");
        //Label
        labelTemps = new JLabel("Temps: ");
        labelTempsInt = new JLabel("0");
        //Chrono
        chrono = new Chrono(labelTempsInt);
    }

    /**
     * Création du mémory de n taille
     * @param n     taille du mémory
     */
    private void nFoisN(int n){
        // initialisation des variables non initialisable avant
        //int
        tailleMemory = n;
        nbCaseMemory = tailleMemory*tailleMemory;
        nbVie = tailleMemory*2;
        // mise à jour du label vie
        labelVie = new JLabel("Nombre de vies restantes: " + nbVie);
        // mise en place des panels: temps et vie
        panelTemps.add(labelTemps);
        panelTemps.add(labelTempsInt);
        panelVie.add(labelVie);
        // création des boutons et panels
        for (int i=0; i<nbCaseMemory; i++) {
            // création des boutons
            boutons[i] = new JButton();
            boutons[i].setIcon(imageCarteDos);
            boutons[i].setDisabledIcon(images[i/2]);
            boutons[i].setPreferredSize(new Dimension(150, 150));
            boutons[i].setEnabled(true);
            //création des panels pour insérer les boutons
            panelBouton[i] = new JPanel();
        }
        //création du bouton piège si le mémory a un nombre impaire de cases
        if (nbCaseMemory%2 == 1) {
            boutons[nbCaseMemory-1].setDisabledIcon(imagePiege);
        }
        // création d'une grilleLayout de la taille du memory
        panelPlateau.setLayout(new GridLayout(tailleMemory,tailleMemory));
        // mélange les boutons du mémory
        boutons = melangeTab(boutons, nbCaseMemory);
        // ajouts des boutons dans les panels puis des panels dans panelPlateau
        for (int i=0; i<nbCaseMemory; i++) {
            panelBouton[i].add(boutons[i]);
            panelPlateau.add(panelBouton[i]);
        }
        // ajouts des panels temps, plateau et vie dans un panel
        panelTotal.add(panelTemps);
        panelTotal.add(panelPlateau);
        panelTotal.add(panelVie);
        // ajout du menu
        setJMenuBar(creerMenu());
        setContentPane(panelTotal);
        // mise en place du controleur
        controlBouton = new ControlBouton(this);
        // pour chaque boutons, ajouter un ActionListener
        for (int i=0; i<nbCaseMemory; i++) {
            boutons[i].addActionListener(controlBouton);
        }
    }

    /**
     * Crée le menu du mémory
     * @return
     */
    private JMenuBar creerMenu() {
        // création du menu
        menuTaille.add(menuTaille3Fois3);
        menuTaille.add(menuTaille4Fois4);
        menuTaille.add(menuTaille5Fois5);
        menuOptions.add(menuItemRecommencer);
        menuOptions.add(menuItemScore);
        menuOptions.addSeparator();
        menuOptions.add(menuTaille);
        menuBar.add(menuOptions);
        // ajout des menuItem au menuControlleur
        controlMenu = new ControlMenu(this);
        menuTaille3Fois3.addActionListener(controlMenu);
        menuTaille4Fois4.addActionListener(controlMenu);
        menuTaille5Fois5.addActionListener(controlMenu);
        menuItemRecommencer.addActionListener(controlMenu);
        menuItemScore.addActionListener(controlMenu);
        //retourne le menu
        return menuBar;
    }

    /**
     * Mélange les boutons du mémory
     * @param boutons
     * @param nbCase
     * @return
     */
    private JButton[] melangeTab(JButton[] boutons, int nbCase) {
        JButton jButtonTampon = new JButton();
        int nbAleatoire;
        for (int i=0; i<nbCase; i++) {
            for (int j=0; j<nbCase; j++) {
                nbAleatoire = (int) (Math.random()*nbCase);
                jButtonTampon = boutons[i];
                boutons[i] = boutons[nbAleatoire];
                boutons[nbAleatoire] = jButtonTampon;
            }
        }
        return boutons;
    }

    /**
     * Recommence le mémory avec la même taille
     */
    public void recommencer() {
        initAtribut();
        nFoisN(tailleMemory);
    }

    /**
     * Recommence le mémory avec une taille n
     * @param n    taille du mémory
     */
    public void changer(int n) {
        initAtribut();
        nFoisN(n);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}