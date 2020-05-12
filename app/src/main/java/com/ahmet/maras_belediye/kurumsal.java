package com.ahmet.maras_belediye;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class kurumsal extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurumsal);
        textView=findViewById(R.id.vizyon_misyon);
        veri veri=new veri();
        veri.execute();
    }
    class veri extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            String s="";
            try{
                Document document=Jsoup.connect("https://kahramanmaras.bel.tr/kurumsal/misyon-ve-vizyon").get();
                Elements e=document.select("p");
                s=e.text();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }
    }
}
