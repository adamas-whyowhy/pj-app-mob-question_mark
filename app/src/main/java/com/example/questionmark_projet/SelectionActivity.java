package com.example.questionmark_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.questionmark_projet.classes.Jeu;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Intent intent = new Intent(this, JeuQuatreQuatreActivity.class);
        LinearLayout ly = findViewById(R.id.lySelection);
        ArrayList<Jeu> lesJeux = MainActivity.getLesJeux();
        int nbJeux = lesJeux.size();    // Nombre de jeux (thèmes) possibles

        // Génération dynamique des boutons en fonction des données de la base
        for(int i = 0; i < nbJeux; i++){
            Button new_btn = new Button(this);
            new_btn.setText(lesJeux.get(i).getTheme());
            ly.addView(new_btn);

            new_btn.setOnClickListener(new View.OnClickListener()
            { public void onClick(View v) {
                intent.putExtra("Theme", new_btn.getText());    // On informe la page suivante du thème choisi
                startActivity(intent); finish();
            }
            });
        }
    }
}