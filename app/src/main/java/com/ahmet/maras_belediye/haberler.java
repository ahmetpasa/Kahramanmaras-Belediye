package com.ahmet.maras_belediye;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.zip.Inflater;

public class haberler extends Fragment {
    ImageView image;
    SQLiteDatabase database;
    String url;
    String url2;
    sqliteHaber sql;
    Bitmap bitmap;

    public haberler(String url) {
        String[] urls=url.split("\"");
        this.url = "https://kahramanmaras.bel.tr"+urls[1];

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.haberlerview,container,false);
        database=getActivity().openOrCreateDatabase("haberler", Context.MODE_PRIVATE,null);
        sql=new sqliteHaber();
        image=v.findViewById(R.id.imageView11);
        //image.setImageBitmap(sql.getresim(url));
        if(sql.isresimUpdated(url)){
            image.setImageBitmap(sql.getresim(url));
        }
        else{
        haberlerfragmentveri haberlerfragmentveri=new haberlerfragmentveri();
      haberlerfragmentveri.execute(url);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(getContext(),haberintent.class);
                ıntent.putExtra("url",url);
                startActivity(ıntent);
            }
        });

        return v;
    }
    class haberlerfragmentveri extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                Document document= Jsoup.connect(strings[0]).get();
                Elements elements=document.select("img[typeof=foaf:Image]");
                url2=elements.get(0).absUrl("src");
                bitmap=getBitmapFromURL(url2);


            } catch (Exception e) {
                e.printStackTrace();
            }
            sql.setresim(url,bitmap);
return url2;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // Picasso.get().load(s).into(image);

            image.setImageBitmap(bitmap);
        }
    }
    class sqliteHaber{


        void ekle(String url){
            if(isExists(url)){return;}
            database.execSQL("insert into haber ('url') values(?)",new String[]{url});

        }
        void sil(String url){
            database.execSQL("delete from haber where url='?'",new String[]{url});
        }
        boolean isExists(String url){
            Cursor c=database.rawQuery("select * from haber where url='"+url+"'",null);
            String urlS="";
            while(c.moveToNext()){
                urlS=c.getString(c.getColumnIndex("url"));
                if (urlS.equals(url)) {
                    return true;
                }

            }

            return false;
        }
        boolean isyaziUpdated(String url){
            Cursor c=database.rawQuery("select * from haber where url='?'",new String[]{url});
            while(c.moveToNext()){
                if (!c.getString(c.getColumnIndex("yazi")).equals(null)) {
                    return true;
                }
            }
            return false;
        }
        boolean isresimUpdated(String url){
            Cursor c=database.rawQuery("select * from haber where url='"+url+"'",null);
            while(c.moveToNext()){
               return true;
            }
            return false;
        }
        String gethaberYazi(String url){
            Cursor c=database.rawQuery("select * from haber where url='?'",new String[]{url});
            while (c.moveToNext()){
                return c.getString(c.getColumnIndex("yazi"));
            }
            return null;
        }
        Bitmap getresim(String url){
            Cursor c=database.rawQuery("select * from resim where url='"+url+"'",null);
            Bitmap bitmap=null;
            while (c.moveToNext()) {
                byte[] bytes = c.getBlob(c.getColumnIndex("resim"));
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            }

            return bitmap;
        }
        void sethaberYazi(String url,String yazi){
            database.execSQL("update haber set yazi='?' where url='?'",new String[]{yazi,url});
        }
        void setresim(String url, Bitmap bitmap){
            database.execSQL("create table if not exists resim(url text,resim blob)");
            Bitmap b=bitmap;
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG,50,outputStream);
            byte[] bytes=outputStream.toByteArray();
            String string="insert into resim values(?,?)";
            SQLiteStatement sqLiteStatement=database.compileStatement(string);
            sqLiteStatement.bindBlob(2,bytes);
            sqLiteStatement.bindString(1,url);
            sqLiteStatement.execute();

        }
    }
    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
