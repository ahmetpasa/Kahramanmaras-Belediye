package com.ahmet.maras_belediye;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmet.maras_belediye.sync.ReminderUtilities;
import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    sqliteHaber sql;
    SQLiteDatabase database;
private ViewPager viewPager;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ArrayList<String> haberlerliste=new ArrayList<>();
    ArrayList<String> arrayListduyuru=new ArrayList<>();
    ArrayList<String> arrayListduyuruUrl=new ArrayList<>();
    RecyclerView rvduyuru;
    duyurularAdapter duyurularAdapter;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration mainConfig = new Configuration(getResources().getConfiguration());
        String languageToLoad = "en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        mainConfig.setLocale(locale);
        getResources().updateConfiguration(mainConfig, null);



database=this.openOrCreateDatabase("haberler",MODE_PRIVATE,null);
database.execSQL("create table if not exists haber(url text,yazi text,resim blob)");
sql=new sqliteHaber();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String dil=sharedPreferences.getString("language","english");
        rvduyuru=findViewById(R.id.duyurular);
        rvduyuru.setHasFixedSize(true);
        rvduyuru.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        duyurularAdapter=new duyurularAdapter(MainActivity.this,arrayListduyuru,arrayListduyuruUrl);

duyuruveri duyuruveri=new duyuruveri();
duyuruveri.execute();


         viewPager=findViewById(R.id.kesfedin);
        viewpageradapter viewPagerAdapter=new viewpageradapter(getSupportFragmentManager());
        viewPagerAdapter.ekle(new kesfedin1());
        viewPagerAdapter.ekle(new kesfedin2());
        viewPagerAdapter.ekle(new kesfedin3());
        viewPager.setAdapter(viewPagerAdapter);
        haberveri haberveri=new haberveri();
        haberveri.execute();


        LinearLayout layoutCenaze=findViewById(R.id.cenaze_ilanlari);
        layoutCenaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(MainActivity.this,cenaze.class);
                startActivity(ıntent);

            }
        });
        LinearLayout taziye=findViewById(R.id.taziye_evleri);
        taziye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(MainActivity.this,taziye_evleri.class);
                startActivity(ıntent);

            }
        });

        LinearLayout semptpazarlari=findViewById(R.id.semt_pazarlari);
        semptpazarlari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(MainActivity.this,semt_pazari.class);
                startActivity(ıntent);
            }
        });
