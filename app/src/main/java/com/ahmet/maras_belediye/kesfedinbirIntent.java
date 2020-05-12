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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class kesfedinbirIntent extends AppCompatActivity {
    String url;
    String ss;
    TextView textView;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kesfedinbir_intent);
        url=getIntent().getStringExtra("url");
        textView=findViewById(R.id.textView2);

        image=findViewById(R.id.imageView8);
        jsoup3 jsoup3=new jsoup3();
        jsoup3.execute();

    }
    class jsoup3 extends AsyncTask<String,String,String> {

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
