package com.ahmet.maras_belediye;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class kesfedinucIntent extends AppCompatActivity {
    String url;
    String ss;
    TextView textView;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kesfedinuc_intent);
        url=getIntent().getStringExtra("url");
        textView=findViewById(R.id.textView4);
        image=findViewById(R.id.imageView10);
       jsoup5 jsoup5=new jsoup5();
        jsoup5.execute();
    }
    class jsoup5 extends AsyncTask<String,String,String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Picasso.get().load(s).into(image);
            textView.setText(ss);

        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            String s="";
            try{


                Document doc2= Jsoup.connect(url).get();
                Elements url=doc2.select("img[typeof=foaf:Image]");
                s=  url.get(0).absUrl("src");
                Elements des =doc2.select("meta[content]");
                ss=doc2.select("div[class=field-item even]").text();


                //    s+=es.get(0);




            } catch (Exception e) {
                s="fuck";
                e.printStackTrace();
            }
            return s;
        }
    }
}
