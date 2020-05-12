package com.ahmet.maras_belediye;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class arac_takip extends AppCompatActivity {
FirebaseFirestore firestore;
FirebaseAuth auth;
ArrayList<String> arrayList=new ArrayList<>();
ArrayList<Location> arLocation=new ArrayList<>();
ArrayList<Double>  arLat=new ArrayList<>();
    ArrayList<Double>  arLongi=new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_arac_takip);
         recyclerView=findViewById(R.id.aracrv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        getdata();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {recyclerView.setAdapter(new adapter());

            }
        },3000);



    }
    class adapter extends RecyclerView.Adapter<adapter.tasarim>{
        @NonNull
        @Override
        public tasarim onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_arac_takip,parent,false);
            return new tasarim(view);
        }

        @Override
        public void onBindViewHolder(@NonNull tasarim holder, final int position) {
            holder.plaka.setText(arrayList.get(position));
          holder.aracid.setText(arLat.get(position)+"");
          holder.zimmet.setText(arLongi.get(position)+"");
          holder.ımageView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String uri = String.format(Locale.ENGLISH, "geo:%f,%f", arLat.get(position), arLongi.get(position));
                  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                  getApplication().startActivity(intent);
              }
          });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        class tasarim extends RecyclerView.ViewHolder{
            CardView cardView;
            TextView zimmet;
            TextView aracid;
            TextView plaka;
            ImageView ımageView;
            public tasarim(@NonNull View itemView) {
                super(itemView);
               cardView= itemView.findViewById(R.id.aractakipcardview);
               zimmet=itemView.findViewById(R.id.zimmet);
               aracid=itemView.findViewById(R.id.arac_id);
               plaka=itemView.findViewById(R.id.arac_plaka);
               ımageView=itemView.findViewById(R.id.konum);
            }
        }
    }
    void getdata(){
        CollectionReference reference=firestore.collection("konum");
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
if(queryDocumentSnapshots!=null){
    for(DocumentSnapshot snapshot:queryDocumentSnapshots.getDocuments()){
        Map<String,Object> map=snapshot.getData();
        arrayList.add((String)map.get("mail"));
      Object latitude=(Object) map.get("latitude");
      Object longitude=(Object) map.get("longitude");
      arLat.add((double)latitude);
      arLongi.add((double)longitude);

    }
}
            }
        });



        }

}
