package com.ahmet.maras_belediye;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class duyuruActivity extends AppCompatActivity {
    ImageView image;
    TextView textView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyuru);
         url=getIntent().getStringExtra("url");
        image=findViewById(R.id.imageView13);
        //textView=findViewById(R.id.textviewduyuru);
        duyuruveri duyuruveri=new duyuruveri();
        duyuruveri.execute(url);




    }
   class duyuruveri extends AsyncTask<String,String,String>{
       @Override
       protected void onPostExecute(String s) {
           super.onPostExecute(s);

if(s.length()>10){
          Picasso.get().load(s).into(image);}

       }

       @Override
       protected String doInBackground(String... strings) {
           String r="";
           try {
               Document document=Jsoup.connect(strings[0]).get();
               Elements element=document.select("div[class=field-items]");
              element=element.select("img");
              r=element.get(0).absUrl("src");

              // r=String.valueOf(element);

           } catch (Exception e) {
               e.printStackTrace();
           }
           return r;
       }
   }
}
