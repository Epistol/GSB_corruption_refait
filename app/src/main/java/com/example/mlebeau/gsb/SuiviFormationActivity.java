package com.example.mlebeau.gsb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mlebeau.gsb.Classes.Formation;
import com.example.mlebeau.gsb.Classes.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SuiviFormationActivity extends AppCompatActivity {


    private static final String HEY = "yo";
    private Spinner spin_formations;
    private TextView txt_nbPlaces;
    private ListView list_inscrits;
    private Button btn_localisationInscrits;
    ArrayList<Users> listeVisiteurs = new ArrayList<>();

    protected void SuiviFormationActivity() {
        this.spin_formations = (Spinner)findViewById(R.id.mesFormations_spin_formations);
        this.txt_nbPlaces = (TextView) findViewById(R.id.mesFormations_txt_nbPlaces);
        this.list_inscrits = (ListView)findViewById(R.id.mesFormations_list_inscrits);
        this.btn_localisationInscrits = (Button)findViewById(R.id.mesFormations_btn_localisationsInscrits);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivi_formation);
        SuiviFormationActivity();
        rechercheFormations();

        spin_formations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               /* afficherLesDonnees((Formation)spin_formations.getSelectedItem());*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_localisationInscrits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuiviFormationActivity.this,MapsActivity.class);
                intent.putExtra("lesVisiteurs",listeVisiteurs);
                startActivity(intent);
            }
        });
    }



    private void rechercheFormations(){
        AsyncSoiree asyncGSB = new AsyncSoiree(){
            @Override
            protected void onPostExecute(String s) {
               /* if(!s.equals("-1")){*/
                ArrayList<Formation> listeFormations = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0;i <jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Formation formation = new Formation(jsonObject);
                        listeFormations.add(formation);
                    }
                    spin_formations.setAdapter(new ArrayAdapter<>(SuiviFormationActivity.this, android.R.layout.simple_list_item_1, listeFormations));
                   /* afficherDonnees((Formation)spin_formations.getSelectedItem());*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*} else{
                    Toast.makeText(SuiviFormationActivity.this,"la session à expiré, veuillez vous reconnecter.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SuiviFormationActivity.this,MainActivity.class);
                    startActivity(intent);
                }*/
            }
        };
        asyncGSB.execute("http://10380.sio.jbdelasalle.com/~mlebeau/GHB/index.php?uc=mesFormations");
    }

    // RIEN NE POPULE LES DONNEES ... ABORT MISSION.
    private void afficherLesDonnees(final Formation formation){
        AsyncSoiree asyncGSB = new AsyncSoiree(){
            @Override
            protected void onPostExecute(String s) {
                if(!s.equals("-1")){
                    listeVisiteurs.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0;i <jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Users visiteur = new Users(jsonObject);
                            listeVisiteurs.add(visiteur);
                        }
                        list_inscrits.setAdapter(new ArrayAdapter<Users>(SuiviFormationActivity.this,android.R.layout.simple_list_item_1,listeVisiteurs));
                        txt_nbPlaces.setText("Il reste "+(formation.getNbPlace()-listeVisiteurs.size())+" places sur "+formation.getNbPlace()+" proposées.");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } /*else{
                    Toast.makeText(SuiviFormationActivity.this,"la session à expiré, veuillez vous reconnecter.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SuiviFormationActivity.this,MainActivity.class);
                    startActivity(intent);
                }*/
            }
        };
      /*  asyncGSB.execute("http://10380.sio.jbdelasalle.com/~mlebeau/GHB/index.php?uc=getInscrits&idFormation="+formation.getId());*/
    }
}

