package com.ahmet.maras_belediye;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ozgecmis extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozgecmis);
        textView=findViewById(R.id.ozgecmis_metin);
        gecmis gecmis=new gecmis();
        gecmis.execute();
    }
    class gecmis extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            String s="";
            try {
                Document document= Jsoup.connect("https://kahramanmaras.bel.tr/ozgecmis/hayrettin-gungor").get();
                Elements elements=document.select("p");
                for(Element element:elements){
                s+=element.text()+"\n\n\n";}

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
