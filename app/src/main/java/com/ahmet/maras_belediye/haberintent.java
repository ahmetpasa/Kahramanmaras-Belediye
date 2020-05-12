package com.ahmet.maras_belediye;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class haberintent extends AppCompatActivity {
    ImageView image;
    TextView textView;
    SQLiteDatabase database;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haberintent);
        database=getApplicationContext().openOrCreateDatabase("haberler",MODE_PRIVATE,null);
        url=getIntent().getStringExtra("url");
        image=findViewById(R.id.imageView12);
        textView=findViewById(R.id.textView5);
        haberintentveri haberintentveri=new haberintentveri();
        haberintentveri.execute(url);
        image.setImageBitmap(getresim(url));

    }
   class haberintentveri extends AsyncTask<String,String,String>{
        String url2="";
        String yazi="";
       @Override
       protected String doInBackground(String... strings) {


           try {
               Document document= Jsoup.connect(strings[0]).get();
               Elements elements=document.select("img[typeof=foaf:Image]");
               Elements elements1=document.select("p");
               for(int i=0;i<elements1.size()-2;i++){

                   yazi+=elements1.get(i).text()+"\n\n";
               }

               url2=elements.get(0).absUrl("src");

           } catch (Exception e) {
               e.printStackTrace();
           }
           return url2;
       }

       @Override
       protected void onPostExecute(String s) {
           super.onPostExecute(s);
           //Picasso.get().load(s).into(image);

           textView.setText(yazi);
       }
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
}
