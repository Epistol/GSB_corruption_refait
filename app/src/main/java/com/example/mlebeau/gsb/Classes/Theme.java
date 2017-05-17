package com.example.mlebeau.gsb.Classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by mlebeau on 06/04/2017.
 */

public class Theme {

    private int idTheme;
    private String libTheme;

    public Theme(int idTheme, String libTheme) {
        this.idTheme = idTheme;
        this.libTheme = libTheme;
    }

    public Theme(JSONObject jsono) {
        try {
            idTheme = jsono.getInt("idTheme");
            libTheme = jsono.getString("libTheme");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return libTheme;
    }

    public int getIdTheme() {
        return idTheme;
    }

    public String getLibTheme() {
        return libTheme;
    }
}
