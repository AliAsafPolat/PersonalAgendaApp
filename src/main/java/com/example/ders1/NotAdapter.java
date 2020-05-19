package com.example.ders1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotAdapter extends RecyclerView.Adapter<NotAdapter.MyViewHolder> {
    ArrayList<Not> notlar;
    LayoutInflater layoutInflater;
    private OnItemClickListener mListener;

    public NotAdapter(Context context, ArrayList<Not> notlar) {
        this.notlar = notlar;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.not_gosterme_duzeni,parent,false);
        MyViewHolder holder = new MyViewHolder(view,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Not n;
        n = notlar.get(position);
        holder.notBaslik.setText(n.getBaslik());
        holder.olusturulmaTarihi.setText("Tarih : "+n.getOlusturulmaTarihi());
    }


    @Override
    public int getItemCount() {
        return notlar.size();
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }


    public void setOnItemClickListener(NotAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView notBaslik;
        TextView olusturulmaTarihi;
        ImageView delete;

        public MyViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            notBaslik = (TextView) itemView.findViewById(R.id.NotBasligiEnter);
            olusturulmaTarihi = (TextView) itemView.findViewById(R.id.olusturulmaTarihi);
            delete = (ImageView)itemView.findViewById(R.id.imageViewDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){ // Gerçekten bir pozisyonda mı şuan onu almalıyız.
                            listener.onItemClick(position);     // Itema basıldığı zaman bu event çalışsın.
                        }
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){ // Gerçekten bir pozisyonda mı şuan onu almalıyız.
                            listener.onDeleteClick(position);     // Itema basıldığı zaman bu event çalışsın.
                        }
                    }
                }
            });
        }
    }
}
