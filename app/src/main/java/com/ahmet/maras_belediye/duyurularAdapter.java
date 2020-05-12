package com.ahmet.maras_belediye;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class duyurularAdapter extends RecyclerView.Adapter<duyurularAdapter.duyurularTasarim> {
    Context context;
    List<String> duyurularList;
    List<String> duyuruUrl;

    public duyurularAdapter(Context context, List<String> duyurularList, List<String> duyurularListUrl) {
        this.context = context;
        this.duyurularList = duyurularList;
        this.duyuruUrl=duyurularListUrl;
    }

    @NonNull
    @Override
    public duyurularTasarim onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.duyurucardtasarim,parent,false);
        return new duyurularTasarim(v);
    }

    @Override
    public void onBindViewHolder(@NonNull duyurularTasarim holder, final int position) {
        holder.textView.setText(duyurularList.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(context,duyuruActivity.class);
                ıntent.putExtra("url",duyuruUrl.get(position));
                context.startActivity(ıntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return duyurularList.size();
    }

    class duyurularTasarim extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView;
        public duyurularTasarim(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.duyuruCardView);
            textView=itemView.findViewById(R.id.duyurutextView);

        }
    }
}
