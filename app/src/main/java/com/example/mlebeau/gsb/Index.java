package com.example.mlebeau.gsb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Button addForm = (Button) findViewById(R.id.new_form);
        Button suiviForm = (Button) findViewById(R.id.suivi_form);
        Button searchForm = (Button) findViewById(R.id.search_form);
        Button myForm = (Button) findViewById(R.id.myform);


        addForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Index.this,AddFormation.class);
                startActivity(intent);
            }
        });


        suiviForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Index.this,SuiviFormationActivity.class);
                startActivity(intent);
            }
        });
        /*
        searchForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Index.this,AddFormation.class);
                startActivity(intent);
            }
        });
        myForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Index.this,AddFormation.class);
                startActivity(intent);
            }
        });*/

    }
}

