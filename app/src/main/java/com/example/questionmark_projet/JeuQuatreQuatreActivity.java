package com.example.questionmark_projet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.questionmark_projet.classes.Mot;

import java.util.ArrayList;
import java.util.Random;

public class JeuQuatreQuatreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu_quatre_quatre);

        Intent myIntent = getIntent();
        String theme = myIntent.getStringExtra("Theme");    // Récupération du thème
        ArrayList<Mot> mots = new ArrayList<Mot>();                    // Récupération des mots du thème
        ArrayList<Mot> tousLesMots = MainActivity.getLesMots();
        for (int i = 0; i < tousLesMots.size() ; i++ ) {
            if (tousLesMots.get(i).getTheme().equals(theme)) {
                mots.add(tousLesMots.get(i));
            }
        }
        Log.d("MYAPP", "" + mots);

        TextView txtTheme = findViewById(R.id.txtTheme);
        txtTheme.setText(theme); // Affichage du thème

        ArrayList<Button> listeClique = new ArrayList<>();    // Boutons cliqués
        ArrayList<Button> listeCliquable = new ArrayList<>(); // Boutons cliquables
        ArrayList<Button> listeSolution = new ArrayList<>();  // Boutons à cliquer pour gagner les mots croisés

        TableLayout table = findViewById(R.id.table);

        TableRow ligne, ligneprev = null, lignesuiv = null;
        //Mot m = new Mot("lion", "def", 2, "animaux");
        int k = 0;
        for (int i = 0; i < table.getChildCount(); i++) {   // Lignes
            if (i != 0)
                ligneprev = (TableRow) table.getChildAt(i - 1); // Ligne précédente
            if (i != 3)
                lignesuiv = (TableRow) table.getChildAt(i + 1); // Ligne suivante
            ligne = (TableRow) table.getChildAt(i);
            for (int j = 0; j < ligne.getChildCount(); j++) {   // Colonnes
                Button btn = (Button) ligne.getChildAt(j);
                int finalJ = j;
                int finalI = i;
                TableRow finalLigne = ligne;
                TableRow finalLigneprev = ligneprev;
                TableRow finalLignesuiv = lignesuiv;

                btn.setOnClickListener((View.OnClickListener) v -> {
                        if (!listeClique.contains(btn)) {
                            if (listeCliquable.isEmpty()) { // Si liste des éléments cliquables vide
                                listeClique.add(btn); // Initialisation du premier élément
                                btn.setBackgroundColor(Color.BLUE);
                                if (finalJ != 0)    // Si n'est pas tout à gauche
                                    listeCliquable.add((Button) finalLigne.getChildAt(finalJ - 1)); // On peut cliquer à gauche
                                if (finalJ != 3)    // Si n'est pas tout à droite
                                    listeCliquable.add((Button) finalLigne.getChildAt(finalJ + 1));
                                if (finalI != 0)         // Si n'est pas tout en haut
                                    listeCliquable.add((Button) finalLigneprev.getChildAt(finalJ));
                                if (finalI != 3)         // Si n'est pas tout en bas
                                    listeCliquable.add((Button) finalLignesuiv.getChildAt(finalJ));
                            } else if (listeCliquable.contains(btn)) {    // Si la liste des éléments cliquables contient le bouton
                                listeClique.add(btn);
                                btn.setBackgroundColor(Color.BLUE);
                                listeCliquable.clear();

                                // Mise à jour des éléments cliquables
                                if (listeClique.contains(finalLigne.getChildAt(finalJ + 1))) { // Si le bouton est à gauche du précédent élément coloré
                                    if (finalJ != 0) {
                                        if(!listeClique.contains(finalLigne.getChildAt(finalJ - 1)))    // Si bouton pas déjà cliqué (lettre d'un autre mot par ex)
                                            listeCliquable.add((Button) finalLigne.getChildAt(finalJ - 1));
                                    }
                                }
                                else if (listeClique.contains(finalLigne.getChildAt(finalJ - 1))) { // Si le bouton est à droite du précédent élément coloré
                                    if (finalJ != 3) {
                                        if(!listeClique.contains(finalLigne.getChildAt(finalJ + 1)))    // Si bouton pas déjà cliqué (lettre d'un autre mot par ex)
                                            listeCliquable.add((Button) finalLigne.getChildAt(finalJ + 1));
                                    }
                                }
                                else if (finalLigneprev != null) { // Si le bouton est en bas du précédent élément coloré
                                    if (listeClique.contains(finalLigneprev.getChildAt(finalJ))) {
                                        if (finalI != 0) {
                                            if(!listeClique.contains(finalLignesuiv.getChildAt(finalJ)))    // Si bouton pas déjà cliqué (lettre d'un autre mot par ex)
                                                listeCliquable.add((Button) finalLignesuiv.getChildAt(finalJ));
                                        }
                                    }
                                }
                                else if (finalLignesuiv != null) { // Si le bouton est en haut du précédent élément coloré
                                    if (listeClique.contains(finalLignesuiv.getChildAt(finalJ))) {
                                        if (finalI != 0) {
                                            if(!listeClique.contains(finalLigneprev.getChildAt(finalJ)))    // Si bouton pas déjà cliqué (lettre d'un autre mot par ex)
                                                listeCliquable.add((Button) finalLigneprev.getChildAt(finalJ));
                                        }
                                    }
                                }
                            } else {
                                // L'élément n'est pas cliqué, la liste n'est pas vide et ne contient pas le bouton : cul de sac
                            }

                        } else { // Le bouton a déjà été cliqué
                            btn.setBackgroundColor(Color.GRAY); // Réinitialisation de la couleur
                            listeClique.remove(btn);
                            if (listeClique.isEmpty()) {    // S'il n'y avait qu'un seul bouton de cliqué
                                listeCliquable.clear();     // On vide la liste des boutons cliquables
                            } else {  // Sinon, c'est qu'il y en a d'autres
                                if (listeClique.size() == 1) {   // S'il ne reste qu'un seul bouton
                                    listeCliquable.clear();
                                    // On trouve ce bouton et on ajoute à la liste cliquable tous ses voisins
                                    for (int x = 0; x < table.getChildCount(); x++) {
                                        TableRow ligneC = (TableRow) table.getChildAt(x);
                                        TableRow ligneCPrev = null, ligneCSuiv = null;
                                        if (x != 0)
                                            ligneCPrev = (TableRow) table.getChildAt(x - 1); // Ligne précédente
                                        if (x != 3)
                                            ligneCSuiv = (TableRow) table.getChildAt(x + 1); // Ligne suivante

                                        for (int y = 0; y < ligneC.getChildCount(); y++) {
                                            if ((Button) ligneC.getChildAt(y) == listeClique.get(0)) {  // On trouve le bouton
                                                if (y != 0)    // Si n'est pas tout à gauche
                                                    listeCliquable.add((Button) ligneC.getChildAt(y - 1)); // On peut cliquer à gauche
                                                if (y != 3)    // Si n'est pas tout à droite
                                                    listeCliquable.add((Button) ligneC.getChildAt(y + 1));
                                                if (x != 0)         // Si n'est pas tout en haut
                                                    listeCliquable.add((Button) ligneCPrev.getChildAt(y));
                                                if (x != 3)         // Si n'est pas tout en bas
                                                    listeCliquable.add((Button) ligneCSuiv.getChildAt(y));
                                            }
                                        }
                                    }
                                } else {  // Il reste plusieurs boutons
                                    // On ajoute le bouton décliqué dans la liste cliquable, car le sens ne change pas à moins qu'il ne reste qu'un bouton
                                    listeCliquable.add(btn);
                                }
                            }
                        }

                        // Vérification : est-ce que tous les mots ont été trouvés ?
                        if (listeClique.containsAll(listeSolution)) {
                            creerMessage("Terminé", "Vous avez terminé le mot croisé sur le thème des " + theme + ".", "Génial !",
                                    (dialog, id) -> finish()).show();   // Alerte et fin de l'activité, retour au menu principal
                        }
                    /* AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setTitle("EltsCliquables");
                    alertBuilder.setMessage(listeCliquable.toString());
                    alertBuilder.setNeutralButton("OK", null);
                    AlertDialog alert = alertBuilder.create();
                    alert.show(); */
                    });    // Fin onClickListener
            }
        }

        // Initialisation des boutons
        for(Mot m : mots) {
            k = 0;
            if(m.getSens().equals("vertical")) {   // Si mot dans sens vertical
                for (int i = 0; i < table.getChildCount(); i++) {
                    ligne = (TableRow) table.getChildAt(i);
                    for (int j = 0; j < ligne.getChildCount(); j++) {
                        Button btn = (Button) ligne.getChildAt(j);

                        if (j == m.getNumero()) {
                            char l = m.getNom().charAt(k);
                            btn.setText(Character.toString(l));
                            if (!listeSolution.contains(btn))
                                listeSolution.add(btn);
                        } else {
                            Random random = new Random();
                            char rd = (char) ('a' + random.nextInt(26));
                            if (!listeSolution.contains(btn))
                                btn.setText(Character.toString(rd));
                        }
                    } // Fin boucle colonnes
                    k++;
                } // Fin boucle lignes
            } // Fin si vertical
            else {  // Si mot horizontal
                for (int i = 0; i < table.getChildCount(); i++) {
                    ligne = (TableRow) table.getChildAt(i);
                    for (int j = 0; j < ligne.getChildCount(); j++) {
                        Button btn = (Button) ligne.getChildAt(j);
                        if (i == m.getNumero()) {    // Si on est sur la bonne ligne
                            char l = m.getNom().charAt(k);
                            btn.setText(Character.toString(l));
                            if (!listeSolution.contains(btn))
                                listeSolution.add(btn);
                            k++;
                        } else {
                            Random random = new Random();
                            char rd = (char) ('a' + random.nextInt(26));
                            if (!listeSolution.contains(btn))   // Si ce bouton n'est pas celui d'un autre mot
                                btn.setText(Character.toString(rd));
                        }
                    } // Fin boucle colonnes
                } // Fin boucle lignes
            } // Fin si horizontal
        } // Fin boucle mot

        creerMessage("Information", "Lorsqu'une case précédemment cliquée et valide (fait partie d'un mot selon vous) fait partie d'un autre mot," +
                "vous devez double-cliquer sur celle-ci à nouveau si celles d'après refusent de se remplir.", "J'ai compris", null).show();

    } // Fin onCreate()

    // Fonction pour créer une alerte
    private AlertDialog creerMessage(String titre, String message, String txt, DialogInterface.OnClickListener ls) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(titre);
        alertBuilder.setMessage(message);
        alertBuilder.setNeutralButton(txt, ls);
        return alertBuilder.create();
    }
}