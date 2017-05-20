package com.example.mlebeau.gsb;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class envoyer_sms extends AppCompatActivity {

        private EditText txt_message;
        private Button btn_envoyer;
        private PendingIntent sentPI;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_envoyer_sms);
            initialisation();
            ActivityCompat.requestPermissions(envoyer_sms.this, new String[]{Manifest.permission.SEND_SMS}, 10000);
            btn_envoyer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sentPI = PendingIntent.getBroadcast(getBaseContext(), 0, new Intent("SMS_SENT"), 0);
                    String telephone = getIntent().getStringExtra("numero");
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(telephone, null, txt_message.getText().toString(), sentPI, null);
                }
            });
        }

        public void initialisation() {
            txt_message = (EditText) findViewById(R.id.envoieSms_txt_message);
            btn_envoyer = (Button) findViewById(R.id.envoieSms_btn_envoyer);
        }
    }
