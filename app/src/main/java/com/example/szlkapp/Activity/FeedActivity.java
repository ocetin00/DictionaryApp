package com.example.szlkapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.szlkapp.Adapter.RcAdapter;
import com.example.szlkapp.Database.Veritabani;
import com.example.szlkapp.Fragment.AddKelimeDialog;
import com.example.szlkapp.Modal.Kelime;
import com.example.szlkapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private RcAdapter rcAdapter;
    private FloatingActionButton fab;


    private List<Kelime> kelimes;

    private Veritabani vt;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_activity);

        mAuth = FirebaseAuth.getInstance();

        kelimes = new ArrayList<>();



        fab = findViewById(R.id.floatingActionButton2);
        recyclerView = findViewById(R.id.rv);

        rcAdapter = new RcAdapter(kelimes,this);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("kelimeler");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rcAdapter);

       tumKelimeler();
         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AddKelimeDialog addKelimeDialog = new AddKelimeDialog();
                 addKelimeDialog.show(getSupportFragmentManager(),"dialog");
                 rcAdapter.notifyDataSetChanged();
             }
         });


    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_cikis : mAuth.signOut();
                Intent intent = new Intent(FeedActivity.this, LoginActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
       // tumKelimeler(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        arama(newText);

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu,menu);


/*

        MenuItem itemProfilee = menu.findItem(R.id.action_goto_profile);
        itemProfilee.setIcon(R.drawable.ic_baseline_account_circle_24);
*/



        //for searchView
        MenuItem item = menu.findItem(R.id.action_ara);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }


     public void tumKelimeler(){

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kelimes.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    if(dataSnapshot.exists()){
                        Kelime kelime = data.getValue(Kelime.class);

                        Log.e("kelimeig",kelime.getIngilizce()+" ");
                        kelime.setKelime_id(data.getKey());

                        kelimes.add(kelime);
                    }

                }
                rcAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void arama(final String aramaKelime){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kelimes.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Kelime kelime = data.getValue(Kelime.class);
                    if(kelime.getIngilizce().contains(aramaKelime)){
                        kelime.setKelime_id(data.getKey());
                        kelimes.add(kelime);

                    }
                    Log.e("kelimeig",kelime.getIngilizce()+" ");

                }
                rcAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}