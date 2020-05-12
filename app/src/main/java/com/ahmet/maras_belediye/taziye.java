package com.ahmet.maras_belediye;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class taziye extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_vefat);

        TextView ana = (TextView) findViewById(R.id.baslik_ana);
        TextView baba = (TextView) findViewById(R.id.baslik_baba);
        TextView dogum = (TextView) findViewById(R.id.baslik_dt);
        TextView vefat = (TextView) findViewById(R.id.baslik_vt);
        TextView def_trh = (TextView) findViewById(R.id.baslik_deft);
        TextView def_yer = (TextView) findViewById(R.id.baslik_defy);
        TextView cks = (TextView) findViewById(R.id.baslik_cks);
        TextView cky = (TextView) findViewById(R.id.baslik_cky);
        TextView cy = (TextView) findViewById(R.id.baslik_ceny);
        TextView ta = (TextView) findViewById(R.id.baslik_ta);
        TextView anm = (TextView) findViewById(R.id.baslik_anm);
        ana.setText(getIntent().getStringExtra("ana_adi"));
        baba.setText(getIntent().getStringExtra("baba_adi"));
        dogum.setText(getIntent().getStringExtra("dogum"));
        vefat.setText(getIntent().getStringExtra("vefat"));
        def_trh.setText(getIntent().getStringExtra("def_trh"));
        def_yer.setText(getIntent().getStringExtra("def_yer"));
        cks.setText(getIntent().getStringExtra("cks"));
        cky.setText(getIntent().getStringExtra("cky"));
        cy.setText(getIntent().getStringExtra("cy"));
        ta.setText(getIntent().getStringExtra("ta"));
        anm.setText(getIntent().getStringExtra("anons"));
    }
}
