package com.example.projectsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class register extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText email;
    EditText pass;
//    String validation="\"^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{6,}$\"";
EditText regpass;
TextView ac;
ProgressDialog dialog;
    String em,pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ac=findViewById(R.id.have_ac);
        Button btn=findViewById(R.id.btn);
        dialog=new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        btn.setOnClickListener(v->{
            createuser();
        });
        ac.setOnClickListener(v->{
           startActivity(new Intent(register.this,login.class));
        });
    }


    public void createuser() {
        email = findViewById(R.id.btnlog);
        pass = findViewById(R.id.pass1);

        em = email.getText().toString();
        pa = pass.getText().toString().trim();


//        String validation="^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=])(?=\\S+$).{6,}$";
        if (TextUtils.isEmpty(em)) {
            email.setError("Email cannot be empty");
            email.requestFocus();
        }
//        else if(!isPasswordValid(pa)){
//            Toast.makeText(register.this,"Password should contain 8 character long and contain atleast one uppercase letter",Toast.LENGTH_LONG).show();
//        }
//        else if(!pa.matches(validation))
//        {
//            Toast.makeText(register.this,"Password should contain 6 character with one uppercase letter ,one digit and one special character",Toast.LENGTH_LONG).show();
////            pass.setText("Password should contain 6 character with one uppercase letter ,one digit and one special character");
//        }

        else if (TextUtils.isEmpty(pa)) {
            pass.setError("Password cannot be empty");
            pass.requestFocus();
        }
        else if(!isPasswordValid(pa)){
            Toast.makeText(register.this,"Password should contain 6 character with one uppercase letter ,one digit and one special character",Toast.LENGTH_LONG).show();
        }
        else {


            mAuth.createUserWithEmailAndPassword(em, pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

//                    User user = new User(em, pa);
                    if (task.isSuccessful()) {
                        dialog.setTitle("Sign Up");
                        dialog.setMessage("Signing up");
//                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        Toast.makeText(register.this, "User Registered successfully", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
//                        dialog.setTitle("Sign Up");
//                        dialog.setMessage("Signing up");
//                        dialog.setCanceledOnTouchOutside(false);
//                        dialog.show();
                        startActivity(new Intent(register.this, onboarding1.class));
                    } else {
                        String e = String.valueOf(task.getException());
                        Toast.makeText(register.this, "Failed" + e, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        }
        boolean isPasswordValid (String pa){
            Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$");
            return pattern.matcher(pa).matches();
        }
    }
