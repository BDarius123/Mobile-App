package com.example.mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    EditText email;
    Button resetBtn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        email = findViewById(R.id.email);
        resetBtn = findViewById(R.id.resetBtn);
        mAuth = FirebaseAuth.getInstance();
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString().trim();
                if(emailText.isEmpty()){
                    Toast.makeText(ResetPassword.this,"Field is empty",Toast.LENGTH_SHORT);
                }
                else{
                    ChangePassword(emailText);
                }
            }
        });

    }
    void ChangePassword(String email){
        resetBtn.setVisibility(View.INVISIBLE);
        mAuth.sendPasswordResetEmail(email)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPassword.this,"Failed",Toast.LENGTH_SHORT);
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ResetPassword.this,"Email sent",Toast.LENGTH_SHORT);
                        Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}