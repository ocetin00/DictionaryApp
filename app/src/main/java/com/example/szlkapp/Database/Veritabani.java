package com.example.szlkapp.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.szlkapp.Modal.Kelime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Veritabani {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

   public Veritabani(){

       database = FirebaseDatabase.getInstance();
       myRef = database.getReference("kelimeler");

    }


    public ArrayList<Kelime> tumKelimeler(){
       final ArrayList<Kelime> kelimes = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        Kelime kelime = data.getValue(Kelime.class);

                        Log.e("kelimeig", kelime.getIngilizce() + " ");
                        kelime.setKelime_id(data.getKey());

                        kelimes.add(kelime);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return kelimes;
    }







}
