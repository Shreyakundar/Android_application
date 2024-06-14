package com.example.projectsem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity3 extends AppCompatActivity {
    FirebaseAuth mAuth;
    ProgressDialog p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        p=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        //EditText user=findViewById(R.id.user);
       // EditText pass=findViewById(R.id.pass);
        Button bt=findViewById(R.id.bt);

        bt.setOnClickListener(v->{
            loginuser();
        });
    }
    private void loginuser() {
        EditText email1 = findViewById(R.id.user);
        EditText pass1 = findViewById(R.id.pass);

        String em = email1.getText().toString();
        String pa = pass1.getText().toString();
        p = new ProgressDialog(this);
        if (TextUtils.isEmpty(em)) {

            email1.setError("Email cannot be empty");
            email1.requestFocus();
        } else if (TextUtils.isEmpty(pa)) {
            pass1.setError("Password cannot be empty");
            pass1.requestFocus();
        } else {
            p.setTitle("Login");
            p.setMessage("Logging in");
            p.setCanceledOnTouchOutside(false);
            p.show();

            mAuth.signInWithEmailAndPassword(em, pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(activity3.this, "User logged in successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(activity3.this,adminprofile.class));
                    } else {
                        Toast.makeText(activity3.this, "Login error", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }}