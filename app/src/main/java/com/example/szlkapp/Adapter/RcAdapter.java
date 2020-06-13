package com.example.szlkapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.szlkapp.Activity.DetayActivity;
import com.example.szlkapp.Modal.Kelime;
import com.example.szlkapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RcAdapter extends RecyclerView.Adapter<RcAdapter.CardHolder>   {

    private List<Kelime> kelimes;
    private Context mContext;
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private FirebaseAuth auth;

    public RcAdapter(List<Kelime> kelimes, Context mContext) {
        this.kelimes = kelimes;
        this.mContext = mContext;

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        firebaseUser = auth.getCurrentUser();
        databaseReference = db.getReference("kisiler").child(firebaseUser.getUid()).child("kelimes_id");
        Log.e("uniq",firebaseUser.getUid());

    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_tasarim,parent,false);

        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        final Kelime kelime = kelimes.get(position);

        holder.textViewKelimeTr.setText(kelime.getTurkce());
        holder.textViewKelimeIng.setText(kelime.getIngilizce());
        holder.checkBoxListeyeEkle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        String kelime_id = kelime.getKelime_id();
                        databaseReference.push().setValue(kelime_id);

                    }
            }
        });

        //card Click
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("card","card selected " );

                //go to DetayActivity
                Intent intent = new Intent(mContext, DetayActivity.class);
                intent.putExtra("kelime",kelime);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return kelimes.size();
    }

    class CardHolder extends RecyclerView.ViewHolder{
        TextView textViewKelimeTr, textViewKelimeIng;
        CardView cardView;
        CheckBox checkBoxListeyeEkle;


        public CardHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView_tasarim);
            textViewKelimeTr = itemView.findViewById(R.id.textView_kelimeTr2);
            textViewKelimeIng = itemView.findViewById(R.id.textView_cardKelimeIng);
            checkBoxListeyeEkle = itemView.findViewById(R.id.checkBox_listeyeEkle);


        }
    }
}
