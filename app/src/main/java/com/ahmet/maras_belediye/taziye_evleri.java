package com.ahmet.maras_belediye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class taziye_evleri extends AppCompatActivity {
    ArrayList<String> taziyeeviadi=new ArrayList<>();
    ArrayList<String> map=new ArrayList<>();
    adapter a;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taziye_evleri);
         recyclerView=findViewById(R.id.taziye_liste);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         a=new adapter();
         taziyeveri taziyeveri=new taziyeveri();
         taziyeveri.execute();

    }
    class  adapter extends RecyclerView.Adapter<adapter.tasarim>{
        @NonNull
        @Override
        public tasarim onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_taziye,parent,false);

            return new tasarim(v);
        }

        @Override
        public void onBindViewHolder(@NonNull tasarim holder, final int position) {

            holder.taziyeevi.setText(taziyeeviadi.get(position));
           holder.ımageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String s=map.get(position);
                   String s1[]=s.split(":");

                   Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps?daddr="+s1[1]+"&amp;ll"));
                   intent.setPackage("com.google.android.apps.maps");
                   startActivity(intent);
               }
           });

        }

        @Override
        public int getItemCount() {
            return taziyeeviadi.size();
        }

        class tasarim extends RecyclerView.ViewHolder{
            TextView taziyeevi;

            ImageView ımageView;
            public tasarim(@NonNull View itemView) {

                super(itemView);
                taziyeevi=itemView.findViewById(R.id.taziye_evi);

                ımageView=itemView.findViewById(R.id.konum);

            }
        }
    }
    class taziyeveri extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                Document document= Jsoup.connect("https://kahramanmaras.bel.tr/taziye-evleri").get();
                Elements elements=document.select("div[class=taziye-title-wrapper]");
                Elements elements1=document.select("a[class=taziye-evi-link]");

                for(Element element:elements1){
                    Document document1=Jsoup.connect(element.absUrl("href")).get();
                   Elements elements2=document1.select("a[class=taziye-navlink]");
                   String s[]=String.valueOf(elements2.get(0)).split("\"");
                   map.add(s[3]);
                }
                for(Element element:elements){
                    taziyeeviadi.add(element.text());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            recyclerView.setAdapter(a);
        }
    }
}
