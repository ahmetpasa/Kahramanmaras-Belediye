package com.ahmet.maras_belediye;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class gorev_yetki extends AppCompatActivity {
    TextView textView;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gorev_yetki);
        textView=findViewById(R.id.metin);
        textView2=findViewById(R.id.baslik);
        gorev gorev=new gorev();
        gorev.execute();
    }

    class gorev extends AsyncTask<String,String,String>{
        String s;
        @Override
        protected String doInBackground(String... strings) {
            try {
                Document doc= Jsoup.connect("https://kahramanmaras.bel.tr/gorev-ve-yetkileri").get();
                Elements elements=doc.select("p");
                s=elements.text();

               Elements elements1=doc.select("ol");

               elements1=elements1.select("li");

               for(Element element:elements1){
                   s+=element.text()+"\n\n\n";
               }


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