LinearLayout aractakip=findViewById(R.id.arac_takip);
aractakip.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent ıntent=new Intent(MainActivity.this,arac_takip.class);
        startActivity(ıntent);
    }
});


        LinearLayout eczanenobet=findViewById(R.id.nobetci_eczane);
        eczanenobet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(MainActivity.this,nobetci_eczane.class);
                startActivity(ıntent);
            }
        });


        LinearLayout sebzemyve=findViewById(R.id.sebze_meyve);
        sebzemyve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(MainActivity.this,sebzemeyve.class);
                startActivity(ıntent);
            }
        });

        ImageView ımageView=findViewById(R.id.imageView);
        ımageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"));
                startActivity(browserIntent);
            }
        });
        ImageView ımageView1=findViewById(R.id.imageView2);
        ımageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com"));
                startActivity(browserIntent);

            }
        });
        ImageView ımageView2=findViewById(R.id.imageView3);
        ımageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com"));
                startActivity(browserIntent);
            }
        });
        ImageView ımageView3=findViewById(R.id.imageView4);
        ımageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com"));
                startActivity(browserIntent);
            }
        });

        navigationView =findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_overflow, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){drawerLayout.closeDrawer(GravityCompat.START);}
        else{System.exit(0);}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.button){
            Toast.makeText(getApplicationContext(),"yes",Toast.LENGTH_SHORT);
        }
        if(menuItem.getItemId()==R.id.nav_kurumsal){
            Intent ıntent=new Intent(getApplicationContext(),kurumsal.class);
            startActivity(ıntent);
        }
        if(menuItem.getItemId()==R.id.nav_baskan){
            Intent ıntent=new Intent(getApplicationContext(),baskan.class);
            startActivity(ıntent);
        }
        if(menuItem.getItemId()==R.id.girisyap){
            Intent ıntent=new Intent(getApplicationContext(),login.class);
            startActivity(ıntent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent ıntent=new Intent(getApplicationContext(),preferences.class);
        startActivity(ıntent);
        return true;
    }

   class viewpageradapter extends FragmentPagerAdapter {
        List<Fragment> fragments=new ArrayList<>();

       public viewpageradapter(FragmentManager fm) {
           super(fm);
       }

       @Override
       public Fragment getItem(int position) {
           return fragments.get(position);
       }

       @Override
       public int getCount() {
           return fragments.size();
       }
       void ekle(Fragment fragment){
           fragments.add(fragment);
       }
   }

    class viewpagerhaberleradapter extends FragmentPagerAdapter {
        List<Fragment> fragments=new ArrayList<>();

        public viewpagerhaberleradapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        void ekle(Fragment fragment){
            fragments.add(fragment);
        }
    }
    class haberveri extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            String verihaber="";
            try {
                Document document= Jsoup.connect("https://kahramanmaras.bel.tr/haber").get();
                Elements elements=document.select("ul[class=links inline]");
                for (Element element:elements){
                    haberlerliste.add(String.valueOf(element.select("li").select("a")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return verihaber;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ViewPager viewPagerhaberler=findViewById(R.id.tek_haber);
            viewpagerhaberleradapter viewpagerhaberleradapter=new viewpagerhaberleradapter(getSupportFragmentManager());
            try{
            Cursor c=database.rawQuery("select * from haber",null);
            while (c.moveToNext()){
                String cString=c.getString(c.getColumnIndex("url"));
                if(!haberlerliste.contains(cString)){sql.sil(cString);}
            }
            c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for(String sitring:haberlerliste){
                sql.ekle(sitring);

            }
            Cursor cursor=database.rawQuery("select * from haber",null);
            while (cursor.moveToNext()){
                viewpagerhaberleradapter.ekle(new haberler(cursor.getString(cursor.getColumnIndex("url"))));
            }
            viewPagerhaberler.setAdapter(viewpagerhaberleradapter);
        }
    }
class duyuruveri extends AsyncTask<String,String,String>{
    @Override
    protected String doInBackground(String... strings) {
        try {
            Document document=Jsoup.connect("https://kahramanmaras.bel.tr/").get();
            Elements elements=document.select("div[id=ms-announcements-wrapper]");
            Elements elements1=elements.select("a");
            for(Element element:elements1){
                arrayListduyuruUrl.add(element.absUrl("href"));
                arrayListduyuru.add(element.text());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        rvduyuru.setAdapter(duyurularAdapter);
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
        boolean isUpdated(String url){
            Cursor c=database.rawQuery("select * from haber where url='?'",new String[]{url});
            while(c.moveToNext()){
                if (!c.getString(c.getColumnIndex("yazi")).equals(null)) {
                    return true;
                }
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
         Cursor c=database.rawQuery("select * from haber where url='?'",new String[]{url});
         while (c.moveToNext()){
             byte[] bytes=c.getBlob(c.getColumnIndex("resim"));
             Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
             return bitmap;
         }
         return null;
     }
         void sethaberYazi(String url,String yazi){
            database.execSQL("update haber set yazi='?' where url='?'",new String[]{yazi,url});
        }
         void setresim(String url, Bitmap bitmap){
            Bitmap b=bitmap;
             ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
             b.compress(Bitmap.CompressFormat.PNG,50,outputStream);
             byte[] bytes=outputStream.toByteArray();

             database.execSQL("update haber set resim='?' where url='?'",new String[]{String.valueOf(bytes),url});
        }
}
}
