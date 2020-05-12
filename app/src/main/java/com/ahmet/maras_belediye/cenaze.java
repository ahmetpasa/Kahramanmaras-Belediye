package com.ahmet.maras_belediye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class cenaze extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cenaze);
        new cenazeAsync().execute();
    }
    class adapter extends RecyclerView.Adapter<adapter.tasarim>{

        ArrayList<String> vefatEden = new ArrayList<String>();
        ArrayList<String> vefatAdres = new ArrayList<String>();
        ArrayList<String> vefatYas = new ArrayList<String>();
        ArrayList<String> a = new ArrayList<String>();
        ArrayList<String> b = new ArrayList<String>();
        ArrayList<String> c = new ArrayList<String>();
        ArrayList<String> d = new ArrayList<String>();
        ArrayList<String> e = new ArrayList<String>();
        ArrayList<String> f = new ArrayList<String>();
        ArrayList<String> g = new ArrayList<String>();
        ArrayList<String> h = new ArrayList<String>();
        ArrayList<String> i = new ArrayList<String>();
        ArrayList<String> j = new ArrayList<String>();
        ArrayList<String> k = new ArrayList<String>();
        public adapter(ArrayList<String> vefatEden, ArrayList<String> vefatAdres, ArrayList<String> vefatYas,ArrayList<String> a,
                       ArrayList<String> b,ArrayList<String> c,ArrayList<String> d,ArrayList<String> e,
                       ArrayList<String> f,ArrayList<String> g,ArrayList<String> h,ArrayList<String> i,ArrayList<String> j
        ,ArrayList<String> k) {
            this.vefatEden = vefatEden;
            this.vefatAdres = vefatAdres;
            this.vefatYas = vefatYas;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
            this.f = f;
            this.g = g;
            this.h = h;
            this.i = i;
            this.j = j;
            this.k = k;
        }

        @NonNull
        @Override
        public tasarim onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cenaze,parent,false);
            return new tasarim(v);
        }

        @Override
        public void onBindViewHolder(@NonNull tasarim holder, final int position) {
            holder.isim.setText(vefatEden.get(position));
            holder.addres.setText(vefatAdres.get(position));
            holder.yas.setText(vefatYas.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(cenaze.this, taziye.class);
                    intent.putExtra("ana_adi",a.get(position));
                    intent.putExtra("baba_adi",b.get(position));
                    intent.putExtra("dogum",c.get(position));
                    intent.putExtra("vefat",d.get(position));
                    intent.putExtra("def_trh",e.get(position));
                    intent.putExtra("def_yer",f.get(position));
                    intent.putExtra("cks",g.get(position));
                    intent.putExtra("cky",h.get(position));
                    intent.putExtra("cy",i.get(position));
                    intent.putExtra("ta",j.get(position));
                    intent.putExtra("anons",k.get(position));
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return vefatEden.size();
        }

        class tasarim extends RecyclerView.ViewHolder{
            TextView isim;
            TextView yas;
            TextView addres;
            public tasarim(@NonNull View itemView) {
                super(itemView);
                isim=itemView.findViewById(R.id.vefat_eden);
                yas=itemView.findViewById(R.id.yas);
                addres=itemView.findViewById(R.id.adres);
            }
        }
    }
    public class cenazeAsync extends AsyncTask<Void, Void, Void> {
        ArrayList<String> vefatEdenler = new ArrayList<String>();
        ArrayList<String> vefatAdresler = new ArrayList<String>();
        ArrayList<String> vefatYaslar = new ArrayList<String>();
        ArrayList<String> AnaAdlari = new ArrayList<String>();
        ArrayList<String> BabaAdlari = new ArrayList<String>();
        ArrayList<String> dogumlar = new ArrayList<String>();
        ArrayList<String> vefatlar = new ArrayList<String>();
        ArrayList<String> def_trhler = new ArrayList<String>();
        ArrayList<String> def_yerler = new ArrayList<String>();
        ArrayList<String> cksler = new ArrayList<String>();
        ArrayList<String> ckyler = new ArrayList<String>();
        ArrayList<String> cyler = new ArrayList<String>();
        ArrayList<String> talar = new ArrayList<String>();
        ArrayList<String> anonslar = new ArrayList<String>();

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document doc = Jsoup.connect("https://kahramanmaras.bel.tr/cenaze-ilanlari").get();
                Elements el = doc.select("h3.cenaze-baslik");
                for(Element e : el){
                    String vefatEden = e.text();
                    vefatEdenler.add(vefatEden);
                }
                el = doc.select("span.cenaze-ilce");
                for(Element e : el){
                    String vefatAdres = e.text();
                    vefatAdresler.add(vefatAdres);
                }
                el = doc.select("span.cenaze-yas");
                for(Element e : el){
                    String vefatYas = e.text();
                    vefatYaslar.add(vefatYas);
                }
                el = doc.select("div.cenaze-tr");
                for(int i = 0; i < el.size(); i+=11){
                    String ana = el.get(i).text();
                    AnaAdlari.add(ana);
                    String baba = el.get(i+1).text();
                    BabaAdlari.add(baba);
                    String dogum = el.get(i+2).text();
                    dogumlar.add(dogum);
                    String vefat = el.get(i+3).text();
                    vefatlar.add(vefat);
                    String def_trh = el.get(i+4).text();
                    def_trhler.add(def_trh);
                    String def_yer = el.get(i+5).text();
                    def_yerler.add(def_yer);
                    String cks = el.get(i+6).text();
                    cksler.add(cks);
                    String cky = el.get(i+7).text();
                    ckyler.add(cky);
                    String cenaze_yakini = el.get(i+8).text();
                    cyler.add(cenaze_yakini);
                    String taziye_adresi = el.get(i+9).text();
                    talar.add(taziye_adresi);
                    String anons = el.get(i+10).text();
                    anonslar.add(anons);
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RecyclerView recyclerView=findViewById(R.id.rvcenazeler);
            adapter a = new adapter(vefatEdenler,vefatAdresler,vefatYaslar,AnaAdlari,BabaAdlari,
            dogumlar,vefatlar,def_trhler,def_yerler,cksler,ckyler,cyler,talar,anonslar);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(a);

    }
}
}
