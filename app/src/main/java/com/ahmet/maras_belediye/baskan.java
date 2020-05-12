package com.ahmet.maras_belediye;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class baskan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baskan);
        TextView textView=findViewById(R.id.gorev_ve_yetki);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(getApplicationContext(),gorev_yetki.class);
                startActivity(ıntent);
            }
        });

        TextView textView1=findViewById(R.id.ozgecmis);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(getApplicationContext(),ozgecmis.class);
                startActivity(ıntent);
            }
        });
    }
}
