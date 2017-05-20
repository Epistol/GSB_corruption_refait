package com.example.mlebeau.gsb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.UnsupportedSchemeException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.mlebeau.gsb.Classes.SQLiteHandler;
import com.example.mlebeau.gsb.Classes.SessionManager;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {


    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite
        db = new SQLiteHandler(getApplicationContext());

        // Gestion sesssion
        session = new SessionManager(getApplicationContext());

        // Déja connecté ?
        if (session.isLoggedIn()) {
            // User est déjà connecté, on l'emmenène vers l'index
            Intent intent = new Intent(MainActivity.this, Index.class);
            startActivity(intent);
            finish();
        }



        Button buttonConnexion = (Button) findViewById(R.id.login);
        final EditText login_text = (EditText) findViewById(R.id.login_field);
        final EditText mdp_text = (EditText) findViewById(R.id.psd_field);

        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String identifiant = login_text.getText().toString();
                String mdp = mdp_text.getText().toString();

                connexionU(identifiant,mdp);
            }
        });
    }

    private void user_id(String login, String mdp){

        final AsyncSoiree asyncSoiree = new AsyncSoiree() {


        };


        try

            {
                String idEncode = URLEncoder.encode(login, "UTF-8");
                String mdpEncode = URLEncoder.encode(mdp, "UTF-8");

                 asyncSoiree.execute("http://10380.sio.jbdelasalle.com/~mlebeau/GHB/index.php?uc=get_id_user&login=" + idEncode );

            }
       catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }


    }


    private void connexionU(final String login, final String mdp){
        final AsyncSoiree asyncSoiree = new AsyncSoiree(){

            @Override
            protected void onPostExecute(String s){

                pDialog.setMessage("Connexion");
                showDialog();


                //s = s.replace("\n","");
                switch (s){
                    case "1":
                        hideDialog();
                        session.setLogin(true);

/*                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");


                        // Inserting row in users table
                        db.addUser(user_id(login,mdp),login);*/


                        Intent intent = new Intent(MainActivity.this,Index.class);
                        startActivity(intent);
                        break;
                    default:
                        hideDialog();
                        Toast.makeText(MainActivity.this, "Le login et mot de passe ne correspondent pas", Toast.LENGTH_LONG).show();
                        break;

                }

            }
        };
        try {
            String idEncode = URLEncoder.encode(login, "UTF-8");
            String mdpEncode = URLEncoder.encode(mdp, "UTF-8");


            asyncSoiree.execute("http://10380.sio.jbdelasalle.com/~mlebeau/GHB/index.php?uc=login&psw="+idEncode+"&login="+mdpEncode);
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}