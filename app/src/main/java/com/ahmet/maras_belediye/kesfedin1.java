package com.ahmet.maras_belediye;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class kesfedin1 extends Fragment {
    ImageView image ;
    String ss="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragmentkesfedin1,container,false);
        image=v.findViewById(R.id.imageView5);
        jsoup jsoup=new jsoup();
        jsoup.execute();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(getContext(),kesfedinbirIntent.class);
                ıntent.putExtra("url",ss);
                startActivity(ıntent);
            }
        });
        return v;
    }
    class jsoup extends AsyncTask<String,String,String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           Picasso.get().load(s).into(image);

        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            String s="";
            try{
                Document doc= Jsoup.connect("https://kahramanmaras.bel.tr/").get();
                Elements ess=doc.select("a[class=ms-news-swiper-a clearfix] href");
                Elements es=doc.select("div[id=ms-swiper-wrapper-discover]");
                s+= es.select("a[href]").get(0).text("href").absUrl("href");
                ss=s;
                Document doc2= Jsoup.connect(s).get();
                Elements url=doc2.select("img[typeof=foaf:Image]");
              s=  url.get(0).absUrl("src");



                //    s+=es.get(0);




            } catch (Exception e) {
                s="fuck";
                e.printStackTrace();
            }
            return s;
        }
    }
}
