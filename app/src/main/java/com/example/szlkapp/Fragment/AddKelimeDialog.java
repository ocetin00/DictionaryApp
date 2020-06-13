package com.example.szlkapp.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.szlkapp.Modal.Kelime;
import com.example.szlkapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddKelimeDialog extends DialogFragment {
    private String yeniTr;
    private String yeniIng;
    private EditText editTextViewIng;
    private EditText editTextViewTr;


    private FirebaseDatabase database;
    private DatabaseReference myRef;






    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("kelimeler");



        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();
       final View view = inflater.inflate(R.layout.add_kelime_alertdialogtasarim,null);

        editTextViewIng = view.findViewById(R.id.editTextT_alertDialog_ing);
        editTextViewTr = view.findViewById(R.id.editTextT_alertDialog_tr);
        builder .setView(view)
                .setTitle("Kelime Ekle")
                .setPositiveButton("EKle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ıng = editTextViewIng.getText().toString();
                        String tr = editTextViewTr.getText().toString();
                        Kelime kelime = new Kelime("",ıng,tr);

                        myRef.push().setValue(kelime);

                    }
                })
                .setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });



        return  builder.create();
    }
}
