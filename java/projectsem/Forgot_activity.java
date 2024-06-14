package com.example.projectsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_activity extends AppCompatActivity {
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        EditText emailforgot=findViewById(R.id.emailforgot);
        Button buttonforgot=findViewById(R.id.buttonforgot);
        ProgressBar p=findViewById(R.id.progressBar);
        mAuth= FirebaseAuth.getInstance();
        buttonforgot.setOnClickListener(v->{
            String email=emailforgot.getText().toString().trim();
            if(email.isEmpty()){
                emailforgot.setError("Email is required");
                emailforgot.requestFocus();
                return;

            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailforgot.setError("Please enter valid email");
                emailforgot.requestFocus();
                return;
            }
            p.setVisibility(View.VISIBLE);
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Forgot_activity.this, "Check your email to reset your password", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(Forgot_activity.this, "Try again ,something wrong happened!", Toast.LENGTH_SHORT).show();
                    }
                   // Intent i=new Intent(Forgot_activity.this,login.class);
                }
            });
        });
    }
}


