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
import java.util.ArrayList;

public class semt_pazari extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semt_pazari);
        new spAsync().execute();

    }
    public class spAsync extends AsyncTask<Void, Void, Void>{
        ArrayList<String> mahalle = new ArrayList<String>();
        ArrayList<String> adres = new ArrayList<String>();

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document doc = Jsoup.connect("https://kahramanmaras.bel.tr/haftalik-semt-pazar-yerleri").get();
                Elements el = doc.select("p.rtecenter");
                for(int i = 0; i < 11; i+=2){
                    String mahalleIsım = el.get(i).text();
                    String adresIsim = el.get(i+1).text();
                    mahalle.add(mahalleIsım);
                    adres.add(adresIsim);
                }
                el = doc.select("td.rtecenter");
                String istisna = el.get(7).text();
                String[] deneme = istisna.split(" ");
                String yeni = "";
                yeni = yeni.concat(deneme[0]);
                yeni = yeni.concat(" " + deneme[1]);
                mahalle.add(yeni);
                String ikinci = "";
                for(int i = 2; i < deneme.length; i++){
                    ikinci = ikinci.concat(" " + deneme[i]);
                }
                adres.add(ikinci);
                el = doc.select("p.rtecenter");
                for(int i = 12; i < 21; i+=2){
                    String mahalleIsım = el.get(i).text();
                    String adresIsim = el.get(i+1).text();
                    mahalle.add(mahalleIsım);
                    adres.add(adresIsim);
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
            TextView b1p = (TextView) findViewById(R.id.baslik_1_pazartesi);
            TextView a1p = (TextView) findViewById(R.id.adres_1_pazartesi);
            TextView b2p = (TextView) findViewById(R.id.baslik_2_pazartesi);
            TextView a2p = (TextView) findViewById(R.id.adres_2_pazartesi);
            TextView b1s = (TextView) findViewById(R.id.baslik_1_sali);
            TextView a1s = (TextView) findViewById(R.id.adres_1_sali);
            TextView b2s = (TextView) findViewById(R.id.baslik_2_sali);
            TextView a2s = (TextView) findViewById(R.id.adres_2_sali);
            TextView b1c = (TextView) findViewById(R.id.baslik_1_carsamba);
            TextView a1c = (TextView) findViewById(R.id.adres_1_carsamba);
            TextView b2c = (TextView) findViewById(R.id.baslik_2_carsamba);
            TextView a2c = (TextView) findViewById(R.id.adres_2_carsamba);
            TextView b1pe = (TextView) findViewById(R.id.baslik_1_persembe);
            TextView a1pe = (TextView) findViewById(R.id.adres_1_persembe);
            TextView b2pe = (TextView) findViewById(R.id.baslik_2_persembe);
            TextView a2pe = (TextView) findViewById(R.id.adres_2_persembe);
            TextView b1cm = (TextView) findViewById(R.id.baslik_1_cuma);
            TextView a1cm = (TextView) findViewById(R.id.adres_1_cuma);
            TextView b2cm = (TextView) findViewById(R.id.baslik_2_cuma);
            TextView a2cm = (TextView) findViewById(R.id.adres_2_cuma);
            TextView b1cu = (TextView) findViewById(R.id.baslik_1_cumartesi);
            TextView a1cu = (TextView) findViewById(R.id.adres_1_cumartesi);
            TextView b2cu = (TextView) findViewById(R.id.baslik_2_cumartesi);
            TextView a2cu = (TextView) findViewById(R.id.adres_2_cumartesi);
            b1p.setText(mahalle.get(0));
            a1p.setText(adres.get(0));
            b2p.setText(mahalle.get(1));
            a2p.setText(adres.get(1));
            b1s.setText(mahalle.get(2));
            a1s.setText(adres.get(2));
            b2s.setText(mahalle.get(3));
            a2s.setText(adres.get(3));
            b1c.setText(mahalle.get(4));
            a1c.setText(adres.get(4));
            b2c.setText(mahalle.get(5));
            a2c.setText(adres.get(5));
            b1pe.setText(mahalle.get(6));
            a1pe.setText(adres.get(6));
            b2pe.setText(mahalle.get(7));
            a2pe.setText(adres.get(7));
            b1cm.setText(mahalle.get(8));
            a1cm.setText(adres.get(8));
            b2cm.setText(mahalle.get(9));
            a2cm.setText(adres.get(9));
            b1cu.setText(mahalle.get(10));
            a1cu.setText(adres.get(10));
            b2cu.setText(mahalle.get(11));
            a2cu.setText(adres.get(11));
        }
    }
}
