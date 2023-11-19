package com.example.questionmark_projet.classes;

public class Jeu {
    private String theme;

    public Jeu() {}

    public Jeu(String t) {
        this.theme = t;
    }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public String toString() {
        return theme;
    }
}
