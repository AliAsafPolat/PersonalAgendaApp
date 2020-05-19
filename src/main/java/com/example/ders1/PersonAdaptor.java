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

public class PersonAdaptor extends RecyclerView.Adapter<PersonAdaptor.MyViewHolder> {

ArrayList<Person> nPersonList;
LayoutInflater inflater;
private OnItemClickListener mListener;

public PersonAdaptor(Context context, ArrayList<Person> kisiler){
    inflater = LayoutInflater.from(context);
    this.nPersonList = kisiler;
}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.itemview,parent,false);
        MyViewHolder holder = new MyViewHolder(view,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Person insan = nPersonList.get(position);
        holder.setData(insan,position);
    }

    @Override
    public int getItemCount() {
        return nPersonList.size();
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
        void onShowPasswordClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

    TextView personName, personPassword;
    TextView personNameEnter,personPasswordEnter;
    ImageView personImage;
    ImageView showPassword_Image;

    // Arayüzde olan widgetları tanımla.
    public MyViewHolder(View itemView,final OnItemClickListener listener){
        super(itemView);
        personName = (TextView) itemView.findViewById(R.id.userName);
        personPassword = (TextView) itemView.findViewById(R.id.userPassword);
        personPasswordEnter = (TextView) itemView.findViewById(R.id.userPasswordEnter);
        personNameEnter = (TextView) itemView.findViewById(R.id.userNameEnter);
        personImage = (ImageView) itemView.findViewById(R.id.userImage);
        showPassword_Image = (ImageView)itemView.findViewById(R.id.imageVeiw_sifregoster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){                         // Atandığından emin olmalıyız.
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){ // Gerçekten bir pozisyonda mı şuan onu almalıyız.
                        listener.onItemClick(position);     // Itema basıldığı zaman bu event çalışsın.
                    }
                }
            }
        });
        showPassword_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){                             // Atandığından emin olmalıyız.
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){     // Gerçekten bir pozisyonda mı şuan onu almalıyız.
                        listener.onShowPasswordClick(position); // Bu kutuya basıldığında çağırılması gereken eventi yazıyoruz.
                    }
                }
            }
        });
    }

        public void setData(Person kisi, int pos){              // Recycle viewe gelen dataları uygun yerlere yerleştir.
            //burda xmldeki verilere karşılıklarını yazıyoruz anladığım kadaryla.
            this.personNameEnter.setText(kisi.getUserName());
            this.personPasswordEnter.setText(kisi.getPassword());
            this.personImage.setImageResource(kisi.getResim());
        }


    }


}
