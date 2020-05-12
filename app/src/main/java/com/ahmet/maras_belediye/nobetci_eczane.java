package com.ahmet.maras_belediye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

import java.util.ArrayList;
import java.util.List;

public class nobetci_eczane extends AppCompatActivity {
    ArrayList<String> isimler=new ArrayList<>();
    ArrayList<String> adresler=new ArrayList<>();
    ArrayList<String> numara=new ArrayList<>();
    ArrayList<String> map=new ArrayList<>();
    RecyclerView recyclerView;
    adapter a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nobetci_eczane);

         recyclerView=findViewById(R.id.eczaneler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



         a=new adapter(isimler,adresler);
        nobetcieczaneveri nobetcieczaneveri=new nobetcieczaneveri();
        nobetcieczaneveri.execute();


    }
    class adapter extends RecyclerView.Adapter<adapter.tasarim>{
        List<String> eczaneadi;
        List<String> eczaneadress;

        public adapter(List<String> eczaneadi, List<String> eczaneadress) {
            this.eczaneadi = eczaneadi;
            this.eczaneadress = eczaneadress;
        }


        @NonNull
        @Override
        public tasarim onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eczane,parent,false);
            return new tasarim(view);
        }

        @Override
        public void onBindViewHolder(@NonNull tasarim holder, final int position) {
            holder.isim.setText(eczaneadi.get(position));
            holder.adress.setText(eczaneadress.get(position));
            holder.ara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+numara.get(position).substring(3)));
                    startActivity(intent);
                }
            });

            holder.konum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map.get(position)));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return eczaneadi.size();
        }

        class tasarim extends RecyclerView.ViewHolder{
            CardView cardView;
            ImageView ara;
            ImageView konum;
            TextView isim;
            TextView adress;
            public tasarim(@NonNull View itemView) {
                super(itemView);
                cardView=itemView.findViewById(R.id.cardeczane);
                konum=itemView.findViewById(R.id.konum);
                isim=itemView.findViewById(R.id.eczaneadi);
                adress=itemView.findViewById(R.id.eczaneadress);
                ara=itemView.findViewById(R.id.telefon);
            }
        }
    }
    class nobetcieczaneveri extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            String ss="";
            try{
            Document document= Jsoup.connect("https://kahramanmaras.bel.tr/nobetci-eczaneler").get();
                Elements elements=document.select("div[class=eczane-td eczane-ad]");
                Elements elements1=document.select("div[class=eczane-adres]");
                Elements elements2=document.select("div[class=eczane-links]");
                Elements elements3=document.select("a[class=eczane-link-map]");




                for(Element element:elements3){
                    map.add(element.absUrl("href"));
                }

                for(Element element:elements2){
                    numara.add(element.select("a").get(1).text());
                }

                for(Element element:elements){
                    isimler.add(element.text());
                }
                for(Element element:elements1){
                    adresler.add(element.text());
                }
               ss= elements.get(0).text();
            }
            catch (Exception e){}
            return ss;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            recyclerView.setAdapter(a);
        }
    }
}
