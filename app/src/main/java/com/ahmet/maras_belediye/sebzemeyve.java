package com.ahmet.maras_belediye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

public class sebzemeyve extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sebzemeyve);
        String sbUrl = "https://kahramanmaras.bel.tr/servis/halfiyat/halfiyat.json?tarih=" + LocalDate.now().toString() + "&hal=&urunturid=&urunid=";
        new sbAsync().execute(sbUrl);
    }

    class adapter extends RecyclerView.Adapter<adapter.tasarim>{
        ArrayList<String> birincikalite = new ArrayList<String>();
        ArrayList<String> ikincikalite = new ArrayList<String>();
        ArrayList<String> urunadi = new ArrayList<String>();
        public adapter(ArrayList<String> birincikalite, ArrayList<String> ikincikalite, ArrayList<String> urunadi) {
            this.birincikalite = birincikalite;
            this.ikincikalite = ikincikalite;
            this.urunadi = urunadi;
        }

        @NonNull
        @Override
        public tasarim onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sebzemeyve,parent,false);

            return new tasarim(v);
        }

        @Override
        public void onBindViewHolder(@NonNull tasarim holder, int position) {

            holder.urun.setText(urunadi.get(position));
            holder.fiyat1.setText(birincikalite.get(position));
            holder.fiyat2.setText(ikincikalite.get(position));
        }

        @Override
        public int getItemCount() {
            return urunadi.size();
        }

        class tasarim extends RecyclerView.ViewHolder{
            TextView urun;
            TextView fiyat1;
            TextView fiyat2;
            public tasarim(@NonNull View itemView) {
                super(itemView);
                urun=itemView.findViewById(R.id.urun);
                fiyat1=itemView.findViewById(R.id.birinci_kalite_fiyat);
                fiyat2=itemView.findViewById(R.id.ikinci_kalite_fiyat);
            }
        }
    }
    public class sbAsync extends AsyncTask<String, Void, Void> {
        ArrayList<String> bk = new ArrayList<>();
        ArrayList<String> ik = new ArrayList<>();
        ArrayList<String> ua = new ArrayList<>();

        @Override
        protected Void doInBackground(String... urlStrings) {
            HttpURLConnection urlConnection   = null;
            BufferedReader reader          = null;
            String 		      forecastJsonStr = null;

            try {
                URL weatherURL = new URL(urlStrings[0]);
                urlConnection  = (HttpURLConnection) weatherURL.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer     = new StringBuffer();

                if (inputStream != null) {
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }
                    if (buffer.length() != 0) {
                        forecastJsonStr = buffer.toString();
                    }
                }
            } catch (IOException e) {
                Log.e("MainActivity", "Error ", e);
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivity", "Error closing stream", e);
                    }
                }
            }

            try {
                JSONObject object = new JSONObject(forecastJsonStr);
                JSONArray data = object.getJSONArray("data");
                for(int i = 0; i < data.length(); i++){
                    JSONObject urun = data.getJSONObject(i);
                    String birinci_kalite = urun.getString("BIRINCI_KALITE_FIYAT");
                    bk.add("Birinci Kalite Fiyat: " + birinci_kalite);
                    String ikinci_kalite = urun.getString("IKINCI_KALITE_FIYAT");
                    ik.add("İkinci Kalite Fiyat: " + ikinci_kalite);
                    String urunadi = urun.getString("URUNADI");
                    ua.add(urunadi);
                }
            } catch (JSONException e) {
                Log.e("FetchWeatherTask", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RecyclerView recyclerView=findViewById(R.id.rvsebzemeyve);
            adapter a=new adapter(bk,ik,ua);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(a);
        }
    }
}
