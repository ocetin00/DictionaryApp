package com.example.szlkapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.szlkapp.Modal.Kelime;
import com.example.szlkapp.Modal.Kisi;
import com.example.szlkapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextMail,editTextpassword;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;

    private String savedMailInfo;
    private String savedPasswordInfo;
    String gelenPassword;
    String gelenEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextMail = findViewById(R.id.editTextTextEmailAddress);
        editTextpassword = findViewById(R.id.editTextTextPassword);

        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();

        databaseReference = db.getReference("kisiler");



        if(user != null){
            Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

         gelenPassword = sharedPreferences.getString("kullanici_password","");
         gelenEmail = sharedPreferences.getString("kullanici_mail","");

        if(!(gelenEmail.matches("") && gelenPassword.matches(""))){
            editTextMail.setText(gelenEmail);
            editTextpassword.setText(gelenPassword);
        }
    }

    public void signIn(View view){
       final  String mail = editTextMail.getText().toString();
       final  String password = editTextpassword.getText().toString();

        gelenPassword = sharedPreferences.getString("kullanici_password","");
        gelenEmail = sharedPreferences.getString("kullanici_mail","");






        Log.e("mail",gelenPassword+"  sifre :"+password);


        if(mail.matches("") || password.matches("")){
            Toast.makeText(LoginActivity.this,"kullanıcı adı veya sifre bos olamaz",Toast.LENGTH_LONG).show();
        }else  {

            mAuth.signInWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {



                    if(!(mail.matches(gelenEmail) && password.matches(gelenPassword))){
                        sharedEditor.putString("kullanici_mail",mail);
                        sharedEditor.putString("kullanici_password",password);
                        sharedEditor.apply();
                    }


                    Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }


    }
    public void signUp(View view){
        final String mail = editTextMail.getText().toString();
        final String password = editTextpassword.getText().toString();
        if(mail.matches("") || password.matches("")){
            Toast.makeText(LoginActivity.this,"kullanıcı adı veya sifre bos olamaz",Toast.LENGTH_LONG).show();
        }else  {
            mAuth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    mAuth.signInWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {



                            if(!(mail.matches(gelenEmail) && password.matches(gelenPassword))){
                                sharedEditor.putString("kullanici_mail",mail);
                                sharedEditor.putString("kullanici_password",password);
                                sharedEditor.apply();
                            }

                            user = mAuth.getCurrentUser();
                            //kullanıcı ekleme


                            Kisi kisi = new Kisi(user.getUid(),user.getEmail(),null);
                            databaseReference.push().setValue(kisi);



                            Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    });





                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });

        }
        }



}