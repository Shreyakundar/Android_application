package com.example.projectsem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    FirebaseAuth mAuth;
    ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
p=new ProgressDialog(this);
        Button btnn = findViewById(R.id.logbtn);
        Button btnn2 = findViewById(R.id.btnn2);
        TextView forgot = findViewById(R.id.forgottxt);

        mAuth = FirebaseAuth.getInstance();
        btnn.setOnClickListener(v -> {
            loginuser();
        });
        btnn2.setOnClickListener(v -> {
            startActivity(new Intent(login.this, register.class));
        });
        forgot.setOnClickListener(v -> {
            startActivity(new Intent(login.this, Forgot_activity.class));
        });
    }

    private void loginuser() {
        EditText email1 = findViewById(R.id.btnlog);
        EditText pass1 = findViewById(R.id.pass1);

        String em = email1.getText().toString();
        String pa = pass1.getText().toString();
        p=new ProgressDialog(this);
        if (TextUtils.isEmpty(em)) {

            email1.setError("Email cannot be empty");
            email1.requestFocus();
        } else if (TextUtils.isEmpty(pa)) {
            pass1.setError("Password cannot be empty");
            pass1.requestFocus();
        }
        else if(em.equalsIgnoreCase("shreyakundar84@gmail.com"))
        {
            mAuth.signInWithEmailAndPassword(em,pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent i=new Intent(login.this,.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            //Toast.makeText(login.this, "Invalid email", Toast.LENGTH_LONG).show();


            mAuth.signInWithEmailAndPassword(em, pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        p.setTitle("Login");
                        p.setMessage("Logging in");
                        p.setCanceledOnTouchOutside(false);
                        p.show();

                        Toast.makeText(login.this, "User logged in successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(login.this, onboarding1.class));
                    } else {
                        Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                    }
                }

            });
        }}
    }


