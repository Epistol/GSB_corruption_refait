package com.example.mlebeau.gsb.Classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mlebeau on 04/04/2017.
 */


public class Formation implements Serializable {
    private int id;
    private String sujet;
    private String descriptif;
    private int nbPlace;
    private String dateHeureDebut;
    private String dateHeureFin;
    private String adresse;
    private int idTheme;
    private String idVisiteurCreateur;
    private String dateHeureCreation;
    private String theme;

    public Formation(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt("id");
            sujet = jsonObject.getString("sujet");
            descriptif = jsonObject.getString("descriptif");
            nbPlace = jsonObject.getInt("nbPlace");
            dateHeureDebut = jsonObject.getString("dateHeureDebut");
            dateHeureFin = jsonObject.getString("dateHeureFin");
            adresse = jsonObject.getString("adresse");
            idTheme = jsonObject.getInt("idTheme");
            idVisiteurCreateur = jsonObject.getString("idVisiteurCreateur");
            dateHeureCreation = jsonObject.getString("dateHeureCreation");
            theme = jsonObject.getString("theme");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return sujet;
    }

    public int getId() {
        return id;
    }

    public String getSujet() {
        return sujet;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public String getDateHeureDebut() {
        return dateHeureDebut;
    }

    public String getDateHeureFin() {
        return dateHeureFin;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getIdTheme() {
        return idTheme;
    }

    public String getIdVisiteurCreateur() {
        return idVisiteurCreateur;
    }

    public String getDateHeureCreation() {
        return dateHeureCreation;
    }

    public String getTheme() {
        return theme;
    }
}

