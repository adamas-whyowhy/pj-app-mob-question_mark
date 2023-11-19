package com.example.questionmark_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.questionmark_projet.classes.Jeu;
import com.example.questionmark_projet.classes.Mot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("motcroise");   // Base
    DatabaseReference myRefJeu = myRef.child("jeu");    // Entité de la base contenant les jeux (thèmes)
    DatabaseReference myRefMot = myRef.child("mot");    // Entité de la base contenant les mots (nom, ...)

    public static ArrayList<Jeu> lesJeux = new ArrayList<>();
    public static ArrayList<Mot> lesMots = new ArrayList<>();

    private Button btnAPropos;
    private Button btnJouer;
    private Button btnParametres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAPropos = findViewById(R.id.btnAPropos);
        btnJouer = findViewById(R.id.btnJouer);
        btnParametres = findViewById(R.id.btnParametres);

        myRefJeu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Un jeu est une HashMap<"theme", valeurdutheme>
                // Récupération de la liste des jeux
                GenericTypeIndicator<ArrayList<HashMap<String, String>>> t = new GenericTypeIndicator<ArrayList<HashMap<String, String>>>() {};
                ArrayList<HashMap<String, String>> jeux = dataSnapshot.getValue(t);
                for (HashMap j : jeux) {
                    lesJeux.add(new Jeu(j.get("theme").toString()));
                }

                Log.d("MYAPP", "Les jeux sont " + lesJeux );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("MYAPP", "Failed to read value.", error.toException());
            }
        });

        myRefMot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Un mot est une HashMap<"theme", valeurdutheme>
                // Récupération de la liste des jeux
                GenericTypeIndicator<ArrayList<HashMap<String, String>>> t = new GenericTypeIndicator<ArrayList<HashMap<String, String>>>() {};
                ArrayList<HashMap<String, String>> mots = dataSnapshot.getValue(t);
                for (HashMap m : mots) {
                    lesMots.add(new Mot(m.get("nom").toString(), m.get("sens").toString(), Integer.parseInt(m.get("numero").toString()), m.get("theme").toString()));
                }

                Log.d("MYAPP", "Les mots sont " + lesMots );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("MYAPP", "Failed to read value.", error.toException());
            }
        });

        final Intent intentAPropos = new Intent(this, AProposActivity.class);
        btnAPropos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { startActivity(intentAPropos); }
        });

        final Intent intentJeu = new Intent(this, SelectionActivity.class);
        btnJouer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { startActivity(intentJeu); }
        });

        btnParametres.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

    public static ArrayList<Jeu> getLesJeux() { return lesJeux; }
    public static ArrayList<Mot> getLesMots() { return lesMots; }
}