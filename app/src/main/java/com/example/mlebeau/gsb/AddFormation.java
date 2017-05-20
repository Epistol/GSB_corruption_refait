package com.example.mlebeau.gsb;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.DatePicker;

import com.example.mlebeau.gsb.Classes.Theme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddFormation extends AppCompatActivity {

    private Spinner dropdown;
    private TextView datedebtu_txt;
    private TextView datefin_txt;
    private Button debut;
    private Button fin;
    private Button valider;
    private TextView sujet;
    private TextView nbPlace;
    private TextView desc;
    private Spinner theme;
    private TextView adresse;
    private TextView nom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_formation);
        dropdown  = (Spinner)findViewById(R.id.spinner_ajout);
        this.datedebtu_txt = (TextView) findViewById(R.id.datedebtu_txt);
        this.datefin_txt = (TextView) findViewById(R.id.datefin_txt);
        this.debut = (Button) findViewById(R.id.debut);
        this.fin = (Button) findViewById(R.id.fin);
        this.valider = (Button) findViewById(R.id.ajout);
        this.sujet = (TextView) findViewById(R.id.sujet);
        this.desc = (TextView) findViewById(R.id.desc);
        this.theme = (Spinner) findViewById(R.id.spinner_ajout);
        this.adresse = (TextView) findViewById(R.id.adresse);
        this.nom = (TextView) findViewById(R.id.nomFormation);


        rechercheThemes();

        valider.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ajouterFormation();

            }
        });



        final boolean[] clicked = {false};

        debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //change boolean value
                clicked[0] = true;
                Log.v(clicked.toString(), "index=" + clicked[0]);
            }
        });



        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener dateListenerDebut = new DatePickerDialog.OnDateSetListener() {

            private void updateLabel() {

                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                datedebtu_txt.setText(sdf.format(myCalendar.getTime()));
            }


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }
        };
        final DatePickerDialog.OnDateSetListener dateListenerFin = new DatePickerDialog.OnDateSetListener() {

            private void updateLabel() {
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                datefin_txt.setText(sdf.format(myCalendar.getTime()));
            }


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }
        };


        debut.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(AddFormation.this, dateListenerDebut, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                    }


                });

        fin.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(AddFormation.this, dateListenerFin, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();



                    }


                });


        // LA RAYMOND BARRE DE CHOIX
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        this.nbPlace = (TextView)findViewById(R.id.SeekBarValue);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                nbPlace.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });



    }


    private void
    rechercheThemes(){
        AsyncSoiree asyncGsb = new AsyncSoiree(){
            @Override
            protected void onPostExecute(String s){
                ArrayList<Theme> lesThemes = new ArrayList<>();
                lesThemes.add(new Theme(0,"Thème de la formation"));
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0;i <jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Theme theme = new Theme(jsonObject);
                        lesThemes.add(theme);
                    }
                    dropdown.setAdapter(new ArrayAdapter<Theme>(AddFormation.this,android.R.layout.simple_list_item_1,lesThemes));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        asyncGsb.execute("http://10380.sio.jbdelasalle.com/~mlebeau/GHB/index.php?uc=getTheme");
    }


    private void ajouterFormation(){
        AsyncSoiree asyncTaskGSB = new AsyncSoiree(){
            @Override
            protected void onPostExecute(String s){


            }
        };

        try {
            asyncTaskGSB.execute("http://10380.sio.jbdelasalle.com/~mlebeau/GHB/index.php?uc=addFormation&theme=" + ((Theme)theme.getSelectedItem()).getIdTheme()  +
                    "&nbPlaces=" + String.valueOf(nbPlace.getText())
                    + "&descriptif=" + URLEncoder.encode(desc.getText().toString(),"utf-8") +
                    "&dateDebut=" + datedebtu_txt.getText().toString().replace("/","-") +
                    "&dateFin=" + datefin_txt.getText().toString().replace("/","-")+
                    "&adresse=" + URLEncoder.encode(adresse.getText().toString(),"utf-8")  +
                    "&nom=" + URLEncoder.encode(nom.getText().toString(),"utf-8") );

            Log.wtf("FUCK", ("http://10380.sio.jbdelasalle.com/~mlebeau/GHB/index.php?uc=addFormation&theme=" + ((Theme)theme.getSelectedItem()).getIdTheme()  +
                    "&nbPlaces=" + String.valueOf(nbPlace.getText())
                    + "&descriptif=" + URLEncoder.encode(desc.getText().toString(),"utf-8") +
                    "&dateDebut=" + datedebtu_txt.getText().toString().replace("/","-") +
                    "&dateFin=" + datefin_txt.getText().toString().replace("/","-")+
                    "&adresse=" + URLEncoder.encode(adresse.getText().toString(),"utf-8")  +
                    "&nom=" + URLEncoder.encode(nom.getText().toString(),"utf-8") )   );


            Intent pickContactIntent = new Intent(AddFormation.this,SuiviFormationActivity.class);
            startActivityForResult(pickContactIntent, 1);

        }  catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }





}
