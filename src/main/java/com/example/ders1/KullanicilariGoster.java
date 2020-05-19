package com.example.ders1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class KullanicilariGoster extends AppCompatActivity {

    private String history = null;
    private RecyclerView recyclerView;
    private PersonAdaptor personAdaptor;
    private LinearLayoutManager linearLayoutManager;

    private ArrayList<Person> mPersonList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanicilari_goster);

        mPersonList = Person.getData();
        for(Person p:mPersonList){
            p.setPassword("*****");     // İlk açılışta şifrelerin gizli kalmasını istiyorum.
        }

        buildRecyclerView();
    }

    public void buildRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        personAdaptor = new PersonAdaptor(this, mPersonList);
        recyclerView.setAdapter(personAdaptor);

        // Oluşturulan recylerView a bir layout lazım.
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        personAdaptor.setOnItemClickListener(new PersonAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Bir kullanıcı üzerine basıldığında gelen bilgiler.
            }

            @Override
            public void onShowPasswordClick(int position) {

                if(mPersonList.get(position).getPassword().compareTo("*****")==0){
                    mPersonList.get(position).setPassword(mPersonList.get(position).getPassword_temp());
                    personAdaptor.notifyItemChanged(position);

                }else{

                    mPersonList.get(position).setPassword("*****");
                    personAdaptor.notifyItemChanged(position);
                }

            }
        });
    }

}
