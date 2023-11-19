package com.example.questionmark_projet.classes;

public class Mot {
    private String nom;
    private String sens;
    private int numero;
    private String theme;

    public Mot() {}

    public Mot(String n, String s, int num, String th) {
        this.nom = n;
        this.sens = s;
        this.numero = num;
        this.theme = th;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getSens() { return sens; }
    public void setSens(String sens) { this.sens = sens; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public String toString() {
        return "Mot : " + this.nom + ", Sens : " + this.sens + ", Numero : " + this.numero + ", Theme : " + this.theme;
    }

}
