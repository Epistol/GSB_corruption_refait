package com.example.mlebeau.gsb;

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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    private void connexionU(String login,String mdp){
        final AsyncSoiree asyncSoiree = new AsyncSoiree(){

            @Override
            protected void onPostExecute(String s){
                //s = s.replace("\n","");
                switch (s){
                    case "1":
                        Intent intent = new Intent(MainActivity.this,Index.class);
                        startActivity(intent);
                        break;
                    default:
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

}