package com.example.mlebeau.gsb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mlebeau.gsb.Classes.Formation;
import com.example.mlebeau.gsb.Classes.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RechercherFormation extends AppCompatActivity {


    private EditText txt_motCle;
    private Button btn_filtrer;
    private Spinner spin_lesFormations;
    private TextView txt_descriptif;
    private TextView txt_nbPlaces;
    private Button btn_localiserFormationSelect;
    private Button btn_localiserToutesFormationsDisponibles;
    private Button btn_inscriptionFormation;
    private ArrayList<Formation> listeFormations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher_formation);

        txt_motCle = (EditText) findViewById(R.id.rechercheForm_txt_motCle);
        btn_filtrer = (Button) findViewById(R.id.filtrer_btn);
        txt_descriptif = (TextView) findViewById(R.id.rechercheForm_txt_desc);
        txt_nbPlaces = (TextView) findViewById(R.id.nb_place);
        spin_lesFormations = (Spinner) findViewById(R.id.rechercheForm_spin_lesFormations);
        btn_localiserFormationSelect = (Button) findViewById(R.id.lie_form_btn);
        btn_localiserToutesFormationsDisponibles = (Button) findViewById(R.id.loca_form_dispo_btn);
        btn_inscriptionFormation = (Button) findViewById(R.id.inscription_form_btn);

        // INITIALISATION DU SPINNER
        changerLeSpinner("");
        spin_lesFormations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                afficherDonnees((Formation) spin_lesFormations.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_localiserToutesFormationsDisponibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RechercherFormation.this, MapsActivity.class);
                intent.putExtra("lesFormations", listeFormations);
                startActivity(intent);
            }
        });

        btn_localiserFormationSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Formation> listeUneFormation = new ArrayList<>();
                listeUneFormation.add((Formation) spin_lesFormations.getSelectedItem());
                Intent intent = new Intent(RechercherFormation.this, MapsActivity.class);
                intent.putExtra("lesFormations", listeUneFormation);
                startActivity(intent);
            }
        });

        btn_filtrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changerLeSpinner(txt_motCle.getText().toString());
            }
        });

        btn_inscriptionFormation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncSoiree asyncGSB = new AsyncSoiree() {
                    @Override
                    protected void onPostExecute(String s) {
                        if (!s.equals("-1")) {
                            Toast.makeText(RechercherFormation.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RechercherFormation.this, "La session gadouille, reconnexion ...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RechercherFormation.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                };
                Formation formationSelect = (Formation) spin_lesFormations.getSelectedItem();
                asyncGSB.execute("http://10380.sio.jbdelasalle.com/~mlebeau/GHB/index.php?uc=inscription&idFormation=" + formationSelect.getId());
            }
        });
    }

    private void afficherDonnees(final Formation formation) {

        AsyncSoiree asyncGSB = new AsyncSoiree() {
            @Override
            protected void onPostExecute(String s) {

                ArrayList<Users> listeVisiteurs = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Users visiteur = new Users(jsonObject);
                        listeVisiteurs.add(visiteur);
                    }
                    txt_descriptif.setText(formation.getDescriptif());
                    txt_nbPlaces.setText("Il reste " + (formation.getNbPlace() - listeVisiteurs.size()) + " places sur " + formation.getNbPlace() + " proposées.");
                    btn_inscriptionFormation.setText("M'inscrire à " + (Formation) spin_lesFormations.getSelectedItem());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        asyncGSB.execute("http://10380.sio.jbdelasalle.com/~mlebeau/GHB/index.php?uc=getInscrits&idFormation=" + formation.getId());
    }

    private void changerLeSpinner(String motCle) {
        AsyncSoiree asyncGSB = new AsyncSoiree() {
            @Override
            protected void onPostExecute(String s) {

                    listeFormations.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Formation formation = new Formation(jsonObject);
                            listeFormations.add(formation);
                        }
                        spin_lesFormations.setAdapter(new ArrayAdapter<Formation>(RechercherFormation.this, android.R.layout.simple_list_item_1, listeFormations));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        };
        asyncGSB.execute("http://10380.sio.jbdelasalle.com/~mlebeau/GHB/index.php?uc=rechercheFormation&motCle=" + motCle);
    }


}
