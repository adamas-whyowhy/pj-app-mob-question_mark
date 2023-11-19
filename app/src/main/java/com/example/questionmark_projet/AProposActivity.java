package com.example.questionmark_projet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AProposActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_propos);

        TextView txtApropos = findViewById(R.id.txtAPropos);
        String txt = "Question Mark est une application qui comprend plusieurs mots croisés. Les thèmes de ces mots croisés sont variés." +
                "\n\nQuestion Mark est un projet du département informatique de l'IUT de Paris.\n" +
                "Il a été conçu par Chloé Hamilcaro, élève du groupe 201, à partir d'Android Studio.\n" +
                "Le code source est consultable et téléchargeable à l'adresse suivante : ";
        txtApropos.setText(txt);
        txtApropos.setGravity(1); // Centrer le texte verticalement et horizontalement

        Intent urlIntent = new Intent(Intent.ACTION_VIEW);  // Intent pour la redirection
        urlIntent.setData(Uri.parse("https://drive.google.com/drive/folders/1vYz17947GvPrNko4NtophKKRg5y_CDTT"));   // URL de redirection
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);   // Alert pour la redirection

        ImageButton imgDrive = findViewById(R.id.imgDrive);

        imgDrive.setOnClickListener(v -> {
            alertBuilder.setTitle("Google Drive");
            alertBuilder.setMessage("Le dossier va s'ouvrir dans l'application Google Drive. Assurez-vous d'être connecté à un compte Google pour" +
                    " pouvoir y accéder.");
            alertBuilder.setPositiveButton("Compris", (dialog, id) -> startActivity(urlIntent));    // Si compris, ouverture de Google Drive
            alertBuilder.setNeutralButton("Je vais attendre", null);    // Si pas compris, rien
            alertBuilder.create().show();
        });

    }
}