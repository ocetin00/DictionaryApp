package com.example.szlkapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.szlkapp.Modal.Kelime;
import com.example.szlkapp.R;

public class DetayActivity extends AppCompatActivity {

    private TextView textViewIng,textViewTr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);

        textViewIng = findViewById(R.id.textView_detayKelimeIng);
        textViewTr = findViewById(R.id.textView_detayKelimeTr);

        Intent intent = getIntent();
        Kelime gelenKelime = (Kelime) intent.getExtras().getSerializable("kelime");

        textViewIng.setText(gelenKelime.getIngilizce());
        textViewTr.setText(gelenKelime.getTurkce());
    }
}