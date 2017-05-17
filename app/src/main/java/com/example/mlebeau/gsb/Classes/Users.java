package com.example.mlebeau.gsb.Classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mlebeau on 04/04/2017.
 */

public class Users {

    private int idUser;
    private String login;
    private String nom;
    private String prenom;
    private String mdp;

    public Users(JSONObject jsonObject) {
        try {
            idUser = jsonObject.getInt("id");
            nom = jsonObject.getString("nom");
            prenom = jsonObject.getString("prenom");
            login = jsonObject.getString("login");
            mdp = jsonObject.getString("mdp");/*
            adresse = jsonObject.getString("adresse");
            cp = jsonObject.getInt("cp");
            ville = jsonObject.getString("ville");
            dateEmbauche = jsonObject.getString("dateEmbauche");
            telephone = jsonObject.getString("telephone");
            dateHeureInscription = jsonObject.getString("dateHeureInscription");*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return prenom+" "+nom/*+" inscrit le "+dateHeureInscription.substring(0,10)*/;
    }

    public int getId() {
        return idUser;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getLogin() {
        return login;
    }

    public String getMdp() {
        return mdp;
    }
/*
    public String getAdresse() {
        return adresse;
    }

    public int getCp() {
        return cp;
    }

    public String getVille() {
        return ville;
    }

    public String getDateEmbauche() {
        return dateEmbauche;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getDateHeureInscription() {
        return dateHeureInscription;
    }*/
}